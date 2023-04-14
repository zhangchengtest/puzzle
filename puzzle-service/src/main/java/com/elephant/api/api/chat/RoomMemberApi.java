package com.elephant.api.api.chat;

import com.elephant.api.base.BaseApi;
import com.elephant.api.dto.chat.RoomMemberDTO;
import com.elephant.api.vo.chat.RoomMemberVO;
import io.swagger.annotations.Api;

/**
 * RoomMemberAPI接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Api(tags = "房间成员表")
public interface RoomMemberApi extends BaseApi<RoomMemberDTO, RoomMemberVO> {

}