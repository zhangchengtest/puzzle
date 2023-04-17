package com.elephant.api.vo.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
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
public class ArticleBatchVO implements Serializable {

    private List<ArticleVO> articles;

    private String title;

}
