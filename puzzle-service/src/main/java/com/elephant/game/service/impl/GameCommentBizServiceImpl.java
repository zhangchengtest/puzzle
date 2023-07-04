package com.elephant.game.service.impl;

import com.elephant.api.dto.game.GameCommentDTO;
import com.elephant.api.vo.game.GameCommentVO;
import lombok.RequiredArgsConstructor;
import com.elephant.game.service.GameCommentBizService;
import com.elephant.game.service.GameCommentHelperService;
import org.springframework.stereotype.Service;
/**
 * GameComment-服务接口
 *
 * @author cunw generator
 * date 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class GameCommentBizServiceImpl implements GameCommentBizService {

    private final GameCommentHelperService gameCommentHelperService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final GameCommentDTO dto){

    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public GameCommentVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final GameCommentDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}