package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.controller.BaseController;
import com.cunw.boot.service.IBeanMappingService;
import com.cunw.framework.vo.PageList;
import com.cunw.framework.vo.ResultVO;
import com.elephant.api.dto.clock.ClockDTO;
import com.elephant.api.dto.clock.ClockEventDTO;
import com.elephant.api.vo.clock.ClockVO;
import com.elephant.api.vo.user.UserAuth;
import com.elephant.clock.service.ClockBizService;
import com.elephant.clock.service.ClockEventService;
import com.elephant.clock.service.ClockHelperService;
import com.elephant.clock.service.ClockService;
import com.elephant.common.model.clock.Clock;
import com.elephant.common.model.clock.ClockEvent;
import com.elephant.utils.AuthUtil;
import com.elephant.utils.EventHelper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClockController
 *
 * @author cunw generator
 * date 2023-06-10
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/clocks")
public class ClockController extends BaseController  {
    private final ClockService clockService;
    private  final ClockEventService clockEventService;
    private final ClockHelperService clockHelperService;
    private final ClockBizService clockBizService;
    private final IBeanMappingService mappingService;

    @GetMapping(value = "/page")
    @ApiOperation(value = "查询分页列表", notes = "根据条件查询分页列表")
    public ResultVO<PageList<ClockVO>> page(@SpringQueryMap final ClockDTO dto) {

        UserAuth user = AuthUtil.getUserAuth();
        if(user != null){
            dto.setUserId(user.getId());
        }
        LambdaQueryWrapper<Clock> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.eq(Clock::getCreateUserCode, dto.getUserId());


        PageList<Clock> pageList = clockService.queryForPage( dto.getPageNum(), dto.getPageSize(), lambdaQuery);

        PageList<ClockVO> puzzleRankVOPageList = mappingService.mapping(pageList, ClockVO.class);

        List<ClockVO> clockVOS = puzzleRankVOPageList.getList();

        clockVOS = clockVOS.stream().filter(e ->
            e.getEventType() == 1 || e.getEventType() == 4).collect(Collectors.toList());
        clockVOS.stream().      forEach(e ->{
            if(e.getEventType() == 1){
                e.setSeconds(EventHelper.calculateDeadline(e.getEventType(), 0));
            }
            if(e.getEventType() == 4){
                e.setSeconds(EventHelper.calculateDeadline(e.getEventType(), NumberUtils.toInt(e.getNotifyDate())));
            }
            e.setDeadLine(EventHelper.formatSeconds(e.getSeconds()));
            if(getEvent(e.getId(), e.getEventType(), dto.getUserId())){
                e.setFinishStatus(1);
                e.setDeadLine("已完成");
            }else {
                e.setFinishStatus(0);
            }

        });

        List<ClockVO> sortedClockVOS = clockVOS.stream()
                .sorted(Comparator.comparingInt(ClockVO::getFinishStatus)
                        .thenComparing(ClockVO::getSeconds))
                .collect(Collectors.toList());


        puzzleRankVOPageList.setList(sortedClockVOS);

        return success(puzzleRankVOPageList);
    }

    public boolean getEvent(String clockId, Integer eventType, String userId){

        // 获取今天日期
        LocalDate today = LocalDate.now();

// 获取今天起始时间（00:00:00）
        LocalDateTime todayStart = today.atStartOfDay();

// 将LocalDateTime类型转换为Date类型
        Date startDate = Date.from(todayStart.atZone(ZoneId.systemDefault()).toInstant());


        LambdaQueryWrapper<ClockEvent> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.ge(ClockEvent::getCreateDate, startDate);
        lambdaQuery.eq(ClockEvent::getCreateUserCode, userId);
        lambdaQuery.eq(ClockEvent::getClockId, clockId);

        List<ClockEvent> clockEvents = clockEventService.queryList(lambdaQuery);
        if(CollectionUtils.isNotEmpty(clockEvents)){
            return true;
        }
        return false;
    }

    //    @Override
//    public ResultVO<Boolean> add(final ClockDTO dto){
//         clockBizService.add(dto);
//         return success(true);
//    }

    @PostMapping(value = "/add")
    public ResultVO<Boolean> add(final @RequestBody ClockDTO dto){
        UserAuth user = AuthUtil.getUserAuth();
        if(user != null){
            dto.setUserId(user.getId());
        }
        Clock po = mappingService.mapping(dto, Clock.class);
        po.setCreateUserCode(dto.getUserId());
        clockService.add(po);
        return success(true);
    }

    @PostMapping(value = "/delete")
    public ResultVO<Boolean> delete(final @RequestBody ClockDTO dto){
        UserAuth user = AuthUtil.getUserAuth();

        LambdaQueryWrapper<Clock> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.ge(Clock::getEventDescription, dto.getEventDescription());
        lambdaQuery.eq(Clock::getCreateUserCode, user.getId());
        lambdaQuery.orderByDesc(Clock::getCreateDate);

        List<Clock> clockEvents = clockService.queryList(lambdaQuery);
        if(CollectionUtils.isNotEmpty(clockEvents)){
            clockService.delete(clockEvents.get(0).getId());
        }

        return success(true);
    }

    @PostMapping(value = "/finish")
    public ResultVO<Boolean> add(final @RequestBody ClockEventDTO dto){
        UserAuth user = AuthUtil.getUserAuth();
        if(user != null){
            dto.setUserId(user.getId());
        }
         ClockEvent po = mappingService.mapping(dto, ClockEvent.class);
         po.setCreateUserCode(dto.getUserId());
         clockEventService.add(po);
         return success(true);
    }

    @PostMapping(value = "/rollback")
    public ResultVO<Boolean> rollback(){
        UserAuth user = AuthUtil.getUserAuth();

        // 获取今天日期
        LocalDate today = LocalDate.now();

// 获取今天起始时间（00:00:00）
        LocalDateTime todayStart = today.atStartOfDay();

// 将LocalDateTime类型转换为Date类型
        Date startDate = Date.from(todayStart.atZone(ZoneId.systemDefault()).toInstant());

        LambdaQueryWrapper<ClockEvent> lambdaQuery = new LambdaQueryWrapper<>();

        lambdaQuery.ge(ClockEvent::getCreateDate, startDate);
        lambdaQuery.eq(ClockEvent::getCreateUserCode, user.getId());
        lambdaQuery.orderByDesc(ClockEvent::getCreateDate);

        List<ClockEvent> clockEvents = clockEventService.queryList(lambdaQuery);
        if(CollectionUtils.isNotEmpty(clockEvents)){
            clockEventService.delete(clockEvents.get(0).getId());
        }

        return success(true);
    }
}