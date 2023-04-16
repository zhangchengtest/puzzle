package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.service.IBeanMappingService;
import com.cunw.framework.vo.PageList;
import com.cunw.tid.SnowIdGenerator;
import com.elephant.api.api.article.ArticleApi;
import com.elephant.api.dto.article.ArticleDTO;
import com.elephant.api.vo.article.ArticleVO;
import com.elephant.common.model.article.Article;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import com.cunw.framework.constant.MarkConstants;
import com.elephant.article.service.ArticleService;
import com.elephant.article.service.ArticleBizService;
import com.elephant.article.service.ArticleHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final SnowIdGenerator idGenerator;

    @Override
    public ResultVO<Boolean> add(final ArticleDTO dto){
         articleBizService.add(dto);
         return success(true);
    }


    @PostMapping("/add")
    public ResultVO<Boolean> addArticle(@RequestBody ArticleDTO articleDTO) {
        // 获取文章参数
        int chapter = articleDTO.getChapter();
        String title = articleDTO.getTitle();
        String content = articleDTO.getContent();
        String category = articleDTO.getCategory();

        LambdaQueryWrapper<Article> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(Article::getCategory, articleDTO.getCategory());
        lambdaQuery.eq(Article::getTitle, articleDTO.getTitle());

        // 查找数据库中已存在文章
        Article old = articleService.getOne(lambdaQuery);

        if (old != null) {
            // 如果已存在，则将新的内容追加到文章末尾
            String oldContent = old.getContent();
            String newContent = oldContent + "\n" + content;
            old.setContent(newContent);
            articleService.modify(old.getId(), old);
        } else {
            // 如果不存在，则创建新的文章
            String newId = idGenerator.getNextStr(); // 使用idWorker生成新文章的id
            Article newArticle = new Article();
            newArticle.setId(newId);
            newArticle.setChapter(chapter);
            newArticle.setTitle(title);
            newArticle.setContent(content);
            newArticle.setReadCount(0);
            newArticle.setCategory(category);
            articleService.add(newArticle);
        }

        return success(true);

    }

    @GetMapping("/see")
    public  ResultVO<Article> seeDinary(@SpringQueryMap ArticleDTO articleDTO) {

        String title = articleDTO.getTitle();
        String category = articleDTO.getCategory();
        LambdaQueryWrapper<Article> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(Article::getCategory, articleDTO.getCategory());
        lambdaQuery.eq(Article::getTitle, articleDTO.getTitle());

        // 查找数据库中已存在文章
        Article old = articleService.getOne(lambdaQuery);

        if (old == null) {
            LocalDate date = LocalDate.parse(title);
            date = date.minusDays(1);
            String previousTitle = date.toString();

            lambdaQuery = new LambdaQueryWrapper<>();

            lambdaQuery.eq(Article::getCategory, articleDTO.getCategory());
            lambdaQuery.eq(Article::getTitle, previousTitle);

            old = articleService.getOne(lambdaQuery);

            if (old == null) {
                String message = "Article not found with title " + title + " and category " + category;
                return success(old);
            }
        }

        if (category.equals("行程")) {
            String[] arr = old.getContent().split("\n");
            StringBuilder contentBuilder = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                contentBuilder.append(i + 1).append(" ").append(arr[i]).append("\n");
            }
            old.setContent(contentBuilder.toString());
        } else if (category.equals("日记")) {
            String data = old.getContent().replaceAll("\n", "").replaceAll(" ", "");
            int length = data.codePointCount(0, data.length());
            old.setContent(old.getContent() + " " + length);
        }

        return success(old);
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