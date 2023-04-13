package com.elephant.api.feign.music;

import com.elephant.api.api.music.MusicApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * MusicFeign
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@FeignClient(value = "cunw-music-server", path = "/musics")
public interface MusicFeign extends MusicApi {

}
