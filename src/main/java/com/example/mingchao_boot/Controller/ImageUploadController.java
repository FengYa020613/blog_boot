package com.example.mingchao_boot.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin//可以加在类上，也可以加到方法上
@RequestMapping("/upload")
public class ImageUploadController {

    // 指定保存图片的目录
    private static final String UPLOAD_DIR_USER = "upload/user/";
    private static final String UPLOAD_DIR_FOOD = "upload/food/";
    private static final String UPLOAD_DIR_ASSESS = "upload/assess/";
    private static final String UPLOAD_DIR_DYNAMIC = "upload/dynamic/";
    private static final String UPLOAD_DIR_REPLY = "upload/reply/";


    @PostMapping("/user")
    public ResponseEntity<String> uploadUserImage(@RequestParam MultipartFile[] file) {
        return getStringResponseEntity(file, UPLOAD_DIR_USER);
    }

    @PostMapping("/food")
    public ResponseEntity<String> uploadFoodImage(@RequestParam MultipartFile[] file) {
        return getStringResponseEntity(file, UPLOAD_DIR_FOOD);
    }

    @PostMapping("/assess")
    public ResponseEntity<String> uploadAssessImage(@RequestParam MultipartFile[] file) {
        return getStringResponseEntity(file, UPLOAD_DIR_ASSESS);
    }

    @PostMapping("/dynamic")
    public ResponseEntity<String> uploadDynamicImage(@RequestParam MultipartFile[] file) {

        return getStringResponseEntity(file, UPLOAD_DIR_DYNAMIC);
    }

    @PostMapping("/reply")
    public ResponseEntity<String> uploadReplyImage(@RequestParam MultipartFile[] file) {
        return getStringResponseEntity(file, UPLOAD_DIR_REPLY);
    }

    private ResponseEntity<String> getStringResponseEntity(MultipartFile[] file, String uploadDirFood) {

        System.out.println(file.length);
        System.out.println(file.toString());
        try {
            // 确保目录存在
            Files.createDirectories(Paths.get(uploadDirFood));
            for (int i = 0; i < file.length; i++) {
                // 获取文件名
                String fileName = file[i].getOriginalFilename();
                // 创建保存文件的路径
                Path filePath = Paths.get(uploadDirFood + fileName);
                // 保存文件到指定路径
                file[i].transferTo(filePath);
            }
            // 返回上传成功信息
            return ResponseEntity.ok("Image uploaded successfully");

        } catch (IOException e) {
            // 处理文件上传过程中可能出现的异常
            e.printStackTrace();
            return ResponseEntity.status(500).body("Image upload failed");
        }
    }
}