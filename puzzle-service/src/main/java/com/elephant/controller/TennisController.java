package com.elephant.controller;

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

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            log.info("\n\n------------game-------------");
            String title = element.text();
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
                String dateText = DateUtils.formatDate(date, "HH:mm");
                String day = DateUtils.formatDate(date, "yyyy-MM-dd");

                Elements tdElements = match.select("td");
                log.info("\n\n------------player-------------");
                System.out.println(dateText);
                Element tdElement1 = tdElements.get(0);  // 在每个<tr>标签中，使用选择器选择所有<td>标签
                String alt1 = "";
                if(CollectionUtils.isNotEmpty(tdElement1.select("img"))){
                    alt1 = tdElement1.select("img").get(0).attr("alt");
                }

                String tdText1 = getTd(tdElement1);
                System.out.println(tdText1);

                Element tdElement2 = tdElements.get(1);  // 在每个<tr>标签中，使用选择器选择所有<td>标签
                String tdText2 = getTd(tdElement2);
                String alt2 = "";
                if(CollectionUtils.isNotEmpty(tdElement2.select("img"))){
                    alt2 = tdElement2.select("img").get(0).attr("alt");
                }

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

                    game.setPlayTime(dateText);
                    game.setScore("");
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
                    game.setPlayTime(dateText);
                    game.setScore("");
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

    private String getTd(Element tdElement2){
        String tdText2 = tdElement2.wholeOwnText().trim();
        Elements matches = tdElement2.select("sub");
        if(CollectionUtils.isNotEmpty(matches)){
            for(Element element: matches){
                tdText2 = tdText2 +" " + element.wholeOwnText().trim();
            }
        }
        Elements matches2 = tdElement2.select("odds");
        if(CollectionUtils.isNotEmpty(matches2)){
            for(Element element: matches2){
                tdText2 = tdText2 +" " + element.wholeOwnText().trim();
            }
        }

        return tdText2;
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
        System.out.println(date);
        System.out.println(System.currentTimeMillis());
    }


    @GetMapping(value = "/page")
    @ApiOperation(value = "查询分页列表", notes = "根据条件查询分页列表")
    public ResultVO<List<GameBatchVO>> list(@SpringQueryMap final GameDTO dto) {

        LambdaQueryWrapper<Game> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.orderByDesc(Game::getCreateDate);

        PageList<Game> pageList = gameService.queryForPage(dto.getPageNum(), dto.getPageSize(), lambdaQuery);

        List<Game> gameList = pageList.getList();

        List<GameVO> gameVOList2 = gameList.stream().map( e-> {
            GameVO gameVO = new GameVO();
            gameVO.setTitle(e.getTitle()+e.getPlayDay());
            gameVO.setPlayers(e.getPlayerOne() +" vs "+ e.getPlayerTwo());
            gameVO.setPlayTime(e.getPlayTime());
            gameVO.setScore(e.getScore());
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
