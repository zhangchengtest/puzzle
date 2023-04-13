package com.elephant.music.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.music.Music;
import com.elephant.music.mapper.MusicMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.MusicService;
import org.springframework.stereotype.Service;

/**
 * Music-服务实现
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class MusicServiceImpl extends BaseOrmServiceImpl<Music, String> implements MusicService {
    private final MusicMapper musicMapper;

    @Override
    protected IBaseMapper<Music, String> getMapper() {
        return this.musicMapper;
    }

}
