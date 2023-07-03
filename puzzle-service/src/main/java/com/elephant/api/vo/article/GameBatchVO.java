package com.elephant.api.vo.article;

import com.elephant.api.vo.game.GameVO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * -VO
 *
 * @author cunw generator
 * 2023-04-14
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameBatchVO implements Serializable {

    private List<GameVO> games;

    private String title;

}
