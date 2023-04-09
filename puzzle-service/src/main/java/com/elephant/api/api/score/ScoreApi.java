package com.elephant.api.api.score;

import com.elephant.api.base.BaseApi;
import com.elephant.api.dto.score.ScoreDTO;
import com.elephant.api.vo.score.ScoreVO;
import io.swagger.annotations.Api;

/**
 * ScoreAPI接口
 *
 * @author cunw generator
 * date 2023-04-08
 * 湖南新云网科技有限公司版权所有.
 */
@Api(tags = "积分表，用于记录用户的积分变动情况")
public interface ScoreApi extends BaseApi<ScoreDTO, ScoreVO> {

}