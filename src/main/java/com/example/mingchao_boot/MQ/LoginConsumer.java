package com.example.mingchao_boot.MQ;

import com.rabbitmq.client.*;

public class LoginConsumer {
    public static void main(String[] args) throws Exception{
        String queueName = "login_queue_user";
        String exchangeName = "fy_exchange_name";
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //服务地址
        factory.setHost("192.168.2.11");
        //账号
        factory.setUsername("fengye");
        //密码
        factory.setPassword("fengye");
        //端口号
        factory.setPort(5672);
        //创建连接
        Connection connection = factory.newConnection();
        //创建通信通道
        Channel channel = connection.createChannel();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("接受到的消息为:"+message);
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消费消息中断");
        };
        /**
         * 消费消息
         * 1.消费哪个队列
         * 2.消费成功之后是否自动应答，true：自动应答
         * 3.接收消息的回调函数
         * 4.取消消息的回调函数
         */
        channel.basicConsume(queueName, true,deliverCallback,cancelCallback);

    }
}
