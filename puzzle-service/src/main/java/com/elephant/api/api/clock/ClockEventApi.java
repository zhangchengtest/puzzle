package com.elephant.api.api.clock;

import com.elephant.api.base.BaseApi;
import com.elephant.api.dto.clock.ClockEventDTO;
import com.elephant.api.vo.clock.ClockEventVO;
import io.swagger.annotations.Api;

/**
 * ClockEventAPI接口
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Api(tags = "闹钟完成事件")
public interface ClockEventApi extends BaseApi<ClockEventDTO, ClockEventVO> {

}