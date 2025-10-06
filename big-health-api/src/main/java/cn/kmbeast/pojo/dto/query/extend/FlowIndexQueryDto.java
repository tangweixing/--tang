package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 健康模型查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlowIndexQueryDto extends QueryDto {

    /**
     * 流量指标编号用户ID
     */
    private Integer userId;

    /**
     * 流量指标类型ID(1、展现 2、浏览 3、点赞 4、收藏 5、分享 6、停留)
     */
    private Integer type;

    /**
     * 流量指标内容模块
     */
    private String contentModule;
    /**
     * 流量指标内容ID
     */
    private Integer contentId;

}
