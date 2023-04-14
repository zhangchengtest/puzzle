package com.elephant.article.service.impl;

import com.elephant.api.dto.article.ArticleDTO;
import com.elephant.api.vo.article.ArticleVO;
import lombok.RequiredArgsConstructor;
import com.elephant.article.service.ArticleBizService;
import com.elephant.article.service.ArticleHelperService;
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
public class ArticleBizServiceImpl implements ArticleBizService {

    private final ArticleHelperService articleHelperService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final ArticleDTO dto){
        articleHelperService.add(dto);
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