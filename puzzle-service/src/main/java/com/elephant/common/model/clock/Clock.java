package com.elephant.common.model.clock;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.cunw.framework.model.IBasePO;
import java.util.Date;

/**
 * 闹钟事件-数据模型
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@TableName("clock")
public class Clock implements IBasePO<String> {
    /**
     * id
     */
    @TableId("id")
    private String id;
    /**
     * 0日历,1按天,2每月(1-31),3每年(MM dd)，4按周
     */
    private Integer eventType;
    /**
     * 事件描述
     */
    private String eventDescription;
    /**
     * 提醒时间
     */
    private String notifyDate;
    /**
     * 是不是农历
     */
    private Integer lunarFlag;
    /**
     * 状态（0：停用，1：启用）
     */
    private Integer status;
    /**
     * 创建人
     */
    private String createUserCode;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改人
     */
    private String updateUserCode;
    /**
     * 修改时间
     */
    private Date updateDate;
}
