//package com.elephant.clock.controller;
//
//import com.cunw.boot.service.IBeanMappingService;
//import com.elephant.api.api.clock.ClockApi;
//import com.elephant.api.dto.clock.ClockDTO;
//import com.elephant.api.vo.clock.ClockVO;
//import com.elephant.common.model.clock.Clock;
//import java.util.Arrays;
//import com.cunw.framework.constant.MarkConstants;
//import com.elephant.clock.service.ClockService;
//import com.elephant.clock.service.ClockBizService;
//import com.elephant.clock.service.ClockHelperService;
//import com.cunw.boot.controller.BaseController;
//import com.cunw.framework.vo.ResultVO;
//import com.cunw.framework.utils.base.StringUtils;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//import lombok.RequiredArgsConstructor;
///**
// * ClockController
// *
// * @author cunw generator
// * date 2023-06-10
// * 湖南新云网科技有限公司版权所有.
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/clocks")
//public class ClockController extends BaseController implements ClockApi {
//    private final ClockService clockService;
//    private final ClockHelperService clockHelperService;
//    private final ClockBizService clockBizService;
//    private final IBeanMappingService mappingService;
//
//    @Override
//    public ResultVO<Boolean> add(final ClockDTO dto){
//         clockBizService.add(dto);
//         return success(true);
//    }
//
//    @Override
//    public ResultVO<ClockVO> get(final String id){
//        if(StringUtils.isBlank(id)){
//           return failed();
//        }
//        final Clock po = clockService.getById(id);
//        final ClockVO vo = mappingService.mapping(po, ClockVO.class);
//        return success(vo);
//    }
//
//    @Override
//    public ResultVO<ClockVO> update(final String id, final ClockDTO dto){
//        Clock po = mappingService.mapping(dto, Clock.class);
//        clockService.modify(id, po);
//        po = clockService.getById(id);
//        final ClockVO vo = mappingService.mapping(po, ClockVO.class);
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
//            clockService.delete(id);
//        } else {
//            clockService.delete(Arrays.asList(id.split(sep)));
//        }
//        return success();
//    }
//}