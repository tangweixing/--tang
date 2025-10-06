package cn.kmbeast.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 流量指标实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("flow_index")
public class FlowIndex {

    /**
     * 流量指标编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
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
    /**
     * 停留时间
     */
    private Long times;

    /**
     * 流量指标注册时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 流量指标更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
