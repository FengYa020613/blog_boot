package com.example.mingchao_boot;

import com.example.mingchao_boot.model.User;
import com.example.mingchao_boot.util.Base64Match;
import com.example.mingchao_boot.util.Base64ToImage;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MingchaoBootApplicationTests {

    @Qualifier("redisTemplete")
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    Base64ToImage base64ToImage;
    @Autowired
    private Gson gson;

   /* @Test
    void contextLoads() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //获取数据库连接
        User user = new User();
        user.setNickName("fengye");
        user.setPhone("17705192835");
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        redisTemplate.opsForValue().set("user",user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

    @Test
    public void uploadImage(){
        List<String> list;
        String base = "<p style=\"text-align: center;\">大概地方</p>\n" +
                "<p style=\"text-align: center;\">GV房间干净</p>\n" +
                "<p style=\"text-align: center;\"><img src=\"http://localhost:8085/upload/image/article/100004469f4922-2472-4cf3-a08c-49ce58ecf8fa.png\"></p>\n" +
                "<p style=\"text-align: center;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"http://localhost:8085/upload/image/article/100004ebc1b3aa-211c-4004-a894-3ce6f7cf5b3b.png\"></p>";

        String base1 = "<p>规范化发过火</p>\n" +
                "<p><img src=\"https://media-cdn-mingchao.kurogame.com/akiwebsite/website2.0/images/1679932800000/qj9qrn3rvh68b3wmyh-1679972337490.jpg\" alt=\"《鸣潮》官方网站 - Wuthering Waves\" width=\"802\" height=\"1426\"></p>\n" +
                "<p>&nbsp;</p>";
        String base2 = "<p><img style=\"width: 100%; border-radius: 10px;\" src=\"https://img2.baidu.com/it/u=640254957,2783" +
                "685170&amp;fm=253&amp;fmt=auto&amp;app=138&amp;f=JPEG?w=1422&amp;h=800\"></p>";
        list = Base64Match.matchUrl(base2);
        System.out.println(list);
        //list = Base64Match.match(base);
       // base64ToImage.LoadArticleImage(list.get(0),10001);
       *//* for (String s : list){
            System.out.println(s);
            base64ToImage.LoadArticleImage(s,10001);
        }*//*
    }

    @Test
    public void delete(){
        System.out.println(base64ToImage.deleteImage("http://localhost:8085/upload/image/article/100012ff090f48-b5d1-46d9-a68b-ec4e3810c0c8.png"));
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg(){
        User user = new User();
        user.setNickName("fengye");
        user.setPhone("17705192835");
        rabbitTemplate.convertAndSend("fy_blog_boot_fanout_exchange","",gson.toJson(user));
    }*/
    @Test
    public void contextLoads() {
        System.out.println(Base64Match.getIMG("java中的锁<img style=\"width: 50px; height: 50px;\" id= src=\"https://prod-alicdn-community.kurobbs.com/product/emoji/61e22e40d25f49b88dee84b61ed0613520240719.png\">"));
    }

}
