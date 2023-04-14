package com.elephant.chat.service.impl;

import com.elephant.api.dto.chat.RoomChatDTO;
import com.elephant.api.vo.chat.RoomChatVO;
import lombok.RequiredArgsConstructor;
import com.elephant.chat.service.RoomChatBizService;
import com.elephant.chat.service.RoomChatHelperService;
import org.springframework.stereotype.Service;
/**
 * RoomChat-服务接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class RoomChatBizServiceImpl implements RoomChatBizService {

    private final RoomChatHelperService roomChatHelperService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final RoomChatDTO dto){

    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public RoomChatVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final RoomChatDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}