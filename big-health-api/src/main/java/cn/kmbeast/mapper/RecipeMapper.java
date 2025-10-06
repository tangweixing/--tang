package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.RecipeQueryDto;
import cn.kmbeast.pojo.entity.Recipe;
import cn.kmbeast.pojo.vo.RecipeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecipeMapper extends BaseMapper<Recipe> {
     List<RecipeVO> listByRecommendIDS(@Param("recommendIDS")List<Integer> recommendIDS);


    List<RecipeVO> queryList(RecipeQueryDto recipeQueryDto);

    Integer queryListCount(RecipeQueryDto recipeQueryDto);

    List<Integer> listIds();

    List<RecipeVO> selectRecipeListWithViewCountByIds(List<Integer> ids);
}
