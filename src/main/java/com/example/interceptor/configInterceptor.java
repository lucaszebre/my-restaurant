package com.example.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.controller.HeaderController;
import com.example.controller.UserController;
import com.example.entities.Header;
import com.example.entities.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class configInterceptor implements HandlerInterceptor {


    @Autowired
    private UserController user;
    
    @Autowired
    private HeaderController header;

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        Header uniqueHeader = header.getUniqueHeader().get(0);
        User uniqueUser = user.getUniqUser().get(0);

        boolean isColorPresent = uniqueUser.getColor().length() >0;
        boolean isPasswordPresent = uniqueUser.getPassword().length() >0;
        boolean isBannerPresent = uniqueHeader.getBannerPhoto().length() > 0;
        boolean isNamePresent =  uniqueHeader.getName().length() >0 ;
        boolean isUrlPresent = uniqueUser.getUrl().length() > 0;

        return isBannerPresent &&
         isNamePresent &&
          isUrlPresent &&
           isColorPresent &&
            isPasswordPresent ;


        }







    
}