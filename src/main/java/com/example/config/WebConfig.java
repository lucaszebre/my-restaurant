package com.example.config; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.interceptor.configInterceptor; 

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private configInterceptor myConfigInterceptor; 

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myConfigInterceptor)
                .addPathPatterns("/**") 
                .excludePathPatterns("/config")
                .excludePathPatterns("/css/**", "/js/**", "/images/**"); 
    }
}
