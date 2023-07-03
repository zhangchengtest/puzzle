package com.elephant.api.feign.game;

import com.elephant.api.api.game.GameApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * GameFeign
 *
 * @author cunw generator
 * date 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@FeignClient(value = "cunw-music-server", path = "/games")
public interface GameFeign extends GameApi {

}
