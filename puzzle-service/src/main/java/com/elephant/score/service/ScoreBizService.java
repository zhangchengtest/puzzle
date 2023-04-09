package com.elephant.score.service;

import com.elephant.api.dto.score.ScoreDTO;
import com.elephant.api.vo.score.ScoreVO;

/**
 * Score-服务接口
 *
 * @author cunw generator
 * date 2023-04-09
 * 湖南新云网科技有限公司版权所有.
 */

public interface ScoreBizService {

    /**
     * 公共新增方法
     * @param dto 新增请求数据
     */
    void add(final ScoreDTO dto);

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     */
    ScoreVO get(final String id);

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     */
    void update(final String id, final ScoreDTO dto);

    /**
     * 公共删除
     * @param id 主键ID
     */
    void delete(final String id);
}