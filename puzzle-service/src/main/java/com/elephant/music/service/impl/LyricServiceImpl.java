package com.elephant.music.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.music.Lyric;
import com.elephant.music.mapper.LyricMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.LyricService;
import org.springframework.stereotype.Service;

/**
 * Lyric-服务实现
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class LyricServiceImpl extends BaseOrmServiceImpl<Lyric, String> implements LyricService {
    private final LyricMapper lyricMapper;

    @Override
    protected IBaseMapper<Lyric, String> getMapper() {
        return this.lyricMapper;
    }

}
