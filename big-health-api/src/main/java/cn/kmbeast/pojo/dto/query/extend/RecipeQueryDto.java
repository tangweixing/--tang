package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 健康模型查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RecipeQueryDto extends QueryDto {


    /**
     * 食谱名称
     */
    private String name;
    /**
     * 食谱用户ID
     */
    private Integer typeId;
    /**
     * 食谱用户ID
     */
    private Integer userId;

    /**
     * 食谱是否审核
     */
    private Boolean isAudit;

}
