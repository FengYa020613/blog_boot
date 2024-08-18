package com.example.mingchao_boot.Service.ServiceImpl;

import com.example.mingchao_boot.Annotations.MethodFunction;
import com.example.mingchao_boot.Mapper.articleMapper;
import com.example.mingchao_boot.Mapper.loginMapper;
import com.example.mingchao_boot.Mapper.userMapper;
import com.example.mingchao_boot.Redis.RedisUtil;
import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.LoginService;
import com.example.mingchao_boot.model.Admin;
import com.example.mingchao_boot.model.AdminLogin;
import com.example.mingchao_boot.model.User;
import com.example.mingchao_boot.model.UserCode;
import com.example.mingchao_boot.util.*;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private loginMapper loginMapper;
    @Autowired
    private userMapper userMapper;
    @Autowired
    private articleMapper articleMapper;
    @Autowired
    SendSms sendSms;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    Base64ToImage base64ToImage;
    Gson gson = new Gson();
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);


    /**
     * 发送登录验证码
     * @param phone
     * @param type 1 为登录验证码 2 为换绑验证码
     * @return
     */
    @Override
    @Transactional
    @MethodFunction(Function = "发送登录验证码")
    public ResultNormal sendCode(String phone,int type) {
        //String code = sendSms.sendLoginSms(phone);
        String code = getRandomCode(6);
        redisUtil.setCode(phone,code,type);
        return ResultNormal.success("发送验证码成功",code);
    }

    @Override
    @Transactional
    @MethodFunction(Function = "用户登录")
    public ResultNormal userLogin(UserCode userCode){
        //获取手机号
        String phone = userCode.getPhone();
        //获取验证码
        String code = userCode.getCode();
        //从redis获取验证码
        String redisCode = redisUtil.getCode(phone,1);
        //如果验证码为空,说明验证码失效或者过期,提示用户请发送验证码
        if (redisCode==null)
            return ResultNormal.error("请先发送验证码");
        //验证验证码是否匹配一致
        if(code.equals(redisCode)){
            //如果输入的验证码一致则返回登录成功的信息，传递token给用户
            //通过用户手机号创建token
            String token = TokenUtil.createToken(userCode.getPhone());
            //添加token到userCode
            userCode.setToken(token);
            //向rabbitMq发送登录消息
            rabbitTemplate.convertAndSend("fy_blog_boot_topic_exchange","login.user",gson.toJson(userCode));
            //返回登陆成功的token
            return ResultNormal.success("登录成功",token);
        }else{
            //不一致则返回验证码错误信息
            return ResultNormal.error("验证码错误");
        }
    }

    @Override
    public ResultNormal getUserInfo(String token) {
        long start = System.nanoTime();
        String userId = redisUtil.get(token);
        long end = System.nanoTime();
        System.out.println("从redis查询用户耗时"+(end-start)/1.0e6+"毫秒");
        if(userId==null){
            log.info("token:"+token+"已过期");
            return ResultNormal.error("token已过期");
        }else{
            User user = gson.fromJson(redisUtil.get("user:"+userId),User.class);
            return ResultNormal.success(user);
        }
    }


    @Override
    @Transactional
    public ResultNormal changePhone(UserCode userCode, HttpServletRequest request) {
        String code = redisUtil.getCode(userCode.getPhone(), 2);
        if (code==null)
            return ResultNormal.error("请先发送验证码");
        if(userCode.getCode().equals(code)){
            String token = request.getHeader("token");
            if (token==null||token.equals("")){
                return ResultNormal.error("未登录");
            }
            User user = (User) getUserInfo(token).getData();
            if (user!=null){
                long userId = user.getUserId();
                if(userMapper.changePhone(userCode.getPhone(),userId)){
                    redisUtil.del("user:"+user.getUserId());
                    user.setPhone(userCode.getPhone());
                    String JsonUser = gson.toJson(user);
                    redisUtil.set("user:"+user.getUserId(),JsonUser);
                }
                return ResultNormal.success();
            }
            userMapper.addUser(userCode.getPhone(),userCode.getNickName());
            return ResultNormal.success();
        }else{
            return ResultNormal.error("验证码错误");
        }
    }

    @Override
    @Transactional
    public ResultNormal getUser(String token) {
        String userId = redisUtil.get(token);
        if(userId==null){
            log.info("token:"+token+"已过期");
            return ResultNormal.error("token已过期");
        }else{
            User user = userMapper.getUserById(userId);
            /*User user = new User();
            String userString = redisUtil.get("user:"+userId);
            if (userString!=null){
                user = gson.fromJson(userString,User.class);
            }else{
                user = userMapper.getUserById(userId);
            }*/
            if (user!=null){
                Integer commentNum = articleMapper.getCommentNum(Integer.parseInt(userId));
                Integer likeNum = articleMapper.getLikeNum(Integer.parseInt(userId));
                user.setLikeNum(likeNum==null?0:likeNum);
                user.setCommentNum(commentNum==null?0:commentNum);
                return ResultNormal.success(user);
            }
            redisUtil.del(token);
            return ResultNormal.error("用户不存在");
        }
    }

    @Override
    public ResultNormal saveUser(User user, HttpServletRequest request) {
        String userId = redisUtil.get(request.getHeader("token"));
        if (userId!=null){
            List<String> list = Base64Match.match(user.getAvatar());
            if (list.size()>0){
                user.setAvatar(base64ToImage.LoadArticleImage(list.get(0), (int) user.getUserId()));
            }
            userMapper.saveUser(user);
            redisUtil.del("user:"+userId);
            redisUtil.set("user:"+userId, gson.toJson(user));
            return ResultNormal.success();
        }
        return ResultNormal.error("未登录");
    }

    @Override
    public ResultNormal getUserFromList(int userId,HttpServletRequest request) {
        User user = userMapper.getUserById(String.valueOf(userId));
        if (user!=null){
            String uid = redisUtil.get(request.getHeader("token"));
            if (uid!=null){
                if(articleMapper.checkAttention(Integer.parseInt(uid),userId)>0){
                    user.setFlag(1);
                }
                if (articleMapper.checkAttention(userId,Integer.parseInt(uid))>0){
                    user.setFans(1);
                }
            }
            Integer commentNum = articleMapper.getCommentNum(userId);
            Integer likeNum = articleMapper.getLikeNum(userId);
            Integer fansNum = redisUtil.getInteger(userId+":fans");
            Integer attention = redisUtil.getInteger(userId+":attention");
            user.setFansNum(fansNum==null?0:fansNum);
            user.setAttentionNum(attention==null?0:attention);
            user.setLikeNum(likeNum==null?0:likeNum);
            user.setCommentNum(commentNum==null?0:commentNum);
            return ResultNormal.success(user);
        }
        return ResultNormal.error("没有此用户");
    }

    @Override
    public ResultNormal loginAdmin(AdminLogin admin) {
        String adminInfo = redisUtil.get("admin:"+admin.getUsername());
        Admin adminLogin;
        if(adminInfo==null){
            adminLogin = userMapper.getAdmin(admin.getUsername());
            if (adminLogin==null){
                return ResultNormal.error("用户不存在");
            }
            redisUtil.set("admin:"+admin.getUsername(),gson.toJson(adminLogin));
        }else{
            adminLogin = gson.fromJson(adminInfo, Admin.class);
        }
        String pwd = adminLogin.getPassword();
        if (pwd.equals(admin.getPassword())){
            String token = TokenUtil.createToken(admin.getUsername());
            return ResultNormal.success(token);
        }
        return ResultNormal.error("密码错误");
    }

    private String getRandomCode(int N){
        Random random = new Random();
        int number = random.nextInt((int) Math.pow(10, N));
        return String.valueOf(number);
    }
}
