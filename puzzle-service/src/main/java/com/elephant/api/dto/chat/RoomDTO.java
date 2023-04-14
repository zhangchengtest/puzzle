package com.elephant.api.dto.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 房间表-DTO
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="房间表-DTO")
public class RoomDTO implements Serializable {
    /**
     * 房间名称
     */
    @ApiModelProperty("房间名称")
    private String name;

    /**
     * 房间头像
     */
    @ApiModelProperty("房间头像")
    private String avatar;

}
