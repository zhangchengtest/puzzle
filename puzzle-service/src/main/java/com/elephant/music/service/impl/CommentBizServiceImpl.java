package com.elephant.music.service.impl;

import com.elephant.api.dto.music.CommentDTO;
import com.elephant.api.vo.music.CommentVO;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.CommentBizService;
import com.elephant.music.service.CommentHelperService;
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
public class CommentBizServiceImpl implements CommentBizService {

    private final CommentHelperService commentHelperService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final CommentDTO dto){

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