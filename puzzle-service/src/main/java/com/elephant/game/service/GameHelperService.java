package com.elephant.game.service;

import com.elephant.api.dto.game.GameDTO;
import com.elephant.api.vo.game.GameVO;

/**
 * Game-服务接口
 *
 * @author cunw generator
 * date 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */

public interface GameHelperService {

    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    void add(final GameDTO dto);

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    GameVO get(final String id);

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    void update(final String id, final GameDTO dto);

    /**
     * 公共删除
     * @param id 主键ID
     */
    void delete(final String id);
}