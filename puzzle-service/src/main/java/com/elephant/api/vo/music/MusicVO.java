package com.elephant.api.vo.music;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * -VO
 *
 * @author cunw generator
 * 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicVO implements Serializable {
    private String id;

    private String name;

    private AlbumVO album;

    private List<ArtistVO> artists;

    private Integer duration;

}
