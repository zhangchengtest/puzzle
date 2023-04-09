package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.api.score.ScoreApi;
import com.elephant.api.dto.score.ScoreDTO;
import com.elephant.api.vo.score.ScoreVO;
import com.elephant.api.vo.user.UserAuth;
import com.elephant.common.model.score.Score;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.score.service.ScoreService;
import com.elephant.score.service.ScoreBizService;
import com.elephant.score.service.ScoreHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import com.elephant.utils.AuthUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * ScoreController
 *
 * @author cunw generator
 * date 2023-04-09
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/scores")
public class ScoreController extends BaseController implements ScoreApi {
    private final ScoreService scoreService;
    private final ScoreHelperService scoreHelperService;
    private final ScoreBizService scoreBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final ScoreDTO dto){
         scoreBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<ScoreVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final Score po = scoreService.getById(id);
        final ScoreVO vo = mappingService.mapping(po, ScoreVO.class);
        return success(vo);
    }

    @GetMapping("/getByUserId")
    @ApiOperation(value="获取详细信息", notes="根据url的id来获取详细信息")
    public ResultVO<ScoreVO> getByUserId(@SpringQueryMap final ScoreDTO dto){
        UserAuth user = AuthUtil.getUserAuth();
        if(user != null){
            dto.setUserId(user.getId());
        }
        LambdaQueryWrapper<Score> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(Score::getUserId, dto.getUserId());
        final Score po = scoreService.getOne(lambdaQuery);
        if(po == null){
            ScoreVO vo = new ScoreVO();
            vo.setScore(0);
            return success(vo);
        }
        final ScoreVO vo = mappingService.mapping(po, ScoreVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<ScoreVO> update(final String id, final ScoreDTO dto){
        Score po = mappingService.mapping(dto, Score.class);
        scoreService.modify(id, po);
        po = scoreService.getById(id);
        final ScoreVO vo = mappingService.mapping(po, ScoreVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            scoreService.delete(id);
        } else {
            scoreService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}