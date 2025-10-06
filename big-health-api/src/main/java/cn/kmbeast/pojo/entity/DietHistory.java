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
 * 饮食记录实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("diet_history")
public class DietHistory {

    /**
     * 饮食记录编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 饮食记录关联用户ID
     */
    private Integer userId;

    /**
     * 饮食记录关联食谱ID
     */
    private Integer recipeId;
    /**
     * 饮食记录能量
     */
    private Integer energy;
    /**
     * 饮食记录备注
     */
    private Integer detail;

    /**
     * 饮食记录注册时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 饮食记录更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
