package com.elephant.music.controller;

import com.cunw.boot.service.IBeanMappingService;
import com.elephant.api.api.music.CommentApi;
import com.elephant.api.dto.music.CommentDTO;
import com.elephant.api.vo.music.CommentVO;
import com.elephant.common.model.music.Comment;
import java.util.Arrays;
import com.cunw.framework.constant.MarkConstants;
import com.elephant.music.service.CommentService;
import com.elephant.music.service.CommentBizService;
import com.elephant.music.service.CommentHelperService;
import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.cunw.framework.utils.base.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
/**
 * CommentController
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController extends BaseController implements CommentApi {
    private final CommentService commentService;
    private final CommentHelperService commentHelperService;
    private final CommentBizService commentBizService;
    private final IBeanMappingService mappingService;

    @Override
    public ResultVO<Boolean> add(final CommentDTO dto){
         commentBizService.add(dto);
         return success(true);
    }

    @Override
    public ResultVO<CommentVO> get(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final Comment po = commentService.getById(id);
        final CommentVO vo = mappingService.mapping(po, CommentVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<CommentVO> update(final String id, final CommentDTO dto){
        Comment po = mappingService.mapping(dto, Comment.class);
        commentService.modify(id, po);
        po = commentService.getById(id);
        final CommentVO vo = mappingService.mapping(po, CommentVO.class);
        return success(vo);
    }

    @Override
    public ResultVO<?> delete(final String id){
        if(StringUtils.isBlank(id)){
           return failed();
        }
        final String sep = MarkConstants.COMMA;
        if (StringUtils.indexOf(id, sep) == -1) {
            commentService.delete(id);
        } else {
            commentService.delete(Arrays.asList(id.split(sep)));
        }
        return success();
    }
}