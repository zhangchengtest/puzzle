package com.elephant.common.model.game;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.cunw.framework.model.IBasePO;
import java.util.Date;

/**
 * -数据模型
 *
 * @author cunw generator
 * date 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@TableName("game")
public class Game implements IBasePO<String> {
    @TableId("id")
    private String id;
    private String title;
    private String playDay;
    private String playerOne;

    private String playerOrder1;

    private String playerOrder2;

    private String history;

    private String countryOne;
    private String playerTwo;
    private String countryTwo;
    private Date playDate;
    private String oneScore1;
    private String oneScore2;
    private String oneScore3;
    private String oneScore4;
    private String oneScore5;
    private String oneScore6;
    private String twoScore1;
    private String twoScore2;
    private String twoScore3;
    private String twoScore4;
    private String twoScore5;
    private String twoScore6;

    private String odd1;
    private String odd2;

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
