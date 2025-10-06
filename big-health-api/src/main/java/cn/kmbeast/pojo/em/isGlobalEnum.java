package cn.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 是否为全局变量
 */
@Getter
@AllArgsConstructor
public enum isGlobalEnum {
    PUBLIC(true),
    PRIVATE(false);
    private final Boolean status;//状态

}
