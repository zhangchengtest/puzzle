package com.elephant.api.dto.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 房间成员表-DTO
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="房间成员表-DTO")
public class RoomMemberDTO implements Serializable {
    /**
     * 房间ID
     */
    @ApiModelProperty("房间ID")
    private String roomId;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private String userId;

}
