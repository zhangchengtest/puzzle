package com.elephant.article.mapper;

import com.elephant.common.model.article.Article;
import com.cunw.orm.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Article-Mapper
 *
 * @author cunw generator
 * date 2023-04-14
 * 湖南新云网科技有限公司版权所有.
 */
@Mapper
public interface ArticleMapper extends IBaseMapper<Article, String> {

}