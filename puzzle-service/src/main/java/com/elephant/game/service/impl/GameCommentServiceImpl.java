package com.elephant.game.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.game.GameComment;
import com.elephant.game.mapper.GameCommentMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.game.service.GameCommentService;
import org.springframework.stereotype.Service;

/**
 * GameComment-服务实现
 *
 * @author cunw generator
 * date 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class GameCommentServiceImpl extends BaseOrmServiceImpl<GameComment, String> implements GameCommentService {
    private final GameCommentMapper gameCommentMapper;

    @Override
    protected IBaseMapper<GameComment, String> getMapper() {
        return this.gameCommentMapper;
    }

}
