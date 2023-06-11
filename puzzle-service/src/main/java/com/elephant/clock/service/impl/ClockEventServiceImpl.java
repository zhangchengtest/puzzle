package com.elephant.clock.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.clock.ClockEvent;
import com.elephant.clock.mapper.ClockEventMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.clock.service.ClockEventService;
import org.springframework.stereotype.Service;

/**
 * ClockEvent-服务实现
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class ClockEventServiceImpl extends BaseOrmServiceImpl<ClockEvent, String> implements ClockEventService {
    private final ClockEventMapper clockEventMapper;

    @Override
    protected IBaseMapper<ClockEvent, String> getMapper() {
        return this.clockEventMapper;
    }

}
