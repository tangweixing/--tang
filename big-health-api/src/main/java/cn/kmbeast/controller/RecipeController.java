package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HealthModelQueryDto;
import cn.kmbeast.pojo.dto.query.extend.RecipeQueryDto;
import cn.kmbeast.pojo.entity.HealthModel;
import cn.kmbeast.pojo.entity.Recipe;
import cn.kmbeast.pojo.vo.OptionsVO;
import cn.kmbeast.pojo.vo.RecipeVO;
import cn.kmbeast.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 食谱
 */
@RestController
@RequestMapping("/recipe")
// 接口分组标签（文档中按标签归类接口，便于查找）
@Api(tags = "食谱管理接口", description = "处理食谱注册、查询、修改操作")
public class RecipeController {

    @Resource
    private RecipeService recipeService;

    /**
     * 新增食谱
     *
     * @param recipe 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/insert")
    @ResponseBody
    @ApiOperation(value = "新增食谱")
    public Result<String> insertEntity(@RequestBody Recipe recipe) {
        return recipeService.insertEntity(recipe);
    }
    /**
     * 修改食谱
     *
     * @param recipe 注册入参
     * @return Result<String> 响应结果
     */
    @PutMapping(value = "/update")
    @ResponseBody
    @ApiOperation(value = "修改食谱")
    public Result<String> updateEntity(@RequestBody Recipe recipe) {
        return recipeService.updateEntity(recipe);
    }
    /**
     * 删除食谱
     *
     * @param ids 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    @ApiOperation(value = "删除食谱列表")
    public Result<String> deleteByIds(@RequestBody List<Integer> ids) {
        return recipeService.deleteByIds(ids);
    }
    /**
     * 列表查询食谱
     *
     * @param recipeQueryDto 注册入参
     * @return Result<String> 响应结果
     */
    @Pager
    @PostMapping(value = "/queryList")
    @ResponseBody
    @ApiOperation(value = "列表查询健康模型")
    public Result<List<RecipeVO>> queryList(@RequestBody RecipeQueryDto recipeQueryDto) {
        return recipeService.queryList(recipeQueryDto);
    }
    /**
     * 查询食谱类型
     *
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/fetchRecipeTypes")
    @ResponseBody
    @ApiOperation(value = "查询食谱类型")
    public Result<List<OptionsVO>> fetchRecipeTypes() {
        return recipeService.fetchRecipeTypes();
    }
    /**
     * 查询食谱类型
     *
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    @ApiOperation(value = "查询食谱类型")
    public Result<Recipe> queryById(@PathVariable Integer id) {
        return recipeService.queryById(id);
    }
}

