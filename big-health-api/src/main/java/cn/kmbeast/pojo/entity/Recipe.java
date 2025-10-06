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
 * 食谱实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("recipe")
public class Recipe {

    /**
     * 食谱编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 食谱名称
     */
    private String name;
    /**
     * 食谱用户ID
     */
    private Integer typeId;
    /**
     * 食谱封面
     */
    private String cover;
    /**
     * 食谱用户ID
     */
    private Integer userId;

    /**
     * 食谱内容
     */
    private String content;
    /**
     * 食谱是否审核
     */
    private Boolean isAudit;

    /**
     * 食谱注册时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 食谱更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
