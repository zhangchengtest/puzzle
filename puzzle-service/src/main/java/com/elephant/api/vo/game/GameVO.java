package com.elephant.api.vo.game;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * -VO
 *
 * @author cunw generator
 * 2023-07-03
 * 湖南新云网科技有限公司版权所有.
 */
@Data
@ApiModel(description="-VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameVO implements Serializable {

    private String title;

    private String playerOne;

    private String playerTwo;

    private String playTime;
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


    private Integer china;

    private String countryOne;

    private String countryTwo;

    private String playerOrder1;

    private String playerOrder2;

    private String history;

}
