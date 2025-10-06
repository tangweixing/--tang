package cn.kmbeast.pojo.vo;

import cn.kmbeast.pojo.entity.HealthNews;
import cn.kmbeast.pojo.entity.Recipe;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RecipeVO extends Recipe {
    /*
    * 类型名称
    * */
    private String typeName;
    /*
    展示次数
     */
    private Integer viewCount;
}
