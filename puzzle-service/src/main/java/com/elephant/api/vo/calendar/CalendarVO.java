package com.elephant.api.vo.calendar;

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
 * 2023-06-18
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="闹钟事件-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalendarVO implements Serializable {
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
    private Date notifyDate;

    /**
     * 是不是农历
     */
    @ApiModelProperty("是不是农历")
    private Integer lunarFlag;

    private String remark;

    /**
     * 状态（0：停用，1：启用）
     */
    @ApiModelProperty("状态（0：停用，1：启用）")
    private Integer status;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createUserCode;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String updateUserCode;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateDate;

}
