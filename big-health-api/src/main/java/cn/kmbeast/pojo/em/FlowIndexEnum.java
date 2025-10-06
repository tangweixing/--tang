package cn.kmbeast.pojo.em;

import cn.kmbeast.pojo.vo.OptionsVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 流量指标分类枚举
 */
@Getter
@AllArgsConstructor
public enum FlowIndexEnum {
    //食谱模块类别
    FLOW_INDEX_1(1, "展现"),
    FLOW_INDEX_2(2, "浏览"),
    FLOW_INDEX_3(3, "点赞"),
    FLOW_INDEX_4(4, "收藏"),
    FLOW_INDEX_5(5,"停留");

    /**
     * 分类编码ID
     */
    private final Integer id;

    /**
     * 分类名
     */
    private final String type;


}
