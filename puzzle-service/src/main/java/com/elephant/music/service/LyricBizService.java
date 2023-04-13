package com.elephant.music.service;

import com.elephant.api.dto.music.LyricDTO;
import com.elephant.api.vo.music.LyricVO;

/**
 * Lyric-服务接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */

public interface LyricBizService {

    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    void add(final LyricDTO dto);

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    LyricVO get(final String id);

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    void update(final String id, final LyricDTO dto);

    /**
     * 公共删除
     * @param id 主键ID
     */
    void delete(final String id);
}