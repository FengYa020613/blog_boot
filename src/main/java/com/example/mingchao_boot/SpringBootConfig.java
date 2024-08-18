package com.example.mingchao_boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringBootConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*前面的upload意思是，访问的时候路径上要加上upload，不然也访问不到。
        后面的upload意思是，你图片上传的路径，我的图片上传就在upload文件中(新创建的的文件夹)。*/
        registry.addResourceHandler("/upload/**").addResourceLocations("file:upload/");
    }
}

