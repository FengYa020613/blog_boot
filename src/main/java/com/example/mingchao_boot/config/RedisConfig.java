package com.example.mingchao_boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfig {
    //写redis自己的配置类
    @Bean("redisTemplete")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
        throws UnknownHostException{
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        //配置具体的序列化方式
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
