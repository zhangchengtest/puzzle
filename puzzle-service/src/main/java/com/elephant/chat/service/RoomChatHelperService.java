package com.elephant.chat.service;

import com.elephant.api.dto.chat.RoomChatDTO;
import com.elephant.api.vo.chat.RoomChatVO;

/**
 * RoomChat-服务接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */

public interface RoomChatHelperService {

    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    void add(final RoomChatDTO dto);

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    RoomChatVO get(final String id);

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    void update(final String id, final RoomChatDTO dto);

    /**
     * 公共删除
     * @param id 主键ID
     */
    void delete(final String id);
}