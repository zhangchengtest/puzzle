package com.elephant.chat.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.chat.RoomMember;
import com.elephant.chat.mapper.RoomMemberMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.chat.service.RoomMemberService;
import org.springframework.stereotype.Service;

/**
 * RoomMember-服务实现
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class RoomMemberServiceImpl extends BaseOrmServiceImpl<RoomMember, String> implements RoomMemberService {
    private final RoomMemberMapper roomMemberMapper;

    @Override
    protected IBaseMapper<RoomMember, String> getMapper() {
        return this.roomMemberMapper;
    }

}
