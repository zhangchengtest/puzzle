package com.elephant.api.dto.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 房间聊天记录表-DTO
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="房间聊天记录表-DTO")
public class RoomChatDTO implements Serializable {
    /**
     * 房间ID
     */
    @ApiModelProperty("房间ID")
    private String roomId;

    /**
     * 发送者ID
     */
    @ApiModelProperty("发送者ID")
    private String userId;

    /**
     * 消息内容
     */
    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("页大小")
    private Integer pageSize;
}
