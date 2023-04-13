package com.elephant.music.service.impl;

import com.elephant.api.dto.music.MusicDTO;
import com.elephant.api.vo.music.MusicVO;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.MusicBizService;
import com.elephant.music.service.MusicHelperService;
import org.springframework.stereotype.Service;
/**
 * Music-服务接口
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class MusicBizServiceImpl implements MusicBizService {

    private final MusicHelperService musicHelperService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final MusicDTO dto){

    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public MusicVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final MusicDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}