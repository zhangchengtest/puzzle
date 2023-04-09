package com.elephant.api.feign.puzzle;

import com.elephant.api.api.puzzle.PuzzleRankApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * PuzzleRankFeign
 *
 * @author cunw generator
 * date 2023-04-09
 * 湖南新云网科技有限公司版权所有.
 */
@FeignClient(value = "cunw-music-server", path = "/puzzleRanks")
public interface PuzzleRankFeign extends PuzzleRankApi {

}
