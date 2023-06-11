package com.elephant.clock.service;

import com.elephant.api.dto.clock.ClockEventDTO;
import com.elephant.api.vo.clock.ClockEventVO;

/**
 * ClockEvent-服务接口
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */

public interface ClockEventHelperService {

    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    void add(final ClockEventDTO dto);

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    ClockEventVO get(final String id);

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    void update(final String id, final ClockEventDTO dto);

    /**
     * 公共删除
     * @param id 主键ID
     */
    void delete(final String id);
}