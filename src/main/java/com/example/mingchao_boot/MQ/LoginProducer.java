package com.example.mingchao_boot.MQ;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @Author fengye
 */
public class LoginProducer {
    public static void main(String[] args) throws Exception {


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
        channel.queueUnbind("fy_queue_name",exchangeName,"fy_queue_name");
        /**
         * 创建交换机
         * 1.交换机名称
         * 2.交换机类型 direct topic fanout headers(性能差)
         * 3.指定交换机是否需要持久化，如果是true，那么交换机的元素要持久化
         * 4.指定交换机在没有队列绑定时是否需要删除，设置false表示不删除
         * 5.Map<String,Object>类型，用来设置交换机其他的一些结构化参数，一般设置null
         */
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT,true,false,null);

        /**
         * 生成队列
         * 1.队列名称
         * 2.队列是否持久化，队列名称等元数据的持久化，不是消息的持久化
         * 3.表示队列是不是私有化的，如果是私有的，只有创建它的应用程序才能消费消息
         * 4.队列在没有消费者订阅的情况下是否自动删除
         * 5.队列的一些结构化信息，比如申明死信队列和磁盘队列会用到，一般设置为null
         */
        channel.queueDeclare(queueName, true,false,false,null);
        /**
         * 交换机和队列绑定
         * 1.队列名称
         * 2.交换机名称
         * 3.路由链，直连模式下可以为队列名称
         */
        /**
         * 删除消息队列绑定
         */
        //channel.queueBind(queueName,exchangeName,queueName);
        //发送消息
        String message = "Hello World1";
        /**
         * 发送消息
         * 1.发送到哪台交换机
         * 2.队列名称
         * 3.其他参数信息
         * 4.发送消息的消息体（需要byte类型）
         */
        channel.basicPublish(exchangeName,queueName,null,message.getBytes());
        //关闭channel和connection
        channel.close();
        connection.close();
    }

}
