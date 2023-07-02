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
import org.apache.commons.lang.StringUtils;
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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/damai")
@Slf4j
public class DamaiController extends BaseController {

    private final ArticleService articleService;

    String _m_h5_tk = "";
    String _m_h5_tk_enc = "";

    Integer page = 1;

    String token = "hhhhh";
    private HttpResponse req(Integer pageIndex) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        String appKey = "12574478";
        String data = "{\"cityId\":\"0\",\"longitude\":0,\"latitude\":0,\"pageIndex\":\""+ pageIndex +"\"," +
                "\"pageSize\":10,\"sortType\":\"3\",\"categoryId\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"option\":31,\"sourceType\":21,\"returnItemOption\":4,\"dmChannel\":\"damai@damaih5_h5\"}";

        long timestamp = System.currentTimeMillis(); //1688227441531
        String sign = generateSign(token, String.valueOf(timestamp), appKey, data);
        data = URLEncoder.encode(data, "utf-8");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://mtop.damai.cn/h5/mtop.damai.wireless.search.search/1.0/?jsv=2.7.2&appKey=12574478" +
                                "&t="+ timestamp+
                                "&sign=" +sign+
                                "&type=originaljson&dataType=json&v=1.0&H5Request=true&AntiCreep=true&AntiFlood=true&api=mtop.damai.wireless.search.search&" +
                                "requestStart=" + timestamp+
                                "&data="+data
                ))
                .GET()
//                .setHeader("authority", "mtop.damai.cn")
//                .setHeader("accept", "application/json")
//                .setHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8")
//                .setHeader("content-type", "application/x-www-form-urlencoded")

                .setHeader("cookie", "cna=LCjiHM2JHkECAQ3k8LuGuxYf; xlly_s=1; _samesite_flag_=true;" +
                        " cookie2=19b904c932279c832230fe6a79fce853; " +
                        "t=0b3b87576bd93051e0a83c7d5525fc5a; " +
                        "_tb_token_=e57e715386ee5; _hvn_login=18; munb=3735517680; " +
                        "csg=571d3a58; damai.cn_nickName=%E9%BA%A6%E5%AD%90; " +
                        "damai.cn_user=zdvRK3gofCnATEdfAnZgKZVG52bfsf4WDEpXCJtiDzYKULL91y5GDAn7rrFk2gvh; " +
                        "damai.cn_user_new=zdvRK3gofCnATEdfAnZgKZVG52bfsf4WDEpXCJtiDzYKULL91y5GDAn7rrFk2gvh; " +
                        "h5token=19f7b709a1ea4735b6cf9a0f9192e886_1_1;" +
                        " damai_cn_user=zdvRK3gofCnATEdfAnZgKZVG52bfsf4WDEpXCJtiDzYKULL91y5GDAn7rrFk2gvh; " +
                        "loginkey=19f7b709a1ea4735b6cf9a0f9192e886_1_1; user_id=90625826; " +
                        "l=fBT8BkgqN2Zql3OYBOfZFurza77OSIRxjuPzaNbMi9fPOh1p5hK1W16nvu89C3hVFsOkR3lDK4dwBeYBqgI-nxv951xKbckmnmOk-Wf..; " +
                        "tfstk=dqBkQpff3_R7WUPnyUp7eKG119FvF49BswHpJpLUgE8jy4HRYHvhkwjJTzsdo06cSvH8P7sEKppUWPeTBgsJdpzTCy2OVbsMe4aTBRI7a39d0PUQnFrlUtznNKzNhWdwSDYp6X8gdCLN4EjR2t-oWFSyovkew3XZ3jlwQBHB0kBqOXOycnYtdIiwe; " +
//                        "_m_h5_tk=38e45c868ef39aae89245448f4ed342b_1688228071273; " +
//                        "_m_h5_tk_enc=5901ed84baf1f69e23418dbfc38e8c2b;" +
                        "_m_h5_tk="+_m_h5_tk+"; " +
                        "_m_h5_tk_enc="+_m_h5_tk_enc+";" +
                        " isg=BAAA_zCtcFzDuwxYv82TElmT0Y7SieRT-LEb_nqTypuu9aEfIpjC42CEDV01xZwr")

