package com.elephant.api.vo.clock;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 闹钟完成事件-VO
 *
 * @author cunw generator
 * 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="闹钟完成事件-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClockEventVO implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * id
     */
    @ApiModelProperty("id")
    private String clockId;

    /**
     * 状态（0：停用，1：启用）
     */
    @ApiModelProperty("状态（0：停用，1：启用）")
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
    @ApiModelProperty("修改人")
    private String updateUserCode;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateDate;

}
