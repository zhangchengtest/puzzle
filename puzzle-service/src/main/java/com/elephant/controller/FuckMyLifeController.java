package com.elephant.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.utils.base.StringUtils;
import com.cunw.framework.vo.ResultVO;
import com.elephant.api.dto.score.TravelDTO;
import com.elephant.api.vo.score.TravelVO;
import com.elephant.article.service.ArticleService;
import com.elephant.common.model.article.Article;
import com.elephant.utils.HttpRequest;
import com.elephant.utils.ObjectUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/life")
@Slf4j
public class FuckMyLifeController extends BaseController {

    private final ArticleService articleService;

    @GetMapping("/fuck")
    public ResultVO<TravelVO> fuck(@SpringQueryMap final TravelDTO dto) throws IOException {
        log.info("request data {}", ObjectUtils.getJsonStringFromObject(dto));
        String url = "https://www.fmylife.com/";

        log.info(url);

        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("a.text-blue-500");
        for (Element element : elements) {
            log.info(element.text());
            String href = element.attr("href");
            log.info(href);

            Article old = getArticle(href);
            if(old != null){
                continue;
            }

            Article article = new Article();
            article.setContent(element.text() );

            article.setTitle(href);
            article.setCategory("fml");
            article.setChapter(1);
            article.setCreateUserCode("1");
            article.setUpdateUserCode("mytest");
            article.setReadCount(0);
            article.setCreateDate(new Date());
            articleService.add(article);
        }

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
