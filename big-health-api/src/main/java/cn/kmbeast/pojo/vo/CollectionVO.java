package cn.kmbeast.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionVO {
    private Integer id;
    private String title; // 健康资讯标题或食谱名称
    private String cover; // 健康资讯标题或食谱封面
    private String module; // 所属模块（health_news 或 recipe）
    private Integer contentId;
}