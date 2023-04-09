package com.elephant.score.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.score.Score;
import com.elephant.score.mapper.ScoreMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.score.service.ScoreService;
import org.springframework.stereotype.Service;

/**
 * Score-服务实现
 *
 * @author cunw generator
 * date 2023-04-09
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class ScoreServiceImpl extends BaseOrmServiceImpl<Score, String> implements ScoreService {
    private final ScoreMapper scoreMapper;

    @Override
    protected IBaseMapper<Score, String> getMapper() {
        return this.scoreMapper;
    }

}
