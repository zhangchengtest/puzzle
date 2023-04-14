package com.elephant.chat.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.chat.RoomChat;
import com.elephant.chat.mapper.RoomChatMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.chat.service.RoomChatService;
import org.springframework.stereotype.Service;

/**
 * RoomChat-服务实现
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class RoomChatServiceImpl extends BaseOrmServiceImpl<RoomChat, String> implements RoomChatService {
    private final RoomChatMapper roomChatMapper;

    @Override
    protected IBaseMapper<RoomChat, String> getMapper() {
        return this.roomChatMapper;
    }

}
