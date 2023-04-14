package com.elephant.common.model.chat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.cunw.framework.model.IBasePO;
import java.util.Date;

/**
 * 房间表-数据模型
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@TableName("room")
public class Room implements IBasePO<String> {
    /**
     * 房间ID
     */
    @TableId("id")
    private String id;
    /**
     * 房间名称
     */
    private String name;
    /**
     * 房间头像
     */
    private String avatar;
    /**
     * 创建时间
     */
    private Date createDate;
}
