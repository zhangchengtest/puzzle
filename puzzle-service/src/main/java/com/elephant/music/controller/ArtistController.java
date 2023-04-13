package com.elephant.music.controller;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.api.music.ArtistApi;
import com.elephant.api.dto.music.ArtistDTO;
import com.elephant.api.vo.music.ArtistVO;
import com.elephant.common.model.music.Artist;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.music.service.ArtistService;
import com.elephant.music.service.ArtistBizService;
import com.elephant.music.service.ArtistHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * ArtistController
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistController extends BaseController implements ArtistApi {
    private final ArtistService artistService;
    private final ArtistHelperService artistHelperService;
    private final ArtistBizService artistBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final ArtistDTO dto){
         artistBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<ArtistVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final Artist po = artistService.getById(id);
        final ArtistVO vo = mappingService.mapping(po, ArtistVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<ArtistVO> update(final String id, final ArtistDTO dto){
        Artist po = mappingService.mapping(dto, Artist.class);
        artistService.modify(id, po);
        po = artistService.getById(id);
        final ArtistVO vo = mappingService.mapping(po, ArtistVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            artistService.delete(id);
        } else {
            artistService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}