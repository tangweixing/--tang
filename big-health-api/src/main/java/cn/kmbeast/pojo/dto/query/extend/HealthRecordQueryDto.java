package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 健康记录查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HealthRecordQueryDto extends QueryDto {


    /**
     * 健康记录用户ID
     */
    private Integer userId;
    /**
     * 健康模型记录类型ID
     */
    private Integer healthModelId;

}
