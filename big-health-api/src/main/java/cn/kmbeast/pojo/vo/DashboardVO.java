package cn.kmbeast.pojo.vo;

import cn.kmbeast.pojo.entity.DietHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class DashboardVO {
    /*
    * 用户数
    * */
    private Integer userCount;
    /*
    健康模型数
     */
    private Integer modelCount;
    /*
    健康资讯数
     */
    private Integer healthNewsCount;
    /*
    食谱数
     */
    private Integer recipeCount;
    /*
    点赞数
     */
    private Integer upvoteCount;
    /*
    收藏数
     */
    private Integer collectCount;
    /*
    多少人看过数
     */
    private Integer peopleCount;
    /*
    点击量
     */
    private Integer clickCount;
}
