package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.service.IBeanMappingService;
import com.cunw.framework.vo.PageList;
import com.elephant.api.api.music.MusicApi;
import com.elephant.api.dto.music.MusicDTO;
import com.elephant.api.dto.puzzle.PuzzleRankDTO;
import com.elephant.api.vo.music.MusicVO;
import com.elephant.api.vo.puzzle.PuzzleRankVO;
import com.elephant.common.model.music.Album;
import com.elephant.common.model.music.Artist;
import com.elephant.common.model.music.Music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cunw.framework.constant.MarkConstants;
import com.elephant.common.model.puzzle.PuzzleRank;
import com.elephant.common.model.score.Score;
import com.elephant.music.service.*;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import com.elephant.utils.ObjectUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * MusicController
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/musics")
public class MusicController extends BaseController implements MusicApi {
    private final MusicService musicService;
    private final ArtistService artistService;
    private final AlbumService  albumService;
    private final MusicHelperService musicHelperService;
    private final MusicBizService musicBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final MusicDTO dto){
         musicBizService.add(dto);
         return success(true);
    }

    @GetMapping(value = "/random")
    @ApiOperation(value = "查询分页列表", notes = "根据条件查询分页列表")
    public ResultVO<String> random(){
        List<String> artistIds = new ArrayList<>();
        artistIds.add("6452");
        LambdaQueryWrapper<Album> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.in(Album::getArtistId, artistIds);
        List<Album>  albumList = albumService.queryList(lambdaQuery);
        List<String> albumIds = albumList.stream().map(Album::getId).collect(Collectors.toList());

        LambdaQueryWrapper<Music> lambdaQuery1 = new LambdaQueryWrapper<>();
        lambdaQuery1.in(Music::getAlbumId, albumIds);
        List<Music>  musicList = musicService.queryList(lambdaQuery1);
        List<String> musicNames = musicList.stream().map(Music::getMusicName).collect(Collectors.toList());

        String musicName = ObjectUtils.selectRandomElement(musicNames);

        return success(musicName);
    }

    @Override
    public ResultVO<MusicVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final Music po = musicService.getById(id);
        final MusicVO vo = mappingService.mapping(po, MusicVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<MusicVO> update(final String id, final MusicDTO dto){
        Music po = mappingService.mapping(dto, Music.class);
        musicService.modify(id, po);
        po = musicService.getById(id);
        final MusicVO vo = mappingService.mapping(po, MusicVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            musicService.delete(id);
        } else {
            musicService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}