package cn.kmbeast.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 健康模型记录实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("health_record")
public class HealthRecord {

    /**
     * 健康模型记录编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 健康模型记录发布用户可有可无
     */
    private Integer userId;

    /**
     * 健康模型记录类型ID
     */
    private Integer healthModelId;

    /**
     * 健康模型记录值
     */
    private Double value;

    /**
     * 健康模型记录注册时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 健康模型记录更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
