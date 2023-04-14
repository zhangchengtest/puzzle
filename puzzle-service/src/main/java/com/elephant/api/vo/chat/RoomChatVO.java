package com.elephant.api.vo.chat;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 房间聊天记录表-VO
 *
 * @author cunw generator
 * 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="房间聊天记录表-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomChatVO implements Serializable {
    /**
     * 消息ID
     */
    @ApiModelProperty("消息ID")
    private String id;

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

    /**
     * 发送时间
     */
    @ApiModelProperty("发送时间")
    private Date sendDate;

}
