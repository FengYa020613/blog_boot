package com.example.mingchao_boot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private static String EXCHANGE_NAME = "fy_blog_boot_topic_exchange";
    private static String LOGIN_QUEUE = "fy_login_queue";
    private static String ATTENTION_QUEUE = "fy_attention_queue";
    private static String LIKE_QUEUE = "fy_like_queue";
    /**
     * 声明交换机
     */
    @Bean("TOPIC_EXCHANGE")
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME,true,false);
    }
    /**
     * 声明队列
     */
    @Bean("USER_LOGIN_QUEUE")
    public Queue loginQueue() {
        return new Queue(LOGIN_QUEUE,true,false,false);
    }

    @Bean("ATTENTION_QUEUE")
    public Queue attentionQueue() {
        return new Queue(ATTENTION_QUEUE,true,false,false);
    }

    @Bean("LIKE_QUEUE")
    public Queue likeQueue() {
        return new Queue(LIKE_QUEUE,true,false,false);
    }
    /**
     * 声明绑定关系
     */
    @Bean
    public Binding loginBinding(@Qualifier("USER_LOGIN_QUEUE") Queue queue, TopicExchange topicExchange) {
        //把queue绑定到exchange上
        return BindingBuilder.bind(queue).to(topicExchange).with("login.*");
    }
    @Bean
    public Binding attentionBinding(@Qualifier("ATTENTION_QUEUE") Queue queue, TopicExchange topicExchange) {
        //把queue绑定到exchange上
        return BindingBuilder.bind(queue).to(topicExchange).with("attention.*");
    }
    @Bean
    public Binding likeBinding(@Qualifier("LIKE_QUEUE") Queue queue,TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("like.*");
    }

}
