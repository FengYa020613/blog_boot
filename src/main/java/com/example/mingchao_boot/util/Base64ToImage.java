package com.example.mingchao_boot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;
import javax.imageio.ImageIO;

@Component
public class Base64ToImage {
    @Value("${upload.url.image.article}")
    private String UPLOAD_IMAGE_ARTICLE;
    @Value("${my-server.address}")
    private String LOCAL_BASE;

    public String LoadArticleImage(String base64String,int userid){
        String fileName = userid + (UUID.randomUUID()).toString();
        try {
            System.out.println(base64String);
            // 解码Base64字符串为字节数组
            byte[] imageBytes = Base64.getDecoder().decode(Arrays.stream(base64String.split(",")).toList().get(1));
            // 将字节数组转换为图片
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bis);
            // 保存图片
            File outputFile = new File(UPLOAD_IMAGE_ARTICLE + fileName + ".png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("图片已保存到：" + LOCAL_BASE + UPLOAD_IMAGE_ARTICLE + fileName + ".png");
            return LOCAL_BASE + UPLOAD_IMAGE_ARTICLE + fileName + ".png";
        } catch (IOException e) {
            e.printStackTrace();
            return "failed to load image";
        }
    }

    public Boolean deleteImage(String path){
        try {
            Files.deleteIfExists(Paths.get(path.replace(LOCAL_BASE,"")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

