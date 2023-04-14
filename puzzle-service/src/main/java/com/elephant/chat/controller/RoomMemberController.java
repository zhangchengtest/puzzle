package com.elephant.chat.controller;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.api.chat.RoomMemberApi;
import com.elephant.api.dto.chat.RoomMemberDTO;
import com.elephant.api.vo.chat.RoomMemberVO;
import com.elephant.common.model.chat.RoomMember;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.chat.service.RoomMemberService;
import com.elephant.chat.service.RoomMemberBizService;
import com.elephant.chat.service.RoomMemberHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * RoomMemberController
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/roomMembers")
public class RoomMemberController extends BaseController implements RoomMemberApi {
    private final RoomMemberService roomMemberService;
    private final RoomMemberHelperService roomMemberHelperService;
    private final RoomMemberBizService roomMemberBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final RoomMemberDTO dto){
         roomMemberBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<RoomMemberVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final RoomMember po = roomMemberService.getById(id);
        final RoomMemberVO vo = mappingService.mapping(po, RoomMemberVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<RoomMemberVO> update(final String id, final RoomMemberDTO dto){
        RoomMember po = mappingService.mapping(dto, RoomMember.class);
        roomMemberService.modify(id, po);
        po = roomMemberService.getById(id);
        final RoomMemberVO vo = mappingService.mapping(po, RoomMemberVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            roomMemberService.delete(id);
        } else {
            roomMemberService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}