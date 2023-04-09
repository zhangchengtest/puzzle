package com.elephant.client;

import com.elephant.api.vo.user.UserAuth;
import com.elephant.api.vo.user.UserAuthVO;
import com.elephant.client.dto.LoginDTO;
import com.elephant.client.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 互动应用-Client
 *
 * @author chengwei.deng
 */
@Service
@RequiredArgsConstructor
public class UserClient extends BaseClient {

    private final UserApi userApi;

    public UserAuth loadUserByEmail(@SpringQueryMap LoginDTO dto){
        UserAuthVO data = super.getDataWithException(userApi.loadUserByEmail(dto));
        UserAuth result = new UserAuth();
        result.setId(data.getUserId());
        result.setUsername(data.getUserName());
        result.setNickname(data.getUserName());
        result.setPwd(data.getPwd());
        result.setEmail(data.getEmail());
        result.setIsLock(data.getIsLock());
        result.setRoleCode(data.getRoleCode());
        result.setRoleName("admin");
        result.setAvatar(data.getAvatar());

        return result;
    }

    public UserAuth loadUserById(@SpringQueryMap UserDTO dto){
        UserAuthVO data = super.getDataWithException(userApi.loadUserById(dto));
        UserAuth result = new UserAuth();
        result.setId(data.getUserId());
        result.setUsername(data.getUserName());
        result.setNickname(data.getUserName());
        result.setPwd(data.getPwd());
        result.setEmail(data.getEmail());
        result.setIsLock(data.getIsLock());
        result.setRoleCode(data.getRoleCode());
        result.setRoleName("admin");
        result.setAvatar(data.getAvatar());

        return result;
    }

}
