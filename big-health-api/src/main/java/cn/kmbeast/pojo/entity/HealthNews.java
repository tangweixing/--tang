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
 * 健康咨询实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("health_news")
public class HealthNews {

    /**
     * 健康咨询编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 健康咨询发布用户可有可无
     */
    private Integer userId;

    /**
     * 健康咨询类型ID
     */
    private Integer typeId;

    /**
     * 健康咨询封面
     */
    private String cover;
    /**
     * 健康咨询标题
     */
    private String title;
    /**
     * 健康咨询内容
     */
    private String content;
    /**
     * 健康咨询内容摘要
     */
    private String summary;


    /**
     * 健康咨询注册时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 健康咨询更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
