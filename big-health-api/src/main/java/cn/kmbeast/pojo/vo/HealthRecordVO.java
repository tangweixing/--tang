package cn.kmbeast.pojo.vo;

import cn.kmbeast.pojo.entity.HealthRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
健康记录的VO类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthRecordVO extends HealthRecord {
    /*
    用户名
    * */
    private String userName;
    /*
    用户头像
    * */
    private String avatar;
    /*
    健康模型名称
     */
    private String healthModelName;
    /*
    健康模型单位
     */
    private String healthModelUnit;
    /*
    正常阈值
     */
    private String normalValue;
}
