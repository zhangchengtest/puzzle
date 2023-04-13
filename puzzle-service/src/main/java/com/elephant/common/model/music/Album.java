package com.elephant.common.model.music;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.cunw.framework.model.IBasePO;
import java.util.Date;

/**
 * -数据模型
 *
 * @author cunw generator
 * date 2023-04-13
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@TableName("album")
public class Album implements IBasePO<String> {
    @TableId("album_id")
    private String id;
    private Long artistId;
    private String title;
    private String img;
    /**
     * 状态：1-启用 0-停用 -1-删除
     */
    private Integer status;
    /**
     * 创建人
     */
    private String createUserCode;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改人
     */
    private String updateUserCode;
    /**
     * 修改时间
     */
    private Date updateDate;
}
