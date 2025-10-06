package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.RecipeQueryDto;
import cn.kmbeast.pojo.entity.Recipe;
import cn.kmbeast.pojo.vo.OptionsVO;
import cn.kmbeast.pojo.vo.RecipeVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RecipeService extends IService<Recipe> {
    Result<String> insertEntity(Recipe recipe);

    Result<String> updateEntity(Recipe recipe);



    Result<String> deleteByIds(List<Integer> ids);


    Result<List<RecipeVO>> queryList(RecipeQueryDto recipeQueryDto);

    Result<List<OptionsVO>> fetchRecipeTypes();

    Result<Recipe> queryById(Integer id);
}
