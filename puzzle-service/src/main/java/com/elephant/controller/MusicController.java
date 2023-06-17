package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.service.IBeanMappingService;
import com.cunw.framework.vo.PageList;
import com.elephant.api.api.music.MusicApi;
import com.elephant.api.dto.music.MusicDTO;
import com.elephant.api.vo.music.MusicVO;
import com.elephant.common.model.music.Album;
import com.elephant.common.model.music.Artist;
import com.elephant.common.model.music.Music;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cunw.framework.constant.MarkConstants;
import com.elephant.common.model.score.Score;
import com.elephant.music.service.*;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import com.elephant.utils.ConvertUtils;
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

    @GetMapping(value = "/page")
    @ApiOperation(value = "查询分页列表", notes = "根据条件查询分页列表")
    public ResultVO<PageList<MusicVO>> page(@SpringQueryMap final MusicDTO dto) {

        LambdaQueryWrapper<Music> lambdaQuery = new LambdaQueryWrapper<>();

        PageList<Music> pageList = musicService.queryForPage( dto.getPageNum(), dto.getPageSize(), lambdaQuery);

        List<String> albumIds = pageList.getList().stream().map(Music::getAlbumId).collect(Collectors.toList());
        List<Album>  albumList = getAlbs(albumIds);
        Map<String, Album> albumMap =  albumList.stream().collect(Collectors.toMap(Album::getId, Function.identity()));
        List<String> artistIds = albumList.stream().map(Album::getArtistId).collect(Collectors.toList());
        Map<String, Artist> artistMap = getArtists(artistIds);
        List<Object> objects = new ArrayList<>();
        objects.add(albumMap);
        objects.add(artistMap);
        PageList<MusicVO> musicVOPageList = ConvertUtils.mapping(pageList, this::convert, objects);

        return success(musicVOPageList);
    }

    private MusicVO convert(Music music, List<Object> objects){
        MusicVO musicVO = mappingService.mapping(music, MusicVO.class);
        Map<String, Album> albumMap = (Map<String, Album>)objects.get(0);
        Map<String, Artist> artistMap =  (Map<String, Artist>)objects.get(1);
        musicVO.setAlbumName(albumMap.get(music.getAlbumId()).getTitle());
        musicVO.setArtistName(artistMap.get(albumMap.get(music.getAlbumId()).getArtistId()).getArtistName());
        return musicVO;
    }

    private List<Album> getAlbs( List<String> albumIds){
        LambdaQueryWrapper<Album> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.in(Album::getId, albumIds);
        List<Album>  albumList = albumService.queryList(lambdaQuery);

        return albumList;
    }

    private Map<String, Artist> getArtists( List<String> ids){
        LambdaQueryWrapper<Artist> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.in(Artist::getId, ids);
        List<Artist>  albumList = artistService.queryList(lambdaQuery);
        Map<String, Artist> albumMap =  albumList.stream().collect(Collectors.toMap(Artist::getId, Function.identity()));
        return albumMap;
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