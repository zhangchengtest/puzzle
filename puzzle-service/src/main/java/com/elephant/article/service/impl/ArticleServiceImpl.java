package com.elephant.article.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.article.Article;
import com.elephant.article.mapper.ArticleMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.article.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * Article-服务实现
 *
 * @author cunw generator
 * date 2023-04-14
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends BaseOrmServiceImpl<Article, String> implements ArticleService {
    private final ArticleMapper articleMapper;

    @Override
    protected IBaseMapper<Article, String> getMapper() {
        return this.articleMapper;
    }

}
