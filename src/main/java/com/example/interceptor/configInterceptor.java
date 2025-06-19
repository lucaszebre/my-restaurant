package com.example.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.entities.RestaurantConfig;
import com.example.repositories.RestaurantConfigRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class configInterceptor implements HandlerInterceptor {

    @Autowired
    private RestaurantConfigRepository restaurantConfigRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        if(restaurantConfigRepository.findAll().isEmpty()){
            response.sendRedirect("/admin/config");
            return false;
        }
        
        RestaurantConfig config = restaurantConfigRepository.findAll().get(0);

        boolean isNamePresent = config.getName() != null && config.getName().length() > 0;
        boolean isUrlPresent = config.getUrl() != null && config.getUrl().length() > 0;
        boolean isColorPresent = config.getColor() != null && config.getColor().length() > 0;
        boolean isPasswordPresent = config.getPassword() != null && config.getPassword().length() > 0;
        boolean isPhotoPresent = config.getPhoto() != null && config.getPhoto().length() > 0;

        if (isNamePresent && isUrlPresent && isColorPresent && isPasswordPresent && isPhotoPresent) {
            return true;
        }

        response.sendRedirect("/admin/config");
        return false;
    }
}