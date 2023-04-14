package com.elephant.chat.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.chat.Room;
import com.elephant.chat.mapper.RoomMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.chat.service.RoomService;
import org.springframework.stereotype.Service;

/**
 * Room-服务实现
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImpl extends BaseOrmServiceImpl<Room, String> implements RoomService {
    private final RoomMapper roomMapper;

    @Override
    protected IBaseMapper<Room, String> getMapper() {
        return this.roomMapper;
    }

}
