package cn.kmbeast.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.RecipeMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.RecipeQueryDto;
import cn.kmbeast.pojo.em.RoleEnum;
import cn.kmbeast.pojo.em.TypeEnum;
import cn.kmbeast.pojo.entity.Recipe;
import cn.kmbeast.pojo.vo.OptionsVO;
import cn.kmbeast.pojo.vo.RecipeVO;
import cn.kmbeast.service.RecipeService;
import cn.kmbeast.utils.AssertUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper,Recipe> implements RecipeService {
    private static final boolean DeflautAudit=false;
    /**
     * 新增食谱
     *
     * @param recipe 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> insertEntity(Recipe recipe) {
        //判断能否插入
        validParam(recipe);

        //设置创建的用户ID
        Integer userId = LocalThreadHolder.getUserId();
        recipe.setUserId(userId);
        if (!userId.equals(RoleEnum.ADMIN.getRole()))
        recipe.setIsAudit(DeflautAudit);
        //插入数据
        save(recipe);
        return ApiResult.success();
    }

    private void validParam(Recipe recipe) {
        AssertUtils.notNull(recipe,"实体类不能为空");
        AssertUtils.between(recipe.getName().length(),0,20,"名称在20字以内");
        AssertUtils.notBlank(recipe.getCover(),"封面不能为空");
        AssertUtils.notBlank(recipe.getContent(),"内容不能为空");
        AssertUtils.notNull(recipe.getTypeId(),"类型不能为空");
    }
    /**
     * 修改食谱
     *
     * @param recipe 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> updateEntity(Recipe recipe) {
        //判断能否修改
        validParam(recipe);
        //数据修改
        updateById(recipe);
        return ApiResult.success("食谱修改成功");
    }
    /**
     * 删除食谱
     *
     * @param ids 注册入参
     * @return Result<String> 响应结果
     */

    @Override
    @Transactional
    public Result<String> deleteByIds(List<Integer> ids) {
        //删除健康咨询
        removeBatchByIds(ids);
        return ApiResult.success("食谱删除成功");
    }

    @Override
    public Result<List<RecipeVO>> queryList(RecipeQueryDto recipeQueryDto) {
        //1.查询出recipes
        List<RecipeVO> recipeVOS = this.baseMapper.queryList(recipeQueryDto);
//3.设置typeId对应model对应的名称
        recipeVOS.forEach(
                recipeVO -> {
                    List<OptionsVO> optionsVOS = TypeEnum.getOptionsByModule("RECIPE");
                    optionsVOS.forEach(
                            optionsVO -> {
                                if (recipeVO.getTypeId().equals(optionsVO.getValue())){
                                    recipeVO.setTypeName(optionsVO.getLabel());
                                }
                            }
                    );
                }
        );
        //4.计算总查询数
        Integer count = this.baseMapper.queryListCount(recipeQueryDto);
        return ApiResult.success(recipeVOS, count);
    }

    @Override
    public Result<List<OptionsVO>> fetchRecipeTypes() {
        return ApiResult.success(TypeEnum.getOptionsByModule("RECIPE"));
    }

    @Override
    public Result<Recipe> queryById(Integer id) {
        return ApiResult.success(this.baseMapper.selectById(id));
    }
}
