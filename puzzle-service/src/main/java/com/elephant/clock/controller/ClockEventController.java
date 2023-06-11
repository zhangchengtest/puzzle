//package com.elephant.clock.controller;
//
//import com.cunw.boot.service.IBeanMappingService;
//import com.elephant.api.api.clock.ClockEventApi;
//import com.elephant.api.dto.clock.ClockEventDTO;
//import com.elephant.api.vo.clock.ClockEventVO;
//import com.elephant.common.model.clock.ClockEvent;
//import java.util.Arrays;
//import com.cunw.framework.constant.MarkConstants;
//import com.elephant.clock.service.ClockEventService;
//import com.elephant.clock.service.ClockEventBizService;
//import com.elephant.clock.service.ClockEventHelperService;
//import com.cunw.boot.controller.BaseController;
//import com.cunw.framework.vo.ResultVO;
//import com.cunw.framework.utils.base.StringUtils;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//import lombok.RequiredArgsConstructor;
///**
// * ClockEventController
// *
// * @author cunw generator
// * date 2023-06-10
// * 湖南新云网科技有限公司版权所有.
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/clockEvents")
//public class ClockEventController extends BaseController implements ClockEventApi {
//    private final ClockEventService clockEventService;
//    private final ClockEventHelperService clockEventHelperService;
//    private final ClockEventBizService clockEventBizService;
//    private final IBeanMappingService mappingService;
//
//    @Override
//    public ResultVO<Boolean> add(final ClockEventDTO dto){
//         clockEventBizService.add(dto);
//         return success(true);
//    }
//
//    @Override
//    public ResultVO<ClockEventVO> get(final String id){
//        if(StringUtils.isBlank(id)){
//           return failed();
//        }
//        final ClockEvent po = clockEventService.getById(id);
//        final ClockEventVO vo = mappingService.mapping(po, ClockEventVO.class);
//        return success(vo);
//    }
//
//    @Override
//    public ResultVO<ClockEventVO> update(final String id, final ClockEventDTO dto){
//        ClockEvent po = mappingService.mapping(dto, ClockEvent.class);
//        clockEventService.modify(id, po);
//        po = clockEventService.getById(id);
//        final ClockEventVO vo = mappingService.mapping(po, ClockEventVO.class);
//        return success(vo);
//    }
//
//    @Override
//    public ResultVO<?> delete(final String id){
//        if(StringUtils.isBlank(id)){
//           return failed();
//        }
//        final String sep = MarkConstants.COMMA;
//        if (StringUtils.indexOf(id, sep) == -1) {
//            clockEventService.delete(id);
//        } else {
//            clockEventService.delete(Arrays.asList(id.split(sep)));
//        }
//        return success();
//    }
//}