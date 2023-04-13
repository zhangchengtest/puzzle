package com.elephant.music.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.music.Artist;
import com.elephant.music.mapper.ArtistMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.ArtistService;
import org.springframework.stereotype.Service;

/**
 * Artist-服务实现
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class ArtistServiceImpl extends BaseOrmServiceImpl<Artist, String> implements ArtistService {
    private final ArtistMapper artistMapper;

    @Override
    protected IBaseMapper<Artist, String> getMapper() {
        return this.artistMapper;
    }

}
