package com.elephant.music.service.impl;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.dto.music.ArtistDTO;
import com.elephant.api.vo.music.ArtistVO;
import com.elephant.common.model.music.Artist;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.ArtistHelperService;
import com.elephant.music.service.ArtistService;
import org.springframework.stereotype.Service;

/**
 * Artist-服务接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class ArtistHelperServiceImpl implements ArtistHelperService {

    private final IBeanMappingService mappingService;
    private final ArtistService artistService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final ArtistDTO dto){
        final Artist po = mappingService.mapping(dto, Artist.class);
        artistService.add(po);
    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public ArtistVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final ArtistDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}