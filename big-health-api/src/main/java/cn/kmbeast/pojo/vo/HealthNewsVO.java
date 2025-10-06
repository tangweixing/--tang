package cn.kmbeast.pojo.vo;

import cn.kmbeast.pojo.entity.HealthNews;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HealthNewsVO extends HealthNews {
    /*
    * 类型名称
    * */
    private String typeName;
}
