package com.elephant.game.service.impl;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.dto.game.GameCommentDTO;
import com.elephant.api.vo.game.GameCommentVO;
import com.elephant.common.model.game.GameComment;
import lombok.RequiredArgsConstructor;
import com.elephant.game.service.GameCommentHelperService;
import com.elephant.game.service.GameCommentService;
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
public class GameCommentHelperServiceImpl implements GameCommentHelperService {

    private final IBeanMappingService mappingService;
    private final GameCommentService gameCommentService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final GameCommentDTO dto){
        final GameComment po = mappingService.mapping(dto, GameComment.class);
        gameCommentService.add(po);
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