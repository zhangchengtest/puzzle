package com.elephant.music.controller;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.api.music.AlbumApi;
import com.elephant.api.dto.music.AlbumDTO;
import com.elephant.api.vo.music.AlbumVO;
import com.elephant.common.model.music.Album;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.music.service.AlbumService;
import com.elephant.music.service.AlbumBizService;
import com.elephant.music.service.AlbumHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * AlbumController
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController extends BaseController implements AlbumApi {
    private final AlbumService albumService;
    private final AlbumHelperService albumHelperService;
    private final AlbumBizService albumBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final AlbumDTO dto){
         albumBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<AlbumVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final Album po = albumService.getById(id);
        final AlbumVO vo = mappingService.mapping(po, AlbumVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<AlbumVO> update(final String id, final AlbumDTO dto){
        Album po = mappingService.mapping(dto, Album.class);
        albumService.modify(id, po);
        po = albumService.getById(id);
        final AlbumVO vo = mappingService.mapping(po, AlbumVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            albumService.delete(id);
        } else {
            albumService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}