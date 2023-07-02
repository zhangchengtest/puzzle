package com.elephant.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.service.IBeanMappingService;
import com.cunw.framework.vo.PageList;
import com.cunw.tid.SnowIdGenerator;
import com.elephant.api.api.article.ArticleApi;
import com.elephant.api.dto.article.ArticleDTO;
import com.elephant.api.vo.article.ArticleBatchVO;
import com.elephant.api.vo.article.ArticleVO;
import com.elephant.api.vo.user.UserAuth;
import com.elephant.calendar.service.CalendarService;
import com.elephant.client.UserClient;
import com.elephant.client.dto.UserDTO;
import com.elephant.common.model.article.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.cunw.framework.constant.MarkConstants;
import com.elephant.article.service.ArticleService;
import com.elephant.article.service.ArticleBizService;
import com.elephant.article.service.ArticleHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import com.elephant.common.model.calendar.Calendar;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.ObjectProvider;
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

    private final UserClient userClient;

    private final SnowIdGenerator idGenerator;

    private final CalendarService calendarService;

    @Override
    public ResultVO<Boolean> add(final ArticleDTO dto){
         articleBizService.add(dto);
         return success(true);
    }

    @GetMapping(value = "/test")
    public ResultVO<String> random(){

        LambdaQueryWrapper<Calendar> lambdaQuery = new LambdaQueryWrapper<>();

        // 查找数据库中已存在文章
        List<Calendar> list = calendarService.queryList(lambdaQuery);

        list.stream().forEach(e -> {
            Article article = new Article();
            if(StringUtils.isNotEmpty(e.getRemark())){
                article.setContent(e.getEventDescription() + "\n"+e.getRemark());
            }else {
                article.setContent(e.getEventDescription() );
            }

            article.setTitle(DateUtils.formatDate(e.getNotifyDate(), "yyyy-MM-dd"));
            article.setCategory("大事件");
            article.setChapter(1);
            article.setCreateUserCode("1");
            article.setUpdateUserCode("mytest");
            article.setReadCount(0);
            article.setCreateDate(e.getNotifyDate());
            articleService.add(article);
        });

        return success();
    }

    public static void main(String[] args) {
        String ss = "你好 https://baidu.com/  https://baidu.com/sss.png";
        System.out.println(urlAndImageToMarkdown(ss));
    }


    public static String urlAndImageToMarkdown(String text) {
        String pattern = "\\b(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]*[-A-Za-z0-9+&@#/%=~_|]";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(text);

        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            String url = matcher.group();
            String link = "";
            if (url.endsWith(".jpg") || url.endsWith(".png")) {
                link = "![" + url.substring(url.lastIndexOf('/') + 1) + "](" + url + ")";
            } else {
                link = "[" + url + "](" + url + ")";
            }
            matcher.appendReplacement(sb, link);
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String urltoMovie(String text) {
        String pattern = "\\b(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]*[-A-Za-z0-9+&@#/%=~_|]";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(text);

        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            String url = matcher.group();
            String link = "";
            url = "http://video.punengshuo.com/?url="+url;
            link = "[" + url + "](" + url + ")";
            matcher.appendReplacement(sb, link);
        }

        matcher.appendTail(sb);
        return sb.toString();
    }


    @PostMapping("/add")
    public ResultVO<Boolean> addArticle(@RequestBody ArticleDTO articleDTO) {
        // 获取文章参数
        int chapter = articleDTO.getChapter();
        String title = articleDTO.getTitle();
        String content = articleDTO.getContent();
//        content = urlAndImageToMarkdown(content);
        String category = articleDTO.getCategory();

        LambdaQueryWrapper<Article> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(Article::getCategory, articleDTO.getCategory());
        lambdaQuery.eq(Article::getTitle, articleDTO.getTitle());
        lambdaQuery.eq(Article::getCreateUserCode, articleDTO.getUserId());

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
            newArticle.setCreateUserCode(articleDTO.getUserId());
            articleService.add(newArticle);
        }

        return success(true);

    }

    @GetMapping("/see")
    public  ResultVO<Article> see(@SpringQueryMap ArticleDTO articleDTO) {

        String title = articleDTO.getTitle();
        String category = articleDTO.getCategory();

        LambdaQueryWrapper<Article> lambdaQuery = new LambdaQueryWrapper<>();
        if(title.equals("分类")){

            lambdaQuery.select(Article::getCategory).groupBy(Article::getCategory);
            List<Article> arr = articleService.queryList(lambdaQuery);

            Article old = new Article();
            String content = "";
            for(int i=0; i<arr.size(); i++){
                Article data = arr.get(i);
                content += (i+1) + " " + data.getCategory() + "\n";
                old.setContent(content);
            }
            return success(old);
        }
        final String lastSql = "limit 1";
        lambdaQuery.eq(Article::getCategory, articleDTO.getCategory());
        lambdaQuery.eq(Article::getCreateUserCode, articleDTO.getUserId());
        lambdaQuery.orderByDesc(Article::getCreateDate);
        lambdaQuery.last(lastSql);

        // 查找数据库中已存在文章
        Article old = articleService.getOne(lambdaQuery);


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

    @GetMapping(value = "/list")
    @ApiOperation(value = "查询分页列表", notes = "根据条件查询分页列表")
    public ResultVO<List<ArticleBatchVO>> list(@SpringQueryMap final ArticleDTO dto) {

        LambdaQueryWrapper<Article> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(Article::getCategory, dto.getCategory());
        lambdaQuery.orderByDesc(Article::getCreateDate);

        PageList<Article> pageList = articleService.queryForPage( dto.getPageNum(), dto.getPageSize(), lambdaQuery);

        PageList<ArticleVO> articleVOPageList = mappingService.mapping(pageList, ArticleVO.class);
        List<ArticleVO> articleVOList = articleVOPageList.getList();

        List<String> userCodes = articleVOList.stream().map(e -> e.getCreateUserCode()).collect(Collectors.toList());

        UserDTO userDTO = new UserDTO();
        userDTO.setUserIds(listToString(userCodes));
        List<UserAuth> userAuths = userClient.loadUserByIds(userDTO);
        Map<String, UserAuth> userAuthMap = userAuths.stream().collect(Collectors.toMap(UserAuth::getId, Function.identity()));

        if(StringUtils.equals(dto.getCategory(), "fml") || StringUtils.equals(dto.getCategory(), "damai")){
            List<ArticleBatchVO> result = null;
            result = articleVOList.stream().map(e -> {

                ArticleBatchVO articleBatchVO = new ArticleBatchVO();
                articleBatchVO.setTitle(e.getTitle());
                List<ArticleVO> articleVOList1 = new ArrayList<>();

                articleVOList1.add(e);
                articleBatchVO.setArticles(articleVOList1);
                return articleBatchVO;

            }).collect(Collectors.toList());

            return success(result);
        }

        if(StringUtils.equals(dto.getCategory(), "电影")){
            List<ArticleBatchVO> result = null;
            result = articleVOList.stream().map(e -> {

                ArticleBatchVO articleBatchVO = new ArticleBatchVO();
                articleBatchVO.setTitle(e.getTitle());
                List<ArticleVO> articleVOList1 = new ArrayList<>();

                e.setContent(urltoMovie(e.getContent()));
                articleVOList1.add(e);
                articleBatchVO.setArticles(articleVOList1);
                return articleBatchVO;

            }).collect(Collectors.toList());

            return success(result);
        }


        String end = articleVOList.get(0).getTitle();
        String start = articleVOList.get(articleVOList.size()-1).getTitle();

        List<String> days = getDaysBetweenDesc(parseDate(start), parseDate(end));


//        List<ArticleVO> articleVOList1 = articleVOList.stream().filter(vo -> vo.getTitle().equals(e)).collect(Collectors.toList());

        Map<String,  List<ArticleVO>> mapALL =
                articleVOList.stream()
                        .collect(
                                Collectors.groupingBy(
                                        ArticleVO::getTitle
                                ));

        List<ArticleBatchVO> result = null;
        if(days.size() > 365){

            List<String> dates = new ArrayList<>(mapALL.keySet());
            Collections.sort(dates, (o1, o2) -> {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = formatter.parse(o1);
                    Date date2 = formatter.parse(o2);
                    return date2.compareTo(date1);
                } catch ( ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });

            result = dates.stream().map(e -> {

                ArticleBatchVO articleBatchVO = new ArticleBatchVO();
                articleBatchVO.setTitle(e);

                List<ArticleVO> articleVOList1 = mapALL.get(e);

                if(CollectionUtil.isNotEmpty(articleVOList1)){
                    articleVOList1.stream().forEach(articleVO -> {
                        articleVO.setAvatar( userAuthMap.get(articleVO.getCreateUserCode()).getAvatar());
                    });
                }else{
                    articleVOList1 = new ArrayList<>();
                    ArticleVO articleVO = new ArticleVO();
                    articleVO.setTitle(e);
                    articleVOList1.add(articleVO);
                }
                articleBatchVO.setArticles(articleVOList1);
                return articleBatchVO;

            }).collect(Collectors.toList());

        }else {

           result = days.stream().map(e -> {

                ArticleBatchVO articleBatchVO = new ArticleBatchVO();
                articleBatchVO.setTitle(e);

                List<ArticleVO> articleVOList1 = mapALL.get(e);

                if(CollectionUtil.isNotEmpty(articleVOList1)){
                    articleVOList1.stream().forEach(articleVO -> {
                        articleVO.setAvatar( userAuthMap.get(articleVO.getCreateUserCode()).getAvatar());
                    });
                }else{
                    articleVOList1 = new ArrayList<>();
                    ArticleVO articleVO = new ArticleVO();
                    articleVO.setTitle(e);
                    articleVOList1.add(articleVO);
                }
                articleBatchVO.setArticles(articleVOList1);
                return articleBatchVO;

            }).collect(Collectors.toList());

        }

        return success(result);
    }

    public static String listToString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (String str : list) {
            result.append(str).append(",");
        }
        return result.deleteCharAt(result.length() - 1).toString();
    }


    public LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    public List<String> getDaysBetween(LocalDate startDate, LocalDate endDate) {
        List<String> days = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            days.add(currentDate.format(formatter));
            currentDate = currentDate.plusDays(1);
        }
        return days;
    }

    public List<String> getDaysBetweenDesc(LocalDate startDate, LocalDate endDate) {
        List<String> days = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = endDate;
        while (!currentDate.isBefore(startDate)) {
            days.add(currentDate.format(formatter));
            currentDate = currentDate.minusDays(1);
        }
        return days;
    }
}