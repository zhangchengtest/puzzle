package com.elephant.api.feign.chat;

import com.elephant.api.api.chat.RoomApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * RoomFeign
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@FeignClient(value = "cunw-chat-server", path = "/rooms")
public interface RoomFeign extends RoomApi {

}
