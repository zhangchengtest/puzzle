package com.elephant.chat.service.impl;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.dto.chat.RoomMemberDTO;
import com.elephant.api.vo.chat.RoomMemberVO;
import com.elephant.common.model.chat.RoomMember;
import lombok.RequiredArgsConstructor;
import com.elephant.chat.service.RoomMemberHelperService;
import com.elephant.chat.service.RoomMemberService;
import org.springframework.stereotype.Service;

/**
 * RoomMember-服务接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class RoomMemberHelperServiceImpl implements RoomMemberHelperService {

    private final IBeanMappingService mappingService;
    private final RoomMemberService roomMemberService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final RoomMemberDTO dto){
        final RoomMember po = mappingService.mapping(dto, RoomMember.class);
        roomMemberService.add(po);
    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public RoomMemberVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final RoomMemberDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}