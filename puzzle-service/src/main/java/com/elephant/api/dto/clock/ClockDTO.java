package com.elephant.api.dto.clock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 闹钟事件-DTO
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="闹钟事件-DTO")
public class ClockDTO implements Serializable {
    /**
     * 0日历,1按天,2每月(1-31),3每年(MM dd)，4按周
     */
    @ApiModelProperty("0日历,1按天,2每月(1-31),3每年(MM dd)，4按周")
    private Integer eventType;

    /**
     * 事件描述
     */
    @ApiModelProperty("事件描述")
    private String eventDescription;

    /**
     * 提醒时间
     */
    @ApiModelProperty("提醒时间")
    private String notifyDate;

    /**
     * 是不是农历
     */
    @ApiModelProperty("是不是农历")
    private Integer lunarFlag;

    /**
     * 状态（0：停用，1：启用）
     */
    @ApiModelProperty("状态（0：停用，1：启用）")
    private Integer status;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("页大小")
    private Integer pageSize;

    private String userId;

}
