package cn.kmbeast.pojo.vo;

import cn.kmbeast.pojo.entity.DietHistory;
import cn.kmbeast.pojo.entity.Recipe;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DietHistoryVO extends DietHistory {
    /*
    * 类型名称
    * */
    private String dietName;
    /*
    用户头像
     */
    private String userAvatar;
    /*
    用户名称
     */
    private String userName;
    /*
    食谱名称
     */
    private String recipeName;
}
