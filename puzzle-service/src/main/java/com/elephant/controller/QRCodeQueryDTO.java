package com.elephant.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 推广总计-DTO
 *
 * @author cunw generator
 * date 2023-02-14
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="推广总计-DTO")
public class QRCodeQueryDTO implements Serializable {
    @NotEmpty(message="id不能为空")
    @ApiModelProperty(value ="帐号id", required = true)
    private String url;

    @ApiModelProperty("高度")
    private int height;
    @ApiModelProperty("宽度")
    private int width;

}
