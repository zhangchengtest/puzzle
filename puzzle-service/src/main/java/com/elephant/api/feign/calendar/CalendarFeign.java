package com.elephant.api.feign.calendar;

import com.elephant.api.api.calendar.CalendarApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * CalendarFeign
 *
 * @author cunw generator
 * date 2023-06-18
 * 湖南新云网科技有限公司版权所有.
 */
@FeignClient(value = "cunw-music-server", path = "/calendars")
public interface CalendarFeign extends CalendarApi {

}
