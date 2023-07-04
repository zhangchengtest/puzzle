package com.elephant.game.controller;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.api.game.GameCommentApi;
import com.elephant.api.dto.game.GameCommentDTO;
import com.elephant.api.vo.game.GameCommentVO;
import com.elephant.common.model.game.GameComment;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.game.service.GameCommentService;
import com.elephant.game.service.GameCommentBizService;
import com.elephant.game.service.GameCommentHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * GameCommentController
 *
 * @author cunw generator
 * date 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/gameComments")
public class GameCommentController extends BaseController implements GameCommentApi {
    private final GameCommentService gameCommentService;
    private final GameCommentHelperService gameCommentHelperService;
    private final GameCommentBizService gameCommentBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final GameCommentDTO dto){
         gameCommentBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<GameCommentVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final GameComment po = gameCommentService.getById(id);
        final GameCommentVO vo = mappingService.mapping(po, GameCommentVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<GameCommentVO> update(final String id, final GameCommentDTO dto){
        GameComment po = mappingService.mapping(dto, GameComment.class);
        gameCommentService.modify(id, po);
        po = gameCommentService.getById(id);
        final GameCommentVO vo = mappingService.mapping(po, GameCommentVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            gameCommentService.delete(id);
        } else {
            gameCommentService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}