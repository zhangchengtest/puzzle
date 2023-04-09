package com.elephant.api.base;

import com.cunw.framework.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * API基类
 *
 * @author cunw generator
 * date 2023-04-08
 * 湖南新云网科技有限公司版权所有.
 */
public interface BaseApi<DTO, VO> {
    /**
     * 公共新增方法
     * @param dto 新增请求数据
     * @return 新增结果
     */
    @PostMapping("/")
    @ApiOperation(value="创建", notes="根据DTO对象创建")
    ResultVO<Boolean> add(@RequestBody final DTO dto);

    /**
     * 公共根据ID查询对象
     * @param id 主键ID
     * @return 查询结果
     */
    @GetMapping("/{id}")
    @ApiOperation(value="获取详细信息", notes="根据url的id来获取详细信息")
    ResultVO<VO> get(@PathVariable final String id);

    /**
     * 公共修改
     * @param id 主键ID
     * @param dto 修改数据
     * @return 修改结果
     */
    @PostMapping("/{id}")
    @ApiOperation(value="更新详细信息", notes="根据url的id来指定更新对象，并根据传过来的DTO信息来更新详细信息")
    ResultVO<VO> update(@PathVariable final String id, @RequestBody final DTO dto);

    /**
     * 公共删除
     * @param id 主键ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除", notes="根据url的id来指定删除对象")
    ResultVO<?> delete(@PathVariable final String id);
}