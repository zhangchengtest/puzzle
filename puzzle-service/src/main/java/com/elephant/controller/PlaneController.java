package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.elephant.api.dto.score.TravelDTO;
import com.elephant.api.vo.score.TravelVO;
import com.elephant.article.service.ArticleService;
import com.elephant.common.model.article.Article;
import com.elephant.utils.HttpRequest;
import com.elephant.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plane")
@Slf4j
public class PlaneController extends BaseController {

    private final ArticleService articleService;

    @GetMapping("/fuck")
    public ResultVO<TravelVO> fuck(@SpringQueryMap final TravelDTO dto) throws IOException {
        log.info("request data {}", ObjectUtils.getJsonStringFromObject(dto));
        String url = "https://www.omio.cn/growth/search-trigger/id";

        String data = HttpRequest.sendAuthPost(url, null, null);

        log.info(url);


        TravelVO travelVO = new TravelVO();
        return success(travelVO);
    }

    public Article getArticle(String title){
        LambdaQueryWrapper<Article> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(Article::getTitle, title);

        // 查找数据库中已存在文章
        Article old = articleService.getOne(lambdaQuery);
        return old;
    }


}
