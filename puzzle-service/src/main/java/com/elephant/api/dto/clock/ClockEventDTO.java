package com.elephant.api.dto.clock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 闹钟完成事件-DTO
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="闹钟完成事件-DTO")
public class ClockEventDTO implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String clockId;

    /**
     * 状态（0：停用，1：启用）
     */
    @ApiModelProperty("状态（0：停用，1：启用）")
    private Integer status;

}
