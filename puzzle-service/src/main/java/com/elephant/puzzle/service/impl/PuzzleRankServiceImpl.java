package com.elephant.puzzle.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.puzzle.PuzzleRank;
import com.elephant.puzzle.mapper.PuzzleRankMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.puzzle.service.PuzzleRankService;
import org.springframework.stereotype.Service;

/**
 * PuzzleRank-服务实现
 *
 * @author cunw generator
 * date 2023-04-09
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class PuzzleRankServiceImpl extends BaseOrmServiceImpl<PuzzleRank, String> implements PuzzleRankService {
    private final PuzzleRankMapper puzzleRankMapper;

    @Override
    protected IBaseMapper<PuzzleRank, String> getMapper() {
        return this.puzzleRankMapper;
    }

}
