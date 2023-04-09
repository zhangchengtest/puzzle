package com.elephant.auth.service;

import com.elephant.api.vo.user.UserAuth;
import com.elephant.auth.bo.TokenModel;
import com.elephant.client.dto.UserDTO;
import org.springframework.cloud.openfeign.SpringQueryMap;

/**
 * @author Tower
 * @ClassName TokenService.java
 * @Description token 校验
 * @createTime 2020年11月25日 10:37:00
 */
public interface TokenVerifyService {
    /**
     * 校验 token
     * @param token
     * @return
     */
    TokenModel checkToken(String token);

    UserAuth loadUserById(UserDTO dto);


}
