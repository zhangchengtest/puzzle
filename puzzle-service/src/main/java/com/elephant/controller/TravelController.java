package com.elephant.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.elephant.api.dto.score.ScoreDTO;
import com.elephant.api.dto.score.TravelDTO;
import com.elephant.api.vo.score.ScoreVO;
import com.elephant.api.vo.score.TravelVO;
import com.elephant.utils.HttpRequest;
import com.elephant.utils.ObjectUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travel")
@Slf4j
public class TravelController extends BaseController {

    @GetMapping("/getCityId")
    @ApiOperation(value="获取详细信息", notes="根据url的id来获取详细信息")
    public ResultVO<TravelVO> getByUserId(@SpringQueryMap final TravelDTO dto){
        String url = "https://m.ctrip.com/restapi/h5api/globalsearch/search?action=gsonline" +
                "&source=globalonline&keyword={}&t=1687580717685";

        url = MessageFormatter.arrayFormat(url, new Object[]{URLEncoder.encode(dto.getKeyword())}).getMessage();
        log.info(url);
        String data = HttpRequest.sendAuthGet(url, null, null);
        JSONObject obj = JSON.parseObject(data);
        JSONArray jsonArray = obj.getJSONArray("data");
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        log.info(jsonObject.getString("id"));
        TravelVO travelVO = new TravelVO();
        travelVO.setId(jsonObject.getString("id"));
        travelVO.setName(jsonObject.getString("eName").replace(" ", ""));
        return success(travelVO);
    }

}
