package com.elephant.api.feign.music;

import com.elephant.api.api.music.CommentApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * CommentFeign
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@FeignClient(value = "cunw-music-server", path = "/comments")
public interface CommentFeign extends CommentApi {

}
