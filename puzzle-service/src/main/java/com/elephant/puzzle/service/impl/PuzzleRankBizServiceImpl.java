package com.elephant.puzzle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.elephant.api.dto.puzzle.PuzzleRankDTO;
import com.elephant.api.vo.puzzle.PuzzleRankVO;
import com.elephant.common.model.score.Score;
import com.elephant.puzzle.service.PuzzleRankService;
import com.elephant.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import com.elephant.puzzle.service.PuzzleRankBizService;
import com.elephant.puzzle.service.PuzzleRankHelperService;
import org.springframework.stereotype.Service;
/**
 * PuzzleRank-服务接口
 *
 * @author cunw generator
 * date 2023-04-09
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class PuzzleRankBizServiceImpl implements PuzzleRankBizService {

    private final PuzzleRankHelperService puzzleRankHelperService;

    private final PuzzleRankService puzzleRankService;

    private final ScoreService scoreService;

    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final PuzzleRankDTO dto){
        LambdaQueryWrapper<Score> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(Score::getUserId, dto.getUserId());
        Score old = scoreService.getOne(lambdaQuery);
        Score score = new Score();
        if(old == null){
            score.setScore(1);
        }else{
            score.setId(old.getId());
            score.setScore(old.getScore()+1);
        }
        score.setUserId(dto.getUserId());
        puzzleRankHelperService.add(dto,score);
    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public PuzzleRankVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final PuzzleRankDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}