package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 食谱记录查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DietHistoryQueryDto extends QueryDto {



    /**
     * 食谱ID
     */
    private Integer recipeId;
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 食谱记录餐
     */
    private Integer detail;

}
