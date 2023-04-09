package com.elephant.puzzle.service.impl;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.dto.puzzle.PuzzleRankDTO;
import com.elephant.api.vo.puzzle.PuzzleRankVO;
import com.elephant.common.model.puzzle.PuzzleRank;
import com.elephant.common.model.score.Score;
import com.elephant.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import com.elephant.puzzle.service.PuzzleRankHelperService;
import com.elephant.puzzle.service.PuzzleRankService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PuzzleRank-服务接口
 *
 * @author cunw generator
 * date 2023-04-09
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class PuzzleRankHelperServiceImpl implements PuzzleRankHelperService {

    private final IBeanMappingService mappingService;
    private final PuzzleRankService puzzleRankService;

    private final ScoreService scoreService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    @Transactional
    public void add(final PuzzleRankDTO dto, final Score score){
        final PuzzleRank po = mappingService.mapping(dto, PuzzleRank.class);
        puzzleRankService.add(po);

        if(score.getId() != null){
            scoreService.modify(score.getId(), score);
        }else {
            scoreService.add(score);
        }

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