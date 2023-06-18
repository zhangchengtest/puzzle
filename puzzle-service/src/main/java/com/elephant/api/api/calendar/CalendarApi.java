package com.elephant.api.api.calendar;

import com.elephant.api.base.BaseApi;
import com.elephant.api.dto.calendar.CalendarDTO;
import com.elephant.api.vo.calendar.CalendarVO;
import io.swagger.annotations.Api;

/**
 * CalendarAPI接口
 *
 * @author cunw generator
 * date 2023-06-18
 * 湖南新云网科技有限公司版权所有.
 */
@Api(tags = "闹钟事件")
public interface CalendarApi extends BaseApi<CalendarDTO, CalendarVO> {

}