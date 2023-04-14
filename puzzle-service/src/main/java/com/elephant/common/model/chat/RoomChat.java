package com.elephant.common.model.chat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.cunw.framework.model.IBasePO;
import java.util.Date;

/**
 * 房间聊天记录表-数据模型
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@TableName("room_chat")
public class RoomChat implements IBasePO<String> {
    /**
     * 消息ID
     */
    @TableId("id")
    private String id;
    /**
     * 房间ID
     */
    private String roomId;
    /**
     * 发送者ID
     */
    private String userId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发送时间
     */
    private Date sendDate;
}
