package com.elephant.api.vo.game;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * -VO
 *
 * @author cunw generator
 * 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameVO implements Serializable {
    private String id;

    private String title;

    private String playerOne;

    private String playerTwo;

    private String playTime;

    private String score;

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
