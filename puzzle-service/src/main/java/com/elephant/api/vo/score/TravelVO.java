package com.elephant.api.vo.score;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class TravelVO implements Serializable {
    /**
     * 积分记录的唯一标识
     */
    private String id;

    /**
     * 积分所属的用户 ID
     */
    private String name;


}
