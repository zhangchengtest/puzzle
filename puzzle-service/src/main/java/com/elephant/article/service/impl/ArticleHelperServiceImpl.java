package com.elephant.article.service.impl;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.dto.article.ArticleDTO;
import com.elephant.api.vo.article.ArticleVO;
import com.elephant.common.model.article.Article;
import lombok.RequiredArgsConstructor;
import com.elephant.article.service.ArticleHelperService;
import com.elephant.article.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * Article-服务接口
 *
 * @author cunw generator
 * date 2023-04-14
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class ArticleHelperServiceImpl implements ArticleHelperService {

    private final IBeanMappingService mappingService;
    private final ArticleService articleService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final ArticleDTO dto){
        final Article po = mappingService.mapping(dto, Article.class);
        articleService.add(po);
    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public ArticleVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final ArticleDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}