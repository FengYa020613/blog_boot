package com.example.mingchao_boot.MQ.Consumer;

import com.example.mingchao_boot.Mapper.articleMapper;
import com.example.mingchao_boot.Mapper.userMapper;
import com.example.mingchao_boot.Redis.RedisUtil;
import com.example.mingchao_boot.model.User;
import com.example.mingchao_boot.model.UserCode;
import com.example.mingchao_boot.model.mq.Attention;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AttentionConsumer {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    Gson gson;
    @Autowired
    articleMapper articleMapper;
    @Autowired
    userMapper userMapper;
    /**
     * 监听队列 有消息就处理消息
     */
    @RabbitListener(queues = "fy_attention_queue")
    public void receive(Message message) {
        Attention attention = gson.fromJson(new String(message.getBody()),Attention.class);
        if (attention.getNum()==1){
            if (articleMapper.findAttention(attention.getUserId(),attention.getTargetId())==0){
                redisUtil.incrValue(attention.getUserId()+":attention");
                redisUtil.incrValue(attention.getTargetId()+":fans");
                articleMapper.addAttention(attention.getUserId(),attention.getTargetId());
            }
        }else{
            redisUtil.decrValue(attention.getUserId()+":attention");
            redisUtil.decrValue(attention.getTargetId()+":fans");
            articleMapper.delAttention(attention.getUserId(),attention.getTargetId());
        }
        System.out.println("接受的关注消息为"+attention);
    }

}
