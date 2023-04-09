package com.elephant.client;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

@ApiModel("请求返回处理VO")
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class ResultVO<T> implements Serializable {
    public static final String SUCCEED = "succeed";
    public static final String FAILED = "failed";
    @ApiModelProperty("状态码")
    private String code;
    @ApiModelProperty("状态标识(succeed/failed)")
    private String status;
    @ApiModelProperty("描述消息")
    private String message;
    /** @deprecated */
    @Deprecated
    @ApiModelProperty("错误消息")
    private String error;
    /** @deprecated */
    @Deprecated
    @ApiModelProperty("错误数据")
    private Object errorData;
    @ApiModelProperty("业务数据")
    private T data;

    public ResultVO() {
        this.code = "0";
        this.status = "succeed";
    }

    public ResultVO(final T data) {
        this.code = "0";
        this.status = "succeed";
        this.data = data;
    }

    public ResultVO(final String code, final String message) {
        this.code = code;
        this.status = "failed";
        this.message = Strings.isNullOrEmpty(message) ? "failed" : message;
    }

    public boolean isSucceed() {
        return "succeed".equalsIgnoreCase(this.status);
    }

    @JsonIgnore
    public boolean isFailed() {
        return "failed".equalsIgnoreCase(this.status);
    }

    @JsonIgnore
    public void failedMessage(final String message) {
        this.status = "failed";
        this.message = Strings.isNullOrEmpty(message) ? "failed" : message;
    }

    @JsonIgnore
    public void succeedMessage(final String message) {
        this.status = "succeed";
        this.message = Strings.isNullOrEmpty(message) ? "succeed" : message;
    }

    @JsonIgnore
    public String resultMessage() {
        if (!Strings.isNullOrEmpty(this.message)) {
            List<String> excludes = Lists.newArrayList(new String[]{"succeed", "failed"});
            if (!excludes.contains(this.message)) {
                return this.message;
            }
        }

        return this.isSucceed() ? "操作成功" : "操作失败";
    }

    @JsonIgnore
    public void succeed() {
        this.succeed(true);
    }

    @JsonIgnore
    public void failed() {
        this.succeed(false);
    }

    @JsonIgnore
    public void succeed(final boolean success) {
        if (success) {
            this.status = "succeed";
            this.code = "0";
        } else {
            this.status = "failed";
            this.code = "500";
        }

    }

    public static <T> ResultVO<T> ofSucceed(@Nullable final String message) {
        ResultVO<T> vo = new ResultVO();
        vo.succeed();
        vo.succeedMessage(message);
        return vo;
    }

    public static <T> ResultVO<T> ofSucceed() {
        return ofSucceed((String)null);
    }

    public static <T> ResultVO<T> ofFailed(@Nullable final String message) {
        ResultVO<T> vo = new ResultVO();
        vo.failed();
        vo.failedMessage(message);
        return vo;
    }

    public static <T> ResultVO<T> ofFailed() {
        return ofFailed((String)null);
    }

    public String getCode() {
        return this.code;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    /** @deprecated */
    @Deprecated
    public String getError() {
        return this.error;
    }

    /** @deprecated */
    @Deprecated
    public Object getErrorData() {
        return this.errorData;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    /** @deprecated */
    @Deprecated
    public void setError(final String error) {
        this.error = error;
    }

    /** @deprecated */
    @Deprecated
    public void setErrorData(final Object errorData) {
        this.errorData = errorData;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResultVO)) {
            return false;
        } else {
            ResultVO<?> other = (ResultVO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                label62: {
                    Object this$error = this.getError();
                    Object other$error = other.getError();
                    if (this$error == null) {
                        if (other$error == null) {
                            break label62;
                        }
                    } else if (this$error.equals(other$error)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    Object this$errorData = this.getErrorData();
                    Object other$errorData = other.getErrorData();
                    if (this$errorData == null) {
                        if (other$errorData == null) {
                            break label55;
                        }
                    } else if (this$errorData.equals(other$errorData)) {
                        break label55;
                    }

                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResultVO;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $error = this.getError();
        result = result * 59 + ($error == null ? 43 : $error.hashCode());
        Object $errorData = this.getErrorData();
        result = result * 59 + ($errorData == null ? 43 : $errorData.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "ResultVO(code=" + this.getCode() + ", status=" + this.getStatus() + ", message=" + this.getMessage() + ", error=" + this.getError() + ", errorData=" + this.getErrorData() + ", data=" + this.getData() + ")";
    }
}
