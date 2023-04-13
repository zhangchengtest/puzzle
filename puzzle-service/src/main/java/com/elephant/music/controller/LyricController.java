package com.elephant.music.controller;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.api.music.LyricApi;
import com.elephant.api.dto.music.LyricDTO;
import com.elephant.api.vo.music.LyricVO;
import com.elephant.common.model.music.Lyric;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.music.service.LyricService;
import com.elephant.music.service.LyricBizService;
import com.elephant.music.service.LyricHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * LyricController
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/lyrics")
public class LyricController extends BaseController implements LyricApi {
    private final LyricService lyricService;
    private final LyricHelperService lyricHelperService;
    private final LyricBizService lyricBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final LyricDTO dto){
         lyricBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<LyricVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final Lyric po = lyricService.getById(id);
        final LyricVO vo = mappingService.mapping(po, LyricVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<LyricVO> update(final String id, final LyricDTO dto){
        Lyric po = mappingService.mapping(dto, Lyric.class);
        lyricService.modify(id, po);
        po = lyricService.getById(id);
        final LyricVO vo = mappingService.mapping(po, LyricVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            lyricService.delete(id);
        } else {
            lyricService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}