package com.elephant.calendar.controller;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.api.calendar.CalendarApi;
import com.elephant.api.dto.calendar.CalendarDTO;
import com.elephant.api.vo.calendar.CalendarVO;
import com.elephant.common.model.calendar.Calendar;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.calendar.service.CalendarService;
import com.elephant.calendar.service.CalendarBizService;
import com.elephant.calendar.service.CalendarHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * CalendarController
 *
 * @author cunw generator
 * date 2023-06-18
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars")
public class CalendarController extends BaseController implements CalendarApi {
    private final CalendarService calendarService;
    private final CalendarHelperService calendarHelperService;
    private final CalendarBizService calendarBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final CalendarDTO dto){
         calendarBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<CalendarVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final Calendar po = calendarService.getById(id);
        final CalendarVO vo = mappingService.mapping(po, CalendarVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<CalendarVO> update(final String id, final CalendarDTO dto){
        Calendar po = mappingService.mapping(dto, Calendar.class);
        calendarService.modify(id, po);
        po = calendarService.getById(id);
        final CalendarVO vo = mappingService.mapping(po, CalendarVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            calendarService.delete(id);
        } else {
            calendarService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}