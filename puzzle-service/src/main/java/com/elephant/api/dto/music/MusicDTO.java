package com.elephant.api.dto.music;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * -DTO
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="-DTO")
public class MusicDTO implements Serializable {
    private String musicName;

    private Integer albumId;

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
