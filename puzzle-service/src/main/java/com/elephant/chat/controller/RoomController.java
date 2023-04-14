package com.elephant.chat.controller;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.api.chat.RoomApi;
import com.elephant.api.dto.chat.RoomDTO;
import com.elephant.api.vo.chat.RoomVO;
import com.elephant.common.model.chat.Room;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.chat.service.RoomService;
import com.elephant.chat.service.RoomBizService;
import com.elephant.chat.service.RoomHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * RoomController
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController extends BaseController implements RoomApi {
    private final RoomService roomService;
    private final RoomHelperService roomHelperService;
    private final RoomBizService roomBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final RoomDTO dto){
         roomBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<RoomVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final Room po = roomService.getById(id);
        final RoomVO vo = mappingService.mapping(po, RoomVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<RoomVO> update(final String id, final RoomDTO dto){
        Room po = mappingService.mapping(dto, Room.class);
        roomService.modify(id, po);
        po = roomService.getById(id);
        final RoomVO vo = mappingService.mapping(po, RoomVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            roomService.delete(id);
        } else {
            roomService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}