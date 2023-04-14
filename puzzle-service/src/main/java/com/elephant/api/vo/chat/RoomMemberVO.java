package com.elephant.api.vo.chat;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 房间成员表-VO
 *
 * @author cunw generator
 * 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="房间成员表-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomMemberVO implements Serializable {
    /**
     * 成员ID
     */
    @ApiModelProperty("成员ID")
    private String id;

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

    /**
     * 加入时间
     */
    @ApiModelProperty("加入时间")
    private Date createDate;

}
