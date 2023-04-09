package com.elephant.api.vo.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthVO {

    private String userId;

    private Integer userInfoId;

    private String userName;

    private String pwd;

    private Integer loginType;

    private String ipAddress;

    private String ipSource;

    private String email;
    private String nickName;

    private String avatar;

    private String intro;

    private String website;

    private Integer isSubscribe;

    private Integer isLock;

    private String roleCode;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    private LocalDateTime lastLoginTime;

}
