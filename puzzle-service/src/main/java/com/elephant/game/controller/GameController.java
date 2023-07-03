package com.elephant.game.controller;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.api.game.GameApi;
import com.elephant.api.dto.game.GameDTO;
import com.elephant.api.vo.game.GameVO;
import com.elephant.common.model.game.Game;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.game.service.GameService;
import com.elephant.game.service.GameBizService;
import com.elephant.game.service.GameHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * GameController
 *
 * @author cunw generator
 * date 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController extends BaseController implements GameApi {
    private final GameService gameService;
    private final GameHelperService gameHelperService;
    private final GameBizService gameBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final GameDTO dto){
         gameBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<GameVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final Game po = gameService.getById(id);
        final GameVO vo = mappingService.mapping(po, GameVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<GameVO> update(final String id, final GameDTO dto){
        Game po = mappingService.mapping(dto, Game.class);
        gameService.modify(id, po);
        po = gameService.getById(id);
        final GameVO vo = mappingService.mapping(po, GameVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            gameService.delete(id);
        } else {
            gameService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}