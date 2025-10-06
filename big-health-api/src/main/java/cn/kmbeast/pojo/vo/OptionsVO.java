package cn.kmbeast.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionsVO {
    /*
        值
    */
   private Integer value;
    /*
        名称
    */
    private String label;
}
