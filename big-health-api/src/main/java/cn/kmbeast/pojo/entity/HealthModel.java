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
 * 健康模型实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("health_model")
public class HealthModel {

    /**
     * 健康模型编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 健康模型名称
     */
    private String name;

    /**
     * 健康模型简介
     */
    private String detail;

    /**
     * 健康模型图标
     */
    private String iconUrl;
    /**
     * 健康模型单位
     */
    private String unit;
    /**
     * 健康模型符号
     */
    private String symbol;
    /**
     * 健康模型正常值范围
     */
    private String normalValue;

    /**
     * 健康模型用户ID
     */
    private Integer userId;

    /**
     * 健康模型是否是全局变量
     */
    private Boolean isGlobal;
    /**
     * 健康咨询注册时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
