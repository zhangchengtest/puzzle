package com.elephant.api.dto.puzzle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * -DTO
 *
 * @author cunw generator
 * date 2023-04-09
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="-DTO")
public class PuzzleRankDTO implements Serializable {
    /**
     * 用户 ID
     */
    @ApiModelProperty("用户 ID")
    private String userId;

    private String username;

    private String title;

    private String url;

    private Integer spendTime;

    private Integer step;

    /**
     * 状态：1-启用 0-停用 -1-删除
     */
    @ApiModelProperty("状态：1-启用 0-停用 -1-删除")
    private Integer status;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("页大小")
    private Integer pageSize;
}
