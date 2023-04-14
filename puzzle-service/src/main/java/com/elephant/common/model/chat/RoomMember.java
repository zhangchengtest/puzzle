package com.elephant.common.model.chat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.cunw.framework.model.IBasePO;
import java.util.Date;

/**
 * 房间成员表-数据模型
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@TableName("room_member")
public class RoomMember implements IBasePO<String> {
    /**
     * 成员ID
     */
    @TableId("id")
    private String id;
    /**
     * 房间ID
     */
    private String roomId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 加入时间
     */
    private Date createDate;
}
