package com.elephant.music.service.impl;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.dto.music.CommentDTO;
import com.elephant.api.vo.music.CommentVO;
import com.elephant.common.model.music.Comment;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.CommentHelperService;
import com.elephant.music.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * Comment-服务接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class CommentHelperServiceImpl implements CommentHelperService {

    private final IBeanMappingService mappingService;
    private final CommentService commentService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final CommentDTO dto){
        final Comment po = mappingService.mapping(dto, Comment.class);
        commentService.add(po);
    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public CommentVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final CommentDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}