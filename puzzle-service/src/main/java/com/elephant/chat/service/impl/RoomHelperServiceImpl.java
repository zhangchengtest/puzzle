package com.elephant.chat.service.impl;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.dto.chat.RoomDTO;
import com.elephant.api.vo.chat.RoomVO;
import com.elephant.common.model.chat.Room;
import lombok.RequiredArgsConstructor;
import com.elephant.chat.service.RoomHelperService;
import com.elephant.chat.service.RoomService;
import org.springframework.stereotype.Service;

/**
 * Room-服务接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class RoomHelperServiceImpl implements RoomHelperService {

    private final IBeanMappingService mappingService;
    private final RoomService roomService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final RoomDTO dto){
        final Room po = mappingService.mapping(dto, Room.class);
        roomService.add(po);
    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public RoomVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final RoomDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}