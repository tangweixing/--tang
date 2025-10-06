package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HealthModelQueryDto;
import cn.kmbeast.pojo.entity.HealthModel;
import cn.kmbeast.pojo.vo.ChartVO;
import cn.kmbeast.pojo.vo.CollectionVO;
import cn.kmbeast.pojo.vo.OptionsVO;
import cn.kmbeast.service.HealthModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 健康模型
 */
@RestController
@RequestMapping("/health-model")
// 接口分组标签（文档中按标签归类接口，便于查找）
@Api(tags = "健康模型管理接口", description = "处理健康模型注册、查询、修改操作")
public class HealthModelController {

    @Resource
    private HealthModelService healthModelService;

    /**
     * 新增健康模型
     *
     * @param healthModel 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/insert")
    @ResponseBody
    @ApiOperation(value = "新增健康模型")
    public Result<String> insertEntity(@RequestBody HealthModel healthModel) {
        return healthModelService.insertEntity(healthModel);
    }
    /**
     * 修改健康模型
     *
     * @param healthModel 注册入参
     * @return Result<String> 响应结果
     */
    @PutMapping(value = "/update")
    @ResponseBody
    @ApiOperation(value = "修改健康模型")
    public Result<String> updateEntity(@RequestBody HealthModel healthModel) {
        return healthModelService.updateEntity(healthModel);
    }
    /**
     * 删除健康模型
     *
     * @param ids 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    @ApiOperation(value = "删除健康模型列表")
    public Result<String> deleteByIds(@RequestBody List<Integer> ids) {
        return healthModelService.deleteByIds(ids);
    }
    /**
     * 列表查询健康模型
     *
     * @param healthModelQueryDto 注册入参
     * @return Result<String> 响应结果
     */
    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    @ApiOperation(value = "列表查询健康模型")
    public Result<List<HealthModel>> list(@RequestBody HealthModelQueryDto healthModelQueryDto) {
        return healthModelService.list(healthModelQueryDto);
    }
    /**
     * 列表查询健康模型
     *
     * @param healthModelQueryDto 注册入参
     * @return Result<String> 响应结果
     */
    @Pager
    @PostMapping(value = "/queryList")
    @ResponseBody
    @ApiOperation(value = "列表查询健康模型")
    public Result<List<HealthModel>> queryList(@RequestBody HealthModelQueryDto healthModelQueryDto) {
        return healthModelService.queryList(healthModelQueryDto);
    }
    /**
     * 列表查询健康模型
     *
     *
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/fetchAllModelName")
    @ResponseBody
    @ApiOperation(value = "查询全部健康模型名称")
    public Result<List<OptionsVO>> fetchAllModelName() {
        return healthModelService.fetchAllModelName();
    }

    /**
     * 列表查询健康模型
     *
     *
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/fetchModelNameByUserId")
    @ResponseBody
    @ApiOperation(value = "查询全部健康模型名称")
    public Result<List<OptionsVO>> fetchModelNameByUserId() {
        return healthModelService.fetchModelNameByUserId();
    }
    /**
     * 获取对应日期 和 健康模型创建数量
     *
     *
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/queryByDay")
    @ResponseBody
    @ApiOperation(value = "查询全部健康模型名称")
    public Result<List<ChartVO>> queryByDay(@RequestBody HealthModelQueryDto healthModelQueryDto) {
        return healthModelService.queryByDay(healthModelQueryDto);
    }
}

