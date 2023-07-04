package com.elephant.controller;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.controller.BaseController;
import com.cunw.boot.service.IBeanMappingService;
import com.cunw.framework.utils.base.StringUtils;
import com.cunw.framework.utils.time.DateUtils;
import com.cunw.framework.vo.PageList;
import com.cunw.framework.vo.ResultVO;
import com.elephant.api.dto.game.GameDTO;
import com.elephant.api.dto.score.TravelDTO;
import com.elephant.api.vo.article.GameBatchVO;
import com.elephant.api.vo.clock.ClockVO;
import com.elephant.api.vo.game.GameVO;
import com.elephant.api.vo.score.TravelVO;
import com.elephant.api.vo.user.UserAuth;
import com.elephant.chess.service.ChessGameService;
import com.elephant.client.dto.UserDTO;
import com.elephant.common.model.game.Game;
import com.elephant.common.model.game.Game;
import com.elephant.common.model.game.GameComment;
import com.elephant.game.service.GameCommentService;
import com.elephant.game.service.GameService;
import com.elephant.utils.ObjectUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tennis")
@Slf4j
public class TennisController extends BaseController {

    private final GameService gameService;

    private final IBeanMappingService mappingService;

    private final GameCommentService gameCommentService;



    @GetMapping("/fuck")
    public ResultVO<TravelVO> fuck(@SpringQueryMap final TravelDTO dto) throws IOException {
        log.info("request data {}", ObjectUtils.getJsonStringFromObject(dto));
        Date now = new Date();
        String ddd = DateUtils.formatDate(now, "yyyy-MM-dd");
        String url = "https://www.rank-tennis.com/zh/result/"+ddd;

        log.info(url);

        Document doc = Jsoup.connect(url).get();

        Elements elements = doc.select("div.cResultTourInfoCity");
        Elements contents = doc.select("div.cResultTourContent");

        List<String> needed = new ArrayList<>();
        needed.add("温网");
        needed.add("W40 香港");
        needed.add("CH75 米兰");


        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            log.info("\n\n------------game-------------");

            String title = element.text();
            System.out.println(title);
            if(!needed.contains(title)){
                continue;
            }
            log.info(title);
            Element content = contents.get(i);
            Elements matches = content.select(".cResultMatch");
            for (Element match : matches) {

                Elements trElements = match.select(".cResultMatchTime");
                String time = trElements.get(0).text();
                long dd = NumberUtils.toLong(time);
                Date date = new Date(dd*1000l);
                String day = DateUtils.formatDate(date, "yyyy-MM-dd");


//                getComment(match);

                String history = getHistory(match);

                Elements tdElements = match.select("td");
                log.info("\n\n------------player-------------");
                Element tdElement1 = tdElements.get(0);  // 在每个<tr>标签中，使用选择器选择所有<td>标签
                String alt1 = "";
                if(CollectionUtils.isNotEmpty(tdElement1.select("img"))){
                    alt1 = tdElement1.select("img").get(0).attr("alt");
                }

                String tdText1 = getName(tdElement1);
                String odd1 = getOdd(tdElement1);

                List<String> scores1 = getScore(tdElement1);

                Element tdElement2 = tdElements.get(1);  // 在每个<tr>标签中，使用选择器选择所有<td>标签
                String tdText2 = getName(tdElement2);
                String alt2 = "";
                if(CollectionUtils.isNotEmpty(tdElement2.select("img"))){
                    alt2 = tdElement2.select("img").get(0).attr("alt");
                }

                List<String> scores2 = getScore(tdElement2);

                String odd2 = getOdd(tdElement2);

                Game old = getGame(title, tdText1, tdText2);
                if(old != null){
                    Game game = new Game();
                    game.setId(old.getId());
                    game.setTitle(title);
                    game.setPlayDay(day);
                    game.setPlayerOne(tdText1);
                    if(StringUtils.isNotEmpty(alt1)){
                        game.setCountryOne(alt1);
                    }else {
                        game.setCountryOne("no");
                    }

                    if(StringUtils.isNotEmpty(alt2)){
                        game.setCountryTwo(alt2);
                    }else {
                        game.setCountryTwo("no");
                    }

                    game.setPlayDate(date);


                    game.setOneScore1(scores1.get(0));
                    game.setOneScore2(scores1.get(1));
                    game.setOneScore3(scores1.get(2));
                    game.setOneScore4(scores1.get(3));
                    game.setOneScore5(scores1.get(4));
                    game.setOneScore6(scores1.get(5));
                    game.setTwoScore1(scores2.get(0));
                    game.setTwoScore2(scores2.get(1));
                    game.setTwoScore3(scores2.get(2));
                    game.setTwoScore4(scores2.get(3));
                    game.setTwoScore5(scores2.get(4));
                    game.setTwoScore6(scores2.get(5));

                    game.setPlayerOrder1(getPlayerOrder(tdElement1));
                    game.setPlayerOrder2(getPlayerOrder(tdElement2));

                    game.setOdd1(odd1);
                    game.setOdd2(odd2);

                    game.setHistory(history);

                    game.setCreateUserCode("1");
                    game.setUpdateUserCode("mytest");
                    gameService.modify(game);

                }else{
                    Game game = new Game();
                    game.setTitle(title);
                    game.setPlayDay(day);
                    game.setPlayerOne(tdText1);
                    if(StringUtils.isNotEmpty(alt1)){
                        game.setCountryOne(alt1);
                    }else {
                        game.setCountryOne("no");
                    }

                    if(StringUtils.isNotEmpty(alt2)){
                        game.setCountryTwo(alt2);
                    }else {
                        game.setCountryTwo("no");
                    }
                    game.setPlayerTwo(tdText2);


                    game.setPlayDate(date);


                    game.setOneScore1(scores1.get(0));
                    game.setOneScore2(scores1.get(1));
                    game.setOneScore3(scores1.get(2));
                    game.setOneScore4(scores1.get(3));
                    game.setOneScore5(scores1.get(4));
                    game.setOneScore6(scores1.get(5));
                    game.setTwoScore1(scores2.get(0));
                    game.setTwoScore2(scores2.get(1));
                    game.setTwoScore3(scores2.get(2));
                    game.setTwoScore4(scores2.get(3));
                    game.setTwoScore5(scores2.get(4));
                    game.setTwoScore6(scores2.get(5));

                    game.setPlayerOrder1(getPlayerOrder(tdElement1));
                    game.setPlayerOrder2(getPlayerOrder(tdElement2));

                    game.setOdd1(odd1);
                    game.setOdd2(odd2);

                    game.setHistory(history);

                    game.setCreateUserCode("1");
                    game.setUpdateUserCode("mytest");
                    game.setCreateDate(new Date());
                    gameService.add(game);
                }

            }
        }

