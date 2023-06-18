package com.elephant.calendar.service.impl;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.dto.calendar.CalendarDTO;
import com.elephant.api.vo.calendar.CalendarVO;
import com.elephant.common.model.calendar.Calendar;
import lombok.RequiredArgsConstructor;
import com.elephant.calendar.service.CalendarHelperService;
import com.elephant.calendar.service.CalendarService;
import org.springframework.stereotype.Service;

/**
 * Calendar-服务接口
 *
 * @author cunw generator
 * date 2023-06-18
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class CalendarHelperServiceImpl implements CalendarHelperService {

    private final IBeanMappingService mappingService;
    private final CalendarService calendarService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final CalendarDTO dto){
        final Calendar po = mappingService.mapping(dto, Calendar.class);
        calendarService.add(po);
    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public CalendarVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final CalendarDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}