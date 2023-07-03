package com.elephant.game.mapper;

import com.elephant.common.model.game.Game;
import com.cunw.orm.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Game-Mapper
 *
 * @author cunw generator
 * date 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@Mapper
public interface GameMapper extends IBaseMapper<Game, String> {

}