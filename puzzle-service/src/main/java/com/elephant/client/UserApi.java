package com.elephant.client;

import com.elephant.api.vo.user.UserAuthVO;
import com.elephant.client.dto.LoginDTO;
import com.elephant.client.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "feignTest", url = "https://api.punengshuo.com/api/auth")
public interface UserApi {

    @GetMapping({"/loadUserByEmail"})
    ResultVO<UserAuthVO> loadUserByEmail(@SpringQueryMap LoginDTO dto);

    @GetMapping({"/loadUserById"})
    ResultVO<UserAuthVO> loadUserById(@SpringQueryMap UserDTO dto);
}
