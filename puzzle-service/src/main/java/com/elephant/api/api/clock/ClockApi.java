package com.elephant.api.api.clock;

import com.elephant.api.base.BaseApi;
import com.elephant.api.dto.clock.ClockDTO;
import com.elephant.api.vo.clock.ClockVO;
import io.swagger.annotations.Api;

/**
 * ClockAPI接口
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Api(tags = "闹钟事件")
public interface ClockApi extends BaseApi<ClockDTO, ClockVO> {

}