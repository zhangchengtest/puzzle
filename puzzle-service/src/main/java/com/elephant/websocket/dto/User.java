package com.elephant.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String userName;
}
