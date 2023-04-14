package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.service.IBeanMappingService;
import com.cunw.framework.vo.PageList;
import com.elephant.api.api.puzzle.PuzzleRankApi;
import com.elephant.api.dto.puzzle.PuzzleRankDTO;
import com.elephant.api.vo.puzzle.PuzzleRankVO;
import com.elephant.api.vo.user.UserAuth;
import com.elephant.common.model.puzzle.PuzzleRank;
import java.util.Arrays;
import java.util.List;

import com.cunw.framework.constant.MarkConstants;
import com.elephant.puzzle.service.PuzzleRankService;
import com.elephant.puzzle.service.PuzzleRankBizService;
import com.elephant.puzzle.service.PuzzleRankHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import com.elephant.utils.AuthUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * PuzzleRankController
 *
 * @author cunw generator
 * date 2023-04-09
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/puzzleRanks")
public class PuzzleRankController extends BaseController implements PuzzleRankApi {
    private final PuzzleRankService puzzleRankService;
    private final PuzzleRankHelperService puzzleRankHelperService;
    private final PuzzleRankBizService puzzleRankBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final PuzzleRankDTO dto){

        UserAuth user = AuthUtil.getUserAuth();
        if(user != null){
            dto.setUserId(user.getId());
        }
        puzzleRankBizService.add(dto);
        return success(true);
    }

    @Override
    public ResultVO<PuzzleRankVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final PuzzleRank po = puzzleRankService.getById(id);
        final PuzzleRankVO vo = mappingService.mapping(po, PuzzleRankVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<PuzzleRankVO> update(final String id, final PuzzleRankDTO dto){
        PuzzleRank po = mappingService.mapping(dto, PuzzleRank.class);
        puzzleRankService.modify(id, po);
        po = puzzleRankService.getById(id);
        final PuzzleRankVO vo = mappingService.mapping(po, PuzzleRankVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            puzzleRankService.delete(id);
        } else {
            puzzleRankService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "查询分页列表", notes = "根据条件查询分页列表")
    public ResultVO<PageList<PuzzleRankVO>> page(@SpringQueryMap final PuzzleRankDTO dto) {

        LambdaQueryWrapper<PuzzleRank> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(PuzzleRank::getUrl, dto.getUrl());


        PageList<PuzzleRank> pageList = puzzleRankService.queryForPage( dto.getPageNum(), dto.getPageSize(), lambdaQuery);

        PageList<PuzzleRankVO> puzzleRankVOPageList = mappingService.mapping(pageList, PuzzleRankVO.class);
        return success(puzzleRankVOPageList);
    }
}