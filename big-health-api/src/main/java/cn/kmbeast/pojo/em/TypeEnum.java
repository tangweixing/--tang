package cn.kmbeast.pojo.em;

import cn.kmbeast.pojo.vo.OptionsVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类枚举
 */
@Getter
@AllArgsConstructor
public enum TypeEnum {
    //食谱模块类别
    RECIPE_1(1, "RECIPE","增肌"),
    RECIPE_2(2, "RECIPE","减脂"),

    //健康资讯模块类别
    NEWS_1(1, "NEWS","有氧运动"),
    NEWS_2(2,"NEWS","无氧运动") ,
    //食谱记录模块
    RECIPE_DIET_1(1,"RECIPE_DIET","早餐"),
    RECIPE_DIET_2(2,"RECIPE_DIET","中餐"),
    RECIPE_DIET_3(3,"RECIPE_DIET","晚餐");

    /**
     * 分类编码ID
     */
    private final Integer id;
    /**
     * 分类模块
     */
    private final String module;
    /**
     * 分类名
     */
    private final String name;

    /**
     * 由分类编码获取分类名
     *
     * @param module 分类编码
     * @return List<OptionsVO> 分类名
     */
    public static List<OptionsVO> getOptionsByModule(String module) {
        List<OptionsVO> optionsVOS = new ArrayList<>();
        for (TypeEnum typeEnum : TypeEnum.values()) {
            if (typeEnum.getModule().equals(module)) {
                OptionsVO optionsVO = new OptionsVO(
                        typeEnum.getId(),
                        typeEnum.getName()
                );
                optionsVOS.add(optionsVO);
            }
        }
        return optionsVOS;
    }

}
