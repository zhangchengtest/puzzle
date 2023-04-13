package com.elephant.music.service;

import com.elephant.api.dto.music.AlbumDTO;
import com.elephant.api.vo.music.AlbumVO;

/**
 * Album-服务接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */

public interface AlbumHelperService {

    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    void add(final AlbumDTO dto);

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    AlbumVO get(final String id);

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    void update(final String id, final AlbumDTO dto);

    /**
     * 公共删除
     * @param id 主键ID
     */
    void delete(final String id);
}