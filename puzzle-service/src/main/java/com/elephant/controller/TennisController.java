package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.utils.time.DateUtils;
import com.cunw.framework.vo.ResultVO;
import com.elephant.api.dto.score.TravelDTO;
import com.elephant.api.vo.score.TravelVO;
import com.elephant.chess.service.ChessGameService;
import com.elephant.common.model.game.Game;
import com.elephant.game.service.GameService;
import com.elephant.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tennis")
@Slf4j
public class TennisController extends BaseController {

    private final GameService gameService;

    @GetMapping("/fuck")
    public ResultVO<TravelVO> fuck(@SpringQueryMap final TravelDTO dto) throws IOException {
        log.info("request data {}", ObjectUtils.getJsonStringFromObject(dto));
        String url = "https://www.rank-tennis.com/zh/result/2023-07-03";

        log.info(url);

        Document doc = Jsoup.connect(url).get();

        Elements elements = doc.select("div.cResultTourInfoCity");
        Elements contents = doc.select("div.cResultTourContent");

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            log.info("\n\n------------game-------------");
            String title = element.text();
            log.info(title);
            Element content = contents.get(i);
            Elements matches = content.select(".cResultMatch");
            for (Element match : matches) {

                Elements trElements = match.select(".cResultMatchTime");
                String time = trElements.get(0).text();
                long dd = NumberUtils.toLong(time);
                Date date = new Date(dd*1000l);
                String dateText = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm");

                Elements tdElements = match.select("td");
                log.info("\n\n------------player-------------");
                System.out.println(dateText);
                Element tdElement1 = tdElements.get(0);  // 在每个<tr>标签中，使用选择器选择所有<td>标签
                String tdText1 = tdElement1.text();
                System.out.println(tdText1);

                Element tdElement2 = tdElements.get(1);  // 在每个<tr>标签中，使用选择器选择所有<td>标签
                String tdText2 = tdElement2.text();
                System.out.println(tdText2);

                Game old = getArticle(title, tdText1, tdText2);
                if(old != null){
                    continue;
                }

                Game game = new Game();
                game.setTitle(title);
                game.setPlayerOne(tdText1);
                game.setPlayerTwo(tdText2);
                game.setPlayTime(dateText);
                game.setScore("");
                game.setCreateUserCode("1");
                game.setUpdateUserCode("mytest");
                game.setCreateDate(new Date());
                gameService.add(game);

            }
        }



        TravelVO travelVO = new TravelVO();
        return success(travelVO);
    }

    public Game getArticle(String title, String player1, String player2){
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


}
