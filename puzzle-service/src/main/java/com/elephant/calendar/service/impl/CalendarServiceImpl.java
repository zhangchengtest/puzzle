package com.elephant.calendar.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.calendar.Calendar;
import com.elephant.calendar.mapper.CalendarMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.calendar.service.CalendarService;
import org.springframework.stereotype.Service;

/**
 * Calendar-服务实现
 *
 * @author cunw generator
 * date 2023-06-18
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class CalendarServiceImpl extends BaseOrmServiceImpl<Calendar, String> implements CalendarService {
    private final CalendarMapper calendarMapper;

    @Override
    protected IBaseMapper<Calendar, String> getMapper() {
        return this.calendarMapper;
    }

}
