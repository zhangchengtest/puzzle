package com.elephant.clock.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.clock.Clock;
import com.elephant.clock.mapper.ClockMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.clock.service.ClockService;
import org.springframework.stereotype.Service;

/**
 * Clock-服务实现
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class ClockServiceImpl extends BaseOrmServiceImpl<Clock, String> implements ClockService {
    private final ClockMapper clockMapper;

    @Override
    protected IBaseMapper<Clock, String> getMapper() {
        return this.clockMapper;
    }

}
