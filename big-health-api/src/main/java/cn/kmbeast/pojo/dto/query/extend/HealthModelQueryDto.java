package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 健康模型查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HealthModelQueryDto extends QueryDto {

    /**
     * 健康模型名称
     */
    private String name;
    /**
     * 健康模型用户ID
     */
    private Integer userId;
    /**
     * 健康模型用户ID
     */
    private String userName;
    /**
     * 健康模型是否为全局变量
     */
    private Boolean isGlobal;

}
