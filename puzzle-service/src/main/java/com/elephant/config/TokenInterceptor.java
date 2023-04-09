package com.elephant.config;

import com.cunw.framework.exception.BusinessException;
import com.elephant.api.vo.user.UserAuth;
import com.elephant.auth.bo.TokenModel;
import com.elephant.auth.service.TokenVerifyService;
import com.elephant.client.UserClient;
import com.elephant.client.dto.UserDTO;
import com.elephant.common.constants.TokenConstants;
import com.elephant.common.enums.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 令牌拦截器
 * @author chengwei.deng
 * @date 2022-12-10 22:00
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private static final String TOKEN_NAME = "Authorization";

    private final TokenVerifyService tokenVerifyService;

    private final ObjectProvider<UserClient> userClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("enter TokenInterceptor");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isEmpty(token)) {
                log.warn(ExceptionEnum.LOGIN_TOKEN_EMPTY.getMsg());
                return true;
            }
        }

        token = StringUtils.replace(token, TokenConstants.AUTH_BEARER_START, "");
        TokenModel tokenModel = tokenVerifyService.checkToken(token);
        String accountId = tokenModel.getUserId();
//        UserAuth account = new UserAuth();
        UserAuth account = userClient.getIfUnique().loadUserById(UserDTO.builder().userId(accountId).build());
        if (Objects.isNull(account)) {
            log.warn(ExceptionEnum.ACCOUNT_NOT_FOUND.getMsg());
            return true;
        }

        request.setAttribute("user", account);
        log.info("leave TokenInterceptor");
        return true;
    }


}
