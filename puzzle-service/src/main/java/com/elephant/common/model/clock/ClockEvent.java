package com.elephant.common.model.clock;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.cunw.framework.model.IBasePO;
import java.util.Date;

/**
 * 闹钟完成事件-数据模型
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@TableName("clock_event")
public class ClockEvent implements IBasePO<String> {
    /**
     * id
     */
    @TableId("id")
    private String id;
    /**
     * id
     */
    private String clockId;
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
