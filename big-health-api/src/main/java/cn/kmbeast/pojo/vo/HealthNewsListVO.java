package cn.kmbeast.pojo.vo;

import lombok.Data;

@Data
public class HealthNewsListVO {
    /*
    * 主键ID
    */
    private Integer id;
    /*
     * 标题
     */
    private String title;
    /*
     * 封面
     */
    private String cover;
/*
浏览量
 */
    private Integer viewCount;

}
