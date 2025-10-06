package cn.kmbeast.pojo.vo;

import cn.kmbeast.pojo.entity.Recipe;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
评分矩阵
 */
@Data
public class FlowIndexScoreVO{
    /*
    * 用户ID
    * */
    private Integer userId;
    /*
     * 内容ID
     * */
    private Integer contentId;
    /*
     * 评分
     * */
    private Double  score;
}
