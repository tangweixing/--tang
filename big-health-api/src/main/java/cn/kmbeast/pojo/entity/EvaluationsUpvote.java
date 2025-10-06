package cn.kmbeast.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论点赞实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("evaluations_upvote")
public class EvaluationsUpvote {

    /**
     * 评论点赞编号
     */
    private Integer id;

    /**
     * 评论点赞关联用户ID
     */
    private Integer userId;

    /**
     * 评论点赞关联评论ID
     */
    private Integer evaluationsId;

}
