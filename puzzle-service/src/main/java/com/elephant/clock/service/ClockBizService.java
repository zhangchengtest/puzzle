package com.elephant.clock.service;

import com.elephant.api.dto.clock.ClockDTO;
import com.elephant.api.vo.clock.ClockVO;

/**
 * Clock-服务接口
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */

public interface ClockBizService {

    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    void add(final ClockDTO dto);

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    ClockVO get(final String id);

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    void update(final String id, final ClockDTO dto);

    /**
     * 公共删除
     * @param id 主键ID
     */
    void delete(final String id);
}