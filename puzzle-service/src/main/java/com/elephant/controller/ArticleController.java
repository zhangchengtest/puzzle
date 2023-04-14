package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.service.IBeanMappingService;
import com.cunw.framework.vo.PageList;
import com.elephant.api.api.article.ArticleApi;
import com.elephant.api.dto.article.ArticleDTO;
import com.elephant.api.vo.article.ArticleVO;
import com.elephant.common.model.article.Article;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.article.service.ArticleService;
import com.elephant.article.service.ArticleBizService;
import com.elephant.article.service.ArticleHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * ArticleController
 *
 * @author cunw generator
 * date 2023-04-14
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController extends BaseController implements ArticleApi {
    private final ArticleService articleService;
    private final ArticleHelperService articleHelperService;
    private final ArticleBizService articleBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final ArticleDTO dto){
         articleBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<ArticleVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final Article po = articleService.getById(id);
        final ArticleVO vo = mappingService.mapping(po, ArticleVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<ArticleVO> update(final String id, final ArticleDTO dto){
        Article po = mappingService.mapping(dto, Article.class);
        articleService.modify(id, po);
        po = articleService.getById(id);
        final ArticleVO vo = mappingService.mapping(po, ArticleVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            articleService.delete(id);
        } else {
            articleService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "查询分页列表", notes = "根据条件查询分页列表")
    public ResultVO<PageList<ArticleVO>> page(@SpringQueryMap final ArticleDTO dto) {

        LambdaQueryWrapper<Article> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(Article::getCategory, dto.getCategory());
        lambdaQuery.orderByDesc(Article::getCreateDate);

        PageList<Article> pageList = articleService.queryForPage( dto.getPageNum(), dto.getPageSize(), lambdaQuery);

        PageList<ArticleVO> articleVOPageList = mappingService.mapping(pageList, ArticleVO.class);
        return success(articleVOPageList);
    }
}