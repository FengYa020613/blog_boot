package com.example.mingchao_boot.Service.ServiceImpl;

import com.example.mingchao_boot.Mapper.userMapper;
import com.example.mingchao_boot.Redis.RedisUtil;
import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.Service.UserService;
import com.example.mingchao_boot.model.SelectUserInfo;
import com.example.mingchao_boot.model.User;
import com.example.mingchao_boot.model.page.UserPage;
import com.example.mingchao_boot.model.UserSetting;
import com.example.mingchao_boot.util.Base64ToImage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    userMapper userMapper;
    @Autowired
    private Base64ToImage base64ToImage;

    @Override
    public ResultNormal getUserSetting(HttpServletRequest request) {
        String id = redisUtil.get(request.getHeader("token"));
        if (id!=null){
            int userId = Integer.parseInt(id);
            return ResultNormal.success(userMapper.getUserSetting(userId));
        }
        return ResultNormal.error("用户未登录");
    }

    @Override
    public ResultNormal setUserSetting(UserSetting setting, HttpServletRequest request) {
        String id = redisUtil.get(request.getHeader("token"));
        if (id!=null){
            int userId = Integer.parseInt(id);
            setting.setUserId(userId);
            userMapper.updateUserSetting(setting);
            return ResultNormal.success();
        }
        return ResultNormal.error("用户未登录");
    }

    @Override
    @Transactional
    public ResultNormal getUserList(SelectUserInfo search) {
        UserPage userPage = new UserPage();
        search.setPage((search.getPage()-1)*search.getPageSize());
        List<User> userList = userMapper.getUserList(search);
        Integer total = userMapper.getUserNum(search);
        userPage.setTotal(total);
        userPage.setUserList(userList);
        return ResultNormal.success(userPage);
    }

    @Override
    @Transactional
    public ResultNormal deleteUser(int userId) {
        User user = userMapper.getUserById(String.valueOf(userId));
        if (user==null){
            return ResultNormal.success();
        }
        String avatar = user.getAvatar();
        if(userMapper.deleteUser(userId)>0){
            base64ToImage.deleteImage(avatar);
            redisUtil.del("user:"+userId);
            return ResultNormal.success();
        }
        return ResultNormal.error("删除失败");
    }
}
