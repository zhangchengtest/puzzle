package com.elephant.common.model.score;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.cunw.framework.model.IBasePO;
import java.util.Date;

/**
 * 积分表，用于记录用户的积分变动情况-数据模型
 *
 * @author cunw generator
 * date 2023-04-08
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@TableName("score")
public class Score implements IBasePO<String> {
    /**
     * 积分记录的唯一标识
     */
    @TableId("id")
    private String id;
    /**
     * 积分所属的用户 ID
     */
    private String userId;
    /**
     * 积分数量
     */
    private Integer score;
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
