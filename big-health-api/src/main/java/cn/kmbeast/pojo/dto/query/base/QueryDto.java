package cn.kmbeast.pojo.dto.query.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 查询参数接收实体类基类，含有四项基础参数，使用时可以拓展
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class QueryDto {
    /**
     * 查询ID列表
     */
    private List<Integer> ids;
    /**
     * 当前页
     */
    private Integer current;
    /**
     * 页面数据大小
     */
    private Integer size;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}

