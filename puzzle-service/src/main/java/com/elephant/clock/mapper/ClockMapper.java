package com.elephant.clock.mapper;

import com.elephant.common.model.clock.Clock;
import com.cunw.orm.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Clock-Mapper
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@Mapper
public interface ClockMapper extends IBaseMapper<Clock, String> {

}