package com.elephant.music.service.impl;

import com.elephant.api.dto.music.ArtistDTO;
import com.elephant.api.vo.music.ArtistVO;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.ArtistBizService;
import com.elephant.music.service.ArtistHelperService;
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
public class ArtistBizServiceImpl implements ArtistBizService {

    private final ArtistHelperService artistHelperService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final ArtistDTO dto){

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