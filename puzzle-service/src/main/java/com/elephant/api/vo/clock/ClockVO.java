package com.elephant.api.vo.clock;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 闹钟事件-VO
 *
 * @author cunw generator
 * 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="闹钟事件-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClockVO implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

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

    @ApiModelProperty("截止时间")
    private String deadLine;

    @ApiModelProperty("截止时间")
    private long seconds;

    @ApiModelProperty("完成状态（0：没有，1：完成）")
    private Integer finishStatus;

}
