package com.elephant.score.service.impl;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.dto.score.ScoreDTO;
import com.elephant.api.vo.score.ScoreVO;
import com.elephant.common.model.score.Score;
import lombok.RequiredArgsConstructor;
import com.elephant.score.service.ScoreHelperService;
import com.elephant.score.service.ScoreService;
import org.springframework.stereotype.Service;

/**
 * Score-服务接口
 *
 * @author cunw generator
 * date 2023-04-09
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class ScoreHelperServiceImpl implements ScoreHelperService {

    private final IBeanMappingService mappingService;
    private final ScoreService scoreService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final ScoreDTO dto){
        final Score po = mappingService.mapping(dto, Score.class);
        scoreService.add(po);
    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public ScoreVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final ScoreDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}