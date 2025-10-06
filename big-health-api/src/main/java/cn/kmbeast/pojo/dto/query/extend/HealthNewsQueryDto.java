package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 健康资讯查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HealthNewsQueryDto extends QueryDto {
    /**
     * 健康咨询类型ID
     */
    private Integer typeId;
    /**
     * 健康咨询标题
     */
    private String title;

}
