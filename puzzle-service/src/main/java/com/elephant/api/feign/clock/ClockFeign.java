package com.elephant.api.feign.clock;

import com.elephant.api.api.clock.ClockApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * ClockFeign
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@FeignClient(value = "cunw-clock-server", path = "/clocks")
public interface ClockFeign extends ClockApi {

}
