package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.service.IBeanMappingService;
import com.cunw.framework.vo.PageList;
import com.elephant.api.api.chat.RoomChatApi;
import com.elephant.api.dto.chat.RoomChatDTO;
import com.elephant.api.vo.chat.RoomChatVO;
import com.elephant.common.model.chat.RoomChat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cunw.framework.constant.MarkConstants;
import com.elephant.chat.service.RoomChatService;
import com.elephant.chat.service.RoomChatBizService;
import com.elephant.chat.service.RoomChatHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import com.elephant.websocket.dto.ChatMessage;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * RoomChatController
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/roomChats")
public class RoomChatController extends BaseController implements RoomChatApi {
    private final RoomChatService roomChatService;
    private final RoomChatHelperService roomChatHelperService;
    private final RoomChatBizService roomChatBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final RoomChatDTO dto){
         roomChatBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<RoomChatVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final RoomChat po = roomChatService.getById(id);
        final RoomChatVO vo = mappingService.mapping(po, RoomChatVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<RoomChatVO> update(final String id, final RoomChatDTO dto){
        RoomChat po = mappingService.mapping(dto, RoomChat.class);
        roomChatService.modify(id, po);
        po = roomChatService.getById(id);
        final RoomChatVO vo = mappingService.mapping(po, RoomChatVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            roomChatService.delete(id);
        } else {
            roomChatService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "查询分页列表", notes = "根据条件查询分页列表")
    public ResultVO<PageList<ChatMessage>> list(@SpringQueryMap final RoomChatDTO dto) {

        LambdaQueryWrapper<RoomChat> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(RoomChat::getRoomId, dto.getRoomId());
        lambdaQuery.orderByAsc(RoomChat::getSendDate);

        PageList<RoomChat> pageList = roomChatService.queryForPage( dto.getPageNum(), dto.getPageSize(), lambdaQuery);

        PageList<ChatMessage> chatMessagePageList = mappingService.mapping(pageList, ChatMessage.class);

        List<ChatMessage> chatMessageList =  pageList.getList()
                .stream()
                .map(e -> {
                    ChatMessage message = new ChatMessage();
                    message.setUserId(e.getUserId());
                    message.setUsername(e.getUserId());
                    message.setContent(e.getContent());
                    message.setAvatarUrl("https://i.pravatar.cc/150?img=1");
                    message.setTimestamp(DateFormatUtils.format(e.getSendDate(), "yyyy-MM-dd HH:mm:ss"));
                    message.setId(e.getId());

                    if(e.getUserId().equals(dto.getUserId())){
                        message.setIsSelf(true);
                    }else{
                        message.setIsSelf(false);
                    }
                    return message;
                }).collect(Collectors.toList());
        chatMessagePageList.setList(chatMessageList);
        return success(chatMessagePageList);
    }
}