//                .setHeader("origin", "https://m.damai.cn")
//                .setHeader("referer", "https://m.damai.cn/")
//                .setHeader("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"")
//                .setHeader("sec-ch-ua-mobile", "?0")
//                .setHeader("sec-ch-ua-platform", "\"Windows\"")
//                .setHeader("sec-fetch-dest", "empty")
//                .setHeader("sec-fetch-mode", "cors")
//                .setHeader("sec-fetch-site", "same-site")
//                .setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")

                .setHeader("authority", "mtop.damai.cn")
                .setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .setHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8")
                .setHeader("cache-control", "max-age=0")
//
//                .setHeader("cookie", "cna=LCjiHM2JHkECAQ3k8LuGuxYf; xlly_s=1; _samesite_flag_=true; cookie2=19b904c932279c832230fe6a79fce853; t=0b3b87576bd93051e0a83c7d5525fc5a; _tb_token_=e57e715386ee5; _hvn_login=18; munb=3735517680; csg=571d3a58; damai.cn_nickName=%E9%BA%A6%E5%AD%90; damai.cn_user=zdvRK3gofCnATEdfAnZgKZVG52bfsf4WDEpXCJtiDzYKULL91y5GDAn7rrFk2gvh; damai.cn_user_new=zdvRK3gofCnATEdfAnZgKZVG52bfsf4WDEpXCJtiDzYKULL91y5GDAn7rrFk2gvh; h5token=19f7b709a1ea4735b6cf9a0f9192e886_1_1; damai_cn_user=zdvRK3gofCnATEdfAnZgKZVG52bfsf4WDEpXCJtiDzYKULL91y5GDAn7rrFk2gvh; loginkey=19f7b709a1ea4735b6cf9a0f9192e886_1_1; user_id=90625826; _m_h5_tk=726a33bb11d1ab57c46d6c1cf2b4a341_1688190656699; _m_h5_tk_enc=060e35982a91ebc157f4988010be770c; l=fBT8BkgqN2ZqldLGBO5Znurza77OaQAf1sPzaNbMiIEGa6Mh9F2Z8NC1Kfi6WdtjgT5D-etrip0J_dUkWo4T5NkDBeYQJpMrWpvpJeM3N7AN.; tfstk=dUdpfwTzRcmHwhg-tMHiaH-Vzu0iIBLeWH8bZgj3FhK90nFo8pxkBaKkX28HPDJJXHYXK9YWZaORTCv3ZMmFwUtcwmmmijYe8T5SmmVXazPEisUxDhPp8e5PNPqSndTFyAMDGGdUS5k8F5-XNwM5YO6iREblJ9I8ZGFRCBFcKiNfv7PPvwWwxIAY7fqcDa2IWVezU9_wayDGu; isg=BO3tuInLddOzfxG_Cu52LWQg_IlnSiEc1aKmWS_zYQTzpg1Y95q-7DtkknpAPTnU; _m_h5_tk=0de9042f8e6120a09c3e191ae8610977_1688195437029; _m_h5_tk_enc=937dcaf6e0c9637e138c0bed051e106f")

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

        return response;
    }

    @GetMapping("/fuck")
    public ResultVO<TravelVO> fuck(@SpringQueryMap final TravelDTO dto) throws IOException, InterruptedException {
        page = 1;
        for(int dd = 0; dd < 3; dd++){
            realFuck();
        }
        page = 1;

        TravelVO travelVO = new TravelVO();
        return success(travelVO);
    }

    public void realFuck() throws IOException, InterruptedException {
        HttpResponse response = req(page);
        String responseBody = (String) response.body();

        log.info(responseBody);
        if(!responseBody.contains("projectInfo")){

            Map<String, List<String>> headers = response.headers().map();
            List<String> cookies = headers.get("Set-Cookie");

            for(String cookie : cookies){
                if(StringUtils.isNotBlank(getCookieValue("_m_h5_tk", cookie))){
                    _m_h5_tk = getCookieValue("_m_h5_tk", cookie);
                    token = _m_h5_tk.split("_")[0];
                }
                if(StringUtils.isNotBlank(getCookieValue("_m_h5_tk_enc", cookie))){
                    _m_h5_tk_enc = getCookieValue("_m_h5_tk_enc", cookie);
                }
            }
            cookies.stream().forEach(e ->{
                log.info(e);
            });

            response = req(page);
            responseBody = (String) response.body();

            save(responseBody);
        }else {
            save(responseBody);
        }
    }

    public void save(String responseBody){
        JSONObject obj = JSON.parseObject(responseBody);
        JSONObject obj1 = obj.getJSONObject("data");
        JSONArray jsonArray = obj1.getJSONArray("projectInfo");
        for(int i =0; i < jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = "演唱会-"+jsonObject.getString("name");
            String priceStr = jsonObject.getString("priceStr");
            String cityName = jsonObject.getString("cityName");
            String showTime = jsonObject.getString("showTime");
            String actores = jsonObject.getString("actores");

            log.info(name);
            String content =
                    priceStr +"\n"
                    +cityName +"\n"
                    +showTime +"\n"
                    +actores +"\n";

            Article old = getArticle(name);
            if(old != null){
                continue;
            }

            Article article = new Article();
            article.setContent(content);

            article.setTitle(name);
            article.setCategory("damai");
            article.setChapter(1);
            article.setCreateUserCode("1");
            article.setUpdateUserCode("mytest");
            article.setReadCount(0);
            article.setCreateDate(new Date());
            articleService.add(article);
        }
        page++;
    }

    public Article getArticle(String title){
        LambdaQueryWrapper<Article> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(Article::getTitle, title);

        // 查找数据库中已存在文章
        Article old = articleService.getOne(lambdaQuery);
        return old;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
//        String token = "ce41b196f080310855dd7c72a53acb8a";
//        String appKey = "12574478";
//        String data = "{\"cityId\":\"0\",\"longitude\":0,\"latitude\":0,\"pageIndex\":\"1\",\"pageSize\":10,\"sortType\":\"3\",\"categoryId\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"option\":31,\"sourceType\":21,\"returnItemOption\":4,\"dmChannel\":\"damai@damaih5_h5\"}";
//
//        long timestamp = System.currentTimeMillis();
//        log.info(timestamp+"");
//        String sign = generateSign(token, String.valueOf(timestamp), appKey, data);
//
//        log.info(sign);
////
//        String cookieString = "_m_h5_tk_enc=c530b89bfa2d676da678d4551402d02f;Path=/;Domain=damai.cn;Max-Age=604800;SameSite=None;Secure";
//        String cookieName = "_m_h5_tk";
//
//        String cookieValue = getCookieValue(cookieName, cookieString);
//        log.info("Cookie Value: " + cookieValue);

        String data = "{\"cityId\":\"0\",\"longitude\":0,\"latitude\":0,\"pageIndex\":\"1\",\"pageSize\":10,\"sortType\":\"3\",\"categoryId\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"option\":31,\"sourceType\":21,\"returnItemOption\":4,\"dmChannel\":\"damai@damaih5_h5\"}";
        System.out.println(URLEncoder.encode(data, "utf-8"));
    }


    public static String getCookieValue(String name, String cookieString) {
        String patternString = "(?:^|;\\s*)" + name + "\\=([^;]+)(?:;\\s*|$)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(cookieString);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }


    public static String generateSign(String token, String timestamp, String appKey, String data) {
        String input = token + "&" + timestamp + "&" + appKey + "&" + data;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(input.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                stringBuilder.append(String.format("%02x", b & 0xff));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