        TravelVO travelVO = new TravelVO();
        return success(travelVO);
    }

    private void getComment(Element trElement){
        String odd = "";
        Elements matches2 = trElement.select(".cResultMatchMidPointFlag");
        if(CollectionUtils.isNotEmpty(matches2)){
            for(Element element: matches2){
                if(StringUtils.isNotEmpty(element.wholeOwnText().trim())){
                    odd = element.wholeOwnText().trim();
                    log.info(odd);
                }
            }
        }
    }

    private String getHistory(Element trElement){
        String odd = "";
        Elements matches2 = trElement.select(".cResultMatchH2H");
        if(CollectionUtils.isNotEmpty(matches2)){
            for(Element element: matches2){
                if(StringUtils.isNotEmpty(element.text().trim())){
                    odd = element.text().trim();
                    log.info(odd);
                }
            }
        }
        return odd;
    }

//    private String getTd(Element tdElement2){
//        String tdText2 = tdElement2.wholeOwnText().trim();
//        Elements matches = tdElement2.select("sub");
//        if(CollectionUtils.isNotEmpty(matches)){
//            for(Element element: matches){
//                tdText2 = tdText2 +" " + element.wholeOwnText().trim();
//            }
//        }
//
//        return tdText2;
//    }

    private String getName(Element tdElement2){
        String tdText2 = tdElement2.wholeOwnText().trim();
        return tdText2;
    }


    private String getPlayerOrder(Element tdElement2){
        String tdText2 = "";
        Elements matches = tdElement2.select("sub");
        if(CollectionUtils.isNotEmpty(matches)){
            for(Element element: matches){
                tdText2 = tdText2 +" " + element.wholeOwnText().trim();
            }
        }
        return tdText2;
    }

    private String getOdd(Element tdElement2){
        String odd = "";
        Elements matches2 = tdElement2.select("odds");
        if(CollectionUtils.isNotEmpty(matches2)){
            for(Element element: matches2){
                odd = element.wholeOwnText().trim();
            }
        }

        return odd;
    }


    private List<String> getScore(Element tdElement2){
        Elements matches = tdElement2.children();
        Element need = null;
        for(Element element: matches){
            if (element.tagName().equals("div")) {
                need = element;
                break;
            }
        }
        Elements matches2 = need.children();

        List<String> scores = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(matches2)){
            for(Element element: matches2){
                if(StringUtils.isNotEmpty(element.text())){
                    log.info("stop");

                    String ss = element.text().trim();

                    if(CollectionUtils.isNotEmpty(element.select(".loser"))){
                        ss = element.select(".loser").get(0).wholeOwnText();
                        if(CollectionUtils.isNotEmpty(element.select(".loser").get(0).select("sup"))){
                            ss = ss+ "-"+ element.select(".loser").get(0).select("sup").get(0).wholeOwnText();
                        }
                    }
                    scores.add(ss);
                }else {
                    scores.add("0");
                }
            }
        }

        return scores;
    }
    public Game getGame(String title, String player1, String player2){
        LambdaQueryWrapper<Game> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(Game::getTitle, title);
        lambdaQuery.eq(Game::getPlayerOne, player1);
        lambdaQuery.eq(Game::getPlayerTwo, player2);

        // 查找数据库中已存在文章
        Game old = gameService.getOne(lambdaQuery);
        return old;
    }

    public static void main(String[] args) {
        //1688290859060
        //1688306400
        Date date = new Date(1688306400*1000l);

    }


    @GetMapping(value = "/page")
    @ApiOperation(value = "查询分页列表", notes = "根据条件查询分页列表")
    public ResultVO<List<GameBatchVO>> list(@SpringQueryMap final GameDTO dto) {

        List<String> needed = new ArrayList<>();
        needed.add("温网");

        LambdaQueryWrapper<Game> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.in(Game::getTitle, needed);
        lambdaQuery.orderByDesc(Game::getCreateDate);

        PageList<Game> pageList = gameService.queryForPage(dto.getPageNum(), dto.getPageSize(), lambdaQuery);

        List<Game> gameList = pageList.getList();

        List<GameVO> gameVOList2 = gameList.stream().map( e-> {
            GameVO gameVO = new GameVO();
            gameVO.setTitle(e.getTitle()+e.getPlayDay());
            gameVO.setPlayerOne(e.getPlayerOne());
            gameVO.setPlayerTwo(e.getPlayerTwo());
            gameVO.setPlayerOrder1(e.getPlayerOrder1());
            gameVO.setPlayerOrder2(e.getPlayerOrder2());
            gameVO.setCountryOne(e.getCountryOne());
            gameVO.setCountryTwo(e.getCountryTwo());
            gameVO.setPlayTime(DateUtils.formatDate(e.getPlayDate(), "HH:mm"));

            gameVO.setHistory(e.getHistory());


            gameVO.setOneScore1(e.getOneScore1());
            gameVO.setOneScore2(e.getOneScore2());
            gameVO.setOneScore3(e.getOneScore3());
            gameVO.setOneScore4(e.getOneScore4());
            gameVO.setOneScore5(e.getOneScore5());
            gameVO.setOneScore6(e.getOneScore6());
            gameVO.setTwoScore1(e.getTwoScore1());
            gameVO.setTwoScore2(e.getTwoScore2());
            gameVO.setTwoScore3(e.getTwoScore3());
            gameVO.setTwoScore4(e.getTwoScore4());
            gameVO.setTwoScore5(e.getTwoScore5());
            gameVO.setTwoScore6(e.getTwoScore6());

            gameVO.setOdd1(e.getOdd1());
            gameVO.setOdd2(e.getOdd2());

            if(e.getCountryOne().contains("CHN") || e.getCountryTwo().contains("CHN")){
                gameVO.setChina(1);
            }else{
                gameVO.setChina(0);
            }

            return gameVO;
        }).collect(Collectors.toList());

        List<GameVO> gameVOList = gameVOList2.stream().sorted(Comparator.comparingInt(GameVO::getChina).reversed()
                .thenComparing(GameVO::getPlayTime))
                .collect(Collectors.toList());

        List<String> gameTitles = gameVOList.stream().map(e -> e.getTitle()).distinct().collect(Collectors.toList());

        List<GameBatchVO> result = null;
        result = gameTitles.stream().map(e -> {

            GameBatchVO gameBatchVO = new GameBatchVO();
            gameBatchVO.setTitle(e);
            List<GameVO> gameVOList1 = gameVOList.stream().filter( gameVO -> gameVO.getTitle().equals(e)).collect(Collectors.toList());
            gameBatchVO.setGames(gameVOList1);
            return gameBatchVO;

        }).collect(Collectors.toList());

        return success(result);
    }

}
