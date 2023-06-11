package com.elephant.clock.service.impl;

import com.elephant.api.dto.clock.ClockEventDTO;
import com.elephant.api.vo.clock.ClockEventVO;
import lombok.RequiredArgsConstructor;
import com.elephant.clock.service.ClockEventBizService;
import com.elephant.clock.service.ClockEventHelperService;
import org.springframework.stereotype.Service;
/**
 * ClockEvent-服务接口
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class ClockEventBizServiceImpl implements ClockEventBizService {

    private final ClockEventHelperService clockEventHelperService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final ClockEventDTO dto){

    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public ClockEventVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final ClockEventDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}