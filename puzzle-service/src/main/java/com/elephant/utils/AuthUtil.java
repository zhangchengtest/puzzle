package com.elephant.utils;

import com.elephant.api.vo.user.UserAuth;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class AuthUtil {


    public static UserAuth getUserAuth() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        UserAuth user = (UserAuth)request.getAttribute("user");
        return user;
    }
}
