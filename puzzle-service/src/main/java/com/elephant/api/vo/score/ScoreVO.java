package com.elephant.api.vo.score;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 积分表，用于记录用户的积分变动情况-VO
 *
 * @author cunw generator
 * 2023-04-08
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="积分表，用于记录用户的积分变动情况-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreVO implements Serializable {
    /**
     * 积分记录的唯一标识
     */
    @ApiModelProperty("积分记录的唯一标识")
    private String id;

    /**
     * 积分所属的用户 ID
     */
    @ApiModelProperty("积分所属的用户 ID")
    private String userId;

    /**
     * 积分数量
     */
    @ApiModelProperty("积分数量")
    private Integer score;

    /**
     * 状态：1-启用 0-停用 -1-删除
     */
    @ApiModelProperty("状态：1-启用 0-停用 -1-删除")
    private Integer status;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createUserCode;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 修改人	            
     */
    @ApiModelProperty("修改人	            ")
    private String updateUserCode;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateDate;

}
