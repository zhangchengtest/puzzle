package com.elephant.game.service.impl;

import com.elephant.api.dto.game.GameDTO;
import com.elephant.api.vo.game.GameVO;
import lombok.RequiredArgsConstructor;
import com.elephant.game.service.GameBizService;
import com.elephant.game.service.GameHelperService;
import org.springframework.stereotype.Service;
/**
 * Game-服务接口
 *
 * @author cunw generator
 * date 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@Service
@RequiredArgsConstructor
public class GameBizServiceImpl implements GameBizService {

    private final GameHelperService gameHelperService;
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    public void add(final GameDTO dto){

    }

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    public GameVO get(final String id){
        return null;
    }

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    public void update(final String id, final GameDTO dto){

    }

    /**
     * 公共删除
     * @param id 主键ID
     */
    public void delete(final String id){

    }
}