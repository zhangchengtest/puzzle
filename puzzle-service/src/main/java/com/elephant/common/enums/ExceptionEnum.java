package com.elephant.common.enums;

import com.cunw.framework.enums.BaseExceptionEnum;
import com.cunw.framework.exception.InternalOutException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Tower
 * @description
 * @date 2022/11/24
 */
public enum ExceptionEnum implements BaseExceptionEnum {


    ACCOUNT_NOT_FOUND("01008", "帐号不存在！"),
    /**
     * 登录模块
     */
    LOGIN_TOKEN_EMPTY("22000", "无token，请重新登录！"),
    LOGIN_TOKEN_EXPIRE("22001", "登录失效，请重新进入！"),
    LOGIN_REFRESH_EXPIRE("22002","refreshToken已过期！"),
    LOGIN_OTHER("22003","您的账号已在别处登录！"),
    OFFICIAL_ERROR("22010", "当前访问人数过多，请稍后重试！"),

    ;

    private final String code;
    private final String msg;

    ExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    /**
     * 失败断言，直接抛出异常
     * @param errorMsg 自定义的异常提示信息
     */
    public void assertFail(String errorMsg) {
        if (StringUtils.isEmpty(errorMsg)){
            errorMsg = getMsg();
        }
        throw new InternalOutException(getCode(), errorMsg);
    }
}
