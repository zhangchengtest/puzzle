package com.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.boot.service.IBeanMappingService;
import com.cunw.framework.vo.PageList;
import com.cunw.tid.SnowIdGenerator;
import com.elephant.api.api.music.MusicApi;
import com.elephant.api.dto.music.MusicDTO;
import com.elephant.api.vo.music.AlbumVO;
import com.elephant.api.vo.music.ArtistVO;
import com.elephant.api.vo.music.LyricVO;
import com.elephant.api.vo.music.MusicVO;
import com.elephant.common.model.music.Album;
import com.elephant.common.model.music.Artist;
import com.elephant.common.model.music.Lyric;
import com.elephant.common.model.music.Music;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

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
@Slf4j
public class MusicController extends BaseController implements MusicApi {
    private final MusicService musicService;
    private final ArtistService artistService;
    private final AlbumService  albumService;
    private final MusicHelperService musicHelperService;

    private final LyricService lyricService;
    private final MusicBizService musicBizService;
    private final IBeanMappingService mappingService;

    private final SnowIdGenerator idGenerator;

    @Value("${mp3.upload.dir}")
    private String mp3uploadDir;

    @Override
    public ResultVO<Boolean> add(final MusicDTO dto){

//        Music music = new Music();
//        music.setId();
//        music.setMusicName();
//        music.setAlbumId();
//        music.setDuration();

//        musicService.add(music);
//         musicBizService.add(dto);
         return success(true);
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException, UnsupportedAudioFileException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        String id = idGenerator.getNextStr();
        String fileName = saveFile(file, id);
        int length = getMp3Length(fileName);
        String fileNameWithoutExtension = getFileNameWithoutExtension(file.getOriginalFilename());
        String[] arr = fileNameWithoutExtension.split("-");
        String artistName = arr[0].trim();
        Artist artist = createArtist(artistName);
        Album album = createAlumn(artistName, artist.getId());
        String musicName = arr[1].trim();
        log.info("length {} fileNameWithoutExtension {}", length, fileNameWithoutExtension);

        Music music = new Music();
        music.setId(id);
        music.setMusicName(musicName);
        music.setAlbumId(album.getId());
        music.setDuration(length*1000);

        musicService.add(music);
        // Do something with file name, length, and seconds
        return "success";
    }

    public Artist createArtist(String name){

        Artist artist = getArtist(name);
        if(artist != null){
            return artist;
        }
        String id = idGenerator.getNextStr();
        Artist artist1 = new Artist();
        artist1.setId(id);
        artist1.setArtistName(name);
        artistService.add(artist1);
        return artist1;
    }

    public Artist getArtist(String name){
        LambdaQueryWrapper<Artist> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(Artist::getArtistName, name);
        Artist artist = artistService.getOne(lambdaQuery);
        return artist;
    }

    public Album createAlumn(String name, String artistId){

        Album artist = getAlbum(name);
        if(artist != null){
            return artist;
        }
        Album artist1 = new Album();
        artist1.setId(artistId);
        artist1.setArtistId(artistId);
        artist1.setTitle(name);
        artist1.setImg("http://peer.punengshuo.com/common/2023-06-18/jay.jpeg");
        albumService.add(artist1);
        return artist1;
    }

    public Album getAlbum(String name){
        LambdaQueryWrapper<Album> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(Album::getTitle, name);
        Album artist = albumService.getOne(lambdaQuery);
        return artist;
    }

    @PostMapping("/uploadLyric")
    public void handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            String s = file.getOriginalFilename().replace(".txt", "").replace(".lrc", "");
            String[] arr = s.split("-");
            String artistName = arr[0].trim();
            String musicName = arr[1].trim();
            InputStream inputStream = file.getInputStream();

            String content = detect(inputStream);

            Music music = getMusic(musicName);

            Lyric old = getLyric(music.getId());
            if(old != null){
                Lyric lyric = new Lyric();
                lyric.setLyric(content);
                lyric.setId(music.getId());
                lyricService.modify(lyric);
                System.out.println(content);
            }else{
                Lyric lyric = new Lyric();
                lyric.setLyric(content);
                lyric.setId(music.getId());
                lyricService.add(lyric);
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Lyric getLyric(String id){
        LambdaQueryWrapper<Lyric> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(Lyric::getId, id);
        Lyric old = lyricService.getOne(lambdaQuery);
        return old;
    }

    public String detect(InputStream inputStream) throws IOException {
        try(BOMInputStream bomInputStream = new BOMInputStream(inputStream, false, ByteOrderMark.UTF_8,
                ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_16BE)) {
            String charset = bomInputStream.getBOMCharsetName();
            if(charset == null){
                charset = "gbk";
            }
            return IOUtils.toString(bomInputStream, charset);
        }
    }

    private Music getMusic(String name){
        LambdaQueryWrapper<Music> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(Music::getMusicName, name);
        Music music = musicService.getOne(lambdaQuery);
        return music;
    }

    private String saveFile(MultipartFile file, String id) throws IOException {
        String fileName = id+".mp3";
        File savedFile = new File(mp3uploadDir + id+".mp3");
        file.transferTo(savedFile);
        return fileName;
    }

    private String getFileNameWithoutExtension(String fileName) {
        Pattern pattern = Pattern.compile("(.*)\\.[^.]+$");
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return fileName;
        }
    }

    private int getMp3Length(String fileName) throws IOException, UnsupportedAudioFileException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        File file = new File(mp3uploadDir + fileName);
        System.out.println(file.getAbsoluteFile().canRead());

        AudioFile audioFile = AudioFileIO.read(file);
        int size = audioFile.getAudioHeader().getTrackLength();

        return size;
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

    @GetMapping(value = "/lyric")
    @ApiOperation(value = "查询分页列表", notes = "根据条件查询分页列表")
    public ResultVO<LyricVO> lyric(@RequestParam final String id) {

        if(StringUtils.isBlank(id)){
            return failed();
        }
        final Lyric po = lyricService.getById(id);
        final LyricVO vo = mappingService.mapping(po, LyricVO.class);
        return success(vo);
    }

    private MusicVO convert(Music music, List<Object> objects){
        MusicVO musicVO = mappingService.mapping(music, MusicVO.class);
        musicVO.setName(music.getMusicName());
        Map<String, Album> albumMap = (Map<String, Album>)objects.get(0);
        Map<String, Artist> artistMap =  (Map<String, Artist>)objects.get(1);
        musicVO.setAlbum(convertAlbum(albumMap.get(music.getAlbumId())));
        musicVO.setArtists(convertArtist(artistMap.get(albumMap.get(music.getAlbumId()).getArtistId())));
        return musicVO;
    }

    public AlbumVO convertAlbum(Album album){
        AlbumVO albumVO = new AlbumVO();
        albumVO.setName(album.getTitle());
        albumVO.setPicUrl(album.getImg());
        return albumVO;
    }

    public List<ArtistVO> convertArtist(Artist artist){
        List<ArtistVO> artistVOS = new ArrayList<>();
        ArtistVO artistVO = new ArtistVO();
        artistVO.setName(artist.getArtistName());
        artistVOS.add(artistVO);

        return artistVOS;
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