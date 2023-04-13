package com.elephant.music.service.impl;

import com.cunw.orm.mapper.IBaseMapper;
import com.elephant.common.model.music.Album;
import com.elephant.music.mapper.AlbumMapper;
import com.cunw.orm.service.impl.BaseOrmServiceImpl;
import lombok.RequiredArgsConstructor;
import com.elephant.music.service.AlbumService;
import org.springframework.stereotype.Service;

/**
 * Album-服务实现
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl extends BaseOrmServiceImpl<Album, String> implements AlbumService {
    private final AlbumMapper albumMapper;

    @Override
    protected IBaseMapper<Album, String> getMapper() {
        return this.albumMapper;
    }

}
