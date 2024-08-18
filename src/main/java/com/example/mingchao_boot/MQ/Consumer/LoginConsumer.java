package com.example.mingchao_boot.MQ.Consumer;

import com.example.mingchao_boot.Mapper.userMapper;
import com.example.mingchao_boot.Redis.RedisUtil;
import com.example.mingchao_boot.model.User;
import com.example.mingchao_boot.model.UserCode;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginConsumer {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    Gson gson;
    @Autowired
    userMapper userMapper;
    /**
     * 监听队列 有消息就处理消息
     */
    @RabbitListener(queues = "fy_login_queue")
    public void receive(Message message) {
        UserCode userCode = gson.fromJson(new String(message.getBody()),UserCode.class);
        String phone = userCode.getPhone();
        String token = userCode.getToken();
        if(redisUtil.get("user:"+userCode.getUserId())==null){
            User user = userMapper.getUser(phone);
            if(user==null){
                userMapper.addUser(phone,"用户"+phone);
            }
            user = userMapper.getUser(phone);
            String JsonUser = gson.toJson(user);
            redisUtil.set("user:"+user.getUserId(),JsonUser);
            redisUtil.setToken(token, String.valueOf(user.getUserId()));
        }
        redisUtil.delCode(phone,1);
        System.out.println("接受的消息为"+userCode);
    }
}
