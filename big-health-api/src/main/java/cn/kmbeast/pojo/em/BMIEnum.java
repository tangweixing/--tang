package cn.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 获得BMI的Id
 */
@Getter
@AllArgsConstructor
public enum BMIEnum {
    BMI(20);

    private final Integer ID;//状态
}
