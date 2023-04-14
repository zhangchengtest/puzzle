package com.elephant.api.vo.chat;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 房间表-VO
 *
 * @author cunw generator
 * 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="房间表-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomVO implements Serializable {
    /**
     * 房间ID
     */
    @ApiModelProperty("房间ID")
    private String id;

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

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

}
