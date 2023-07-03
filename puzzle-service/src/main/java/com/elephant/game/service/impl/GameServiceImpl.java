package com.elephant.game.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.game.Game;
import com.elephant.game.mapper.GameMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.game.service.GameService;
import org.springframework.stereotype.Service;

/**
 * Game-服务实现
 *
 * @author cunw generator
 * date 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class GameServiceImpl extends BaseOrmServiceImpl<Game, String> implements GameService {
    private final GameMapper gameMapper;

    @Override
    protected IBaseMapper<Game, String> getMapper() {
        return this.gameMapper;
    }

}
