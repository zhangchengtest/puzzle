package com.elephant.music.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.music.Comment;
import com.elephant.music.mapper.CommentMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * Comment-服务实现
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends BaseOrmServiceImpl<Comment, String> implements CommentService {
    private final CommentMapper commentMapper;

    @Override
    protected IBaseMapper<Comment, String> getMapper() {
        return this.commentMapper;
    }

}
