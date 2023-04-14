package com.elephant.article.service;

import com.elephant.api.dto.article.ArticleDTO;
import com.elephant.api.vo.article.ArticleVO;

/**
 * Article-服务接口
 *
 * @author cunw generator
 * date 2023-04-14
 * 湖南新云网科技有限公司版权所有.
 */

public interface ArticleBizService {

    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    void add(final ArticleDTO dto);

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    ArticleVO get(final String id);

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    void update(final String id, final ArticleDTO dto);

    /**
     * 公共删除
     * @param id 主键ID
     */
    void delete(final String id);
}