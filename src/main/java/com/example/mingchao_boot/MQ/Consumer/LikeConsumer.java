package com.example.mingchao_boot.MQ.Consumer;

import com.example.mingchao_boot.Mapper.articleMapper;
import com.example.mingchao_boot.ResultModel.ResultNormal;
import com.example.mingchao_boot.model.article.Like;
import com.example.mingchao_boot.model.mq.Attention;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class LikeConsumer {

    @Autowired
    Gson gson;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    articleMapper articleMapper;
    /**
     * 监听队列 有消息就处理消息
     */
    @RabbitListener(queues = "fy_like_queue")
    public void receive(Message message) {
        Like like = gson.fromJson(new String(message.getBody()),Like.class);
        SetArticleLikeNum(like);
        System.out.println("接受的消息为"+like.getUserId()+(like.getNum()==1?"点赞了":"取消点赞")+like.getId());
    }



    private void SetArticleLikeNum(Like like) {
        String type = like.getType()==0?"articleLike:":"assessLike:";
        String key = type + like.getId();
        //等于1说明是点赞
        if (like.getNum()==1){
            redisTemplate.opsForSet().add(key,like.getUserId());
        }
        //否则是取消点赞
        else{
            redisTemplate.opsForSet().remove(key,0);
            redisTemplate.opsForSet().remove(key,like.getUserId());
        }
        System.out.println("当前"+key+"的点赞列表为"+redisTemplate.opsForSet().members(key));
        articleMapper.setArticleLikeNum(like.getId(),like.getNum());
    }
}
