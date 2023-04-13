package com.elephant.music.service.impl;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.dto.music.LyricDTO;
import com.elephant.api.vo.music.LyricVO;
import com.elephant.common.model.music.Lyric;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.LyricHelperService;
import com.elephant.music.service.LyricService;
import org.springframework.stereotype.Service;

/**
 * Lyric-服务接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class LyricHelperServiceImpl implements LyricHelperService {

    private final IBeanMappingService mappingService;
    private final LyricService lyricService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final LyricDTO dto){
        final Lyric po = mappingService.mapping(dto, Lyric.class);
        lyricService.add(po);
    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public LyricVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final LyricDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}