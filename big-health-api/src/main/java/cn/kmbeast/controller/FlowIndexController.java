package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.FlowIndexQueryDto;
import cn.kmbeast.pojo.entity.FlowIndex;
import cn.kmbeast.pojo.vo.*;

import cn.kmbeast.service.FlowIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流量指标
 */
@RestController
@RequestMapping("/flowIndex")
// 接口分组标签（文档中按标签归类接口，便于查找）
@Api(tags = "流量指标管理接口", description = "处理流量指标注册、查询、修改操作")
public class FlowIndexController {

    @Resource
    private FlowIndexService flowIndexService;

    /**
     * 新增流量指标
     *
     * @param flowIndex 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/insert")
    @ResponseBody
    @ApiOperation(value = "新增流量指标")
    public Result<String> insertEntity(@RequestBody FlowIndex flowIndex) {
        return flowIndexService.insertEntity(flowIndex);
    }
    /**
     * 删除流量指标
     *
     * @param id 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/delete/{id}")
    @ResponseBody
    @ApiOperation(value = "删除流量指标列表")
    public Result<String> delete(@PathVariable Integer id) {
        return flowIndexService.delete(id);
    }
    /**
     * 列表查询流量指标
     *
     * @param flowIndexQueryDto 注册入参
     * @return Result<String> 响应结果
     */
    @Pager
    @PostMapping(value = "/queryList")
    @ResponseBody
    @ApiOperation(value = "列表查询健康模型")
    public Result<List<FlowIndex>> queryList(@RequestBody FlowIndexQueryDto flowIndexQueryDto) {
        return flowIndexService.queryList(flowIndexQueryDto);
    }
    /**
     * 列表查询收藏列表
     *
     *
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/queryCollection")
    @ResponseBody
    @ApiOperation(value = "列表查询收藏列表")
    public Result<List<CollectionVO>> queryCollection() {
        return flowIndexService.queryCollection();
    }
    /**
     * 列表查询资讯流量
     *
     *
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/queryDashboard")
    @ResponseBody
    @ApiOperation(value = "列表查询资讯流量")
    public Result<DashboardVO> queryDashboard(@RequestBody FlowIndexQueryDto flowIndexQueryDto) {
        return flowIndexService.queryDashboard(flowIndexQueryDto);
    }
    /**
     * 列表推荐最热资讯
     *
     *
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/recommendHealthNews")
    @ResponseBody
    @ApiOperation(value = "列表查询资讯流量")
    public Result<List<HealthNewsListVO>> recommendHealthNews() {
        return flowIndexService.recommendHealthNews();
    }
    /**
     * 列表推荐食谱
     *
     *
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/recommendRecipe")
    @ResponseBody
    @ApiOperation(value = "列表推荐食谱")
    public Result<List<RecipeVO>> recommendRecipe() {
        return flowIndexService.recommendRecipe();
    }
}

