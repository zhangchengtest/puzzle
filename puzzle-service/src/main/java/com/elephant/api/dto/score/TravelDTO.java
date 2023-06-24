package com.elephant.api.dto.score;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 积分表，用于记录用户的积分变动情况-DTO
 *
 * @author cunw generator
 * date 2023-04-08
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="积分表，用于记录用户的积分变动情况-DTO")
public class TravelDTO implements Serializable {
    /**
     * 积分所属的用户 ID
     */
    private String keyword;


}
