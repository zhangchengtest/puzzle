package com.elephant.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.elephant.api.dto.score.TravelDTO;
import com.elephant.api.vo.score.TravelVO;
import com.elephant.article.service.ArticleService;
import com.elephant.common.model.article.Article;
import com.elephant.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.aspectj.weaver.ast.Not;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/damai")
@Slf4j
public class DamaiController extends BaseController {

    private final ArticleService articleService;

    @GetMapping("/fuck")
    public ResultVO<TravelVO> fuck(@SpringQueryMap final TravelDTO dto) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://mtop.damai.cn/h5/mtop.damai.wireless.search.search/1.0/?jsv=2.7.2&appKey=12574478&t=1688184913533&sign=be816649c3cf06bb117ac37caed00914&type=originaljson&dataType=json&v=1.0&H5Request=true&AntiCreep=true&AntiFlood=true&api=mtop.damai.wireless.search.search&requestStart=1688184913531&data=%7B%22cityId%22%3A%220%22%2C%22longitude%22%3A0%2C%22latitude%22%3A0%2C%22pageIndex%22%3A%221%22%2C%22pageSize%22%3A10%2C%22sortType%22%3A%223%22%2C%22categoryId%22%3A%221%22%2C%22startDate%22%3A%22%22%2C%22endDate%22%3A%22%22%2C%22option%22%3A31%2C%22sourceType%22%3A21%2C%22returnItemOption%22%3A4%2C%22dmChannel%22%3A%22damai%40damaih5_h5%22%7D"
                ))

                .GET()
                .setHeader("authority", "mtop.damai.cn")
                .setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .setHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8")
                .setHeader("cache-control", "max-age=0")
                .setHeader("cookie", "cna=LCjiHM2JHkECAQ3k8LuGuxYf; xlly_s=1; _samesite_flag_=true; cookie2=19b904c932279c832230fe6a79fce853; t=0b3b87576bd93051e0a83c7d5525fc5a; _tb_token_=e57e715386ee5; _hvn_login=18; munb=3735517680; csg=571d3a58; damai.cn_nickName=%E9%BA%A6%E5%AD%90; damai.cn_user=zdvRK3gofCnATEdfAnZgKZVG52bfsf4WDEpXCJtiDzYKULL91y5GDAn7rrFk2gvh; damai.cn_user_new=zdvRK3gofCnATEdfAnZgKZVG52bfsf4WDEpXCJtiDzYKULL91y5GDAn7rrFk2gvh; h5token=19f7b709a1ea4735b6cf9a0f9192e886_1_1; damai_cn_user=zdvRK3gofCnATEdfAnZgKZVG52bfsf4WDEpXCJtiDzYKULL91y5GDAn7rrFk2gvh; loginkey=19f7b709a1ea4735b6cf9a0f9192e886_1_1; user_id=90625826; _m_h5_tk=726a33bb11d1ab57c46d6c1cf2b4a341_1688190656699; _m_h5_tk_enc=060e35982a91ebc157f4988010be770c; l=fBT8BkgqN2ZqldLGBO5Znurza77OaQAf1sPzaNbMiIEGa6Mh9F2Z8NC1Kfi6WdtjgT5D-etrip0J_dUkWo4T5NkDBeYQJpMrWpvpJeM3N7AN.; tfstk=dUdpfwTzRcmHwhg-tMHiaH-Vzu0iIBLeWH8bZgj3FhK90nFo8pxkBaKkX28HPDJJXHYXK9YWZaORTCv3ZMmFwUtcwmmmijYe8T5SmmVXazPEisUxDhPp8e5PNPqSndTFyAMDGGdUS5k8F5-XNwM5YO6iREblJ9I8ZGFRCBFcKiNfv7PPvwWwxIAY7fqcDa2IWVezU9_wayDGu; isg=BO3tuInLddOzfxG_Cu52LWQg_IlnSiEc1aKmWS_zYQTzpg1Y95q-7DtkknpAPTnU; _m_h5_tk=0de9042f8e6120a09c3e191ae8610977_1688195437029; _m_h5_tk_enc=937dcaf6e0c9637e138c0bed051e106f")
                .setHeader("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"")
                .setHeader("sec-ch-ua-mobile", "?0")
                .setHeader("sec-ch-ua-platform", "\"Windows\"")
                .setHeader("sec-fetch-dest", "document")
                .setHeader("sec-fetch-mode", "navigate")
                .setHeader("sec-fetch-site", "none")
                .setHeader("sec-fetch-user", "?1")
                .setHeader("upgrade-insecure-requests", "1")
                .setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = (String) response.body();
        System.out.println(responseBody);

        JSONObject obj = JSON.parseObject(responseBody);
        JSONObject obj1 = obj.getJSONObject("data");
        JSONArray jsonArray = obj1.getJSONArray("projectInfo");
        for(int i =0; i < jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject.get("name"));
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
