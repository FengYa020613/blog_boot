package com.example.mingchao_boot.config;

import com.example.mingchao_boot.Filter.WebFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean FilterRegister(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new WebFilter());
        registration.addUrlPatterns("/*");
        registration.setName("WebFilter");
        registration.setOrder(1);
        return registration;
    }
}
