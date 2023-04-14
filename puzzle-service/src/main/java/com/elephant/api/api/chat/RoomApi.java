package com.elephant.api.api.chat;

import com.elephant.api.base.BaseApi;
import com.elephant.api.dto.chat.RoomDTO;
import com.elephant.api.vo.chat.RoomVO;
import io.swagger.annotations.Api;

/**
 * RoomAPI接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Api(tags = "房间表")
public interface RoomApi extends BaseApi<RoomDTO, RoomVO> {

}