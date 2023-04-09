package com.elephant.auth.service.impl;

import com.elephant.api.vo.user.UserAuth;
import com.elephant.auth.bo.TokenModel;
import com.elephant.auth.service.JwtUtils;
import com.elephant.auth.service.TokenVerifyService;
import com.elephant.client.UserApi;
import com.elephant.client.dto.UserDTO;
import com.elephant.common.constants.TokenConstants;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Tower
 * @ClassName TokenService.java
 * @Description token 校验
 * @createTime 2020年11月25日 10:37:00
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TokenVerifyServiceImpl implements TokenVerifyService {

    @Override
    public TokenModel checkToken(String token) {
        // 解析token
        token = token.replace(TokenConstants.AUTH_BEARER_START, "");
        TokenModel tokenModel = JwtUtils.parse(token, TokenModel.class);

        return tokenModel;
    }

    @Override
    public UserAuth loadUserById(UserDTO dto){
        return null;
    }


}
