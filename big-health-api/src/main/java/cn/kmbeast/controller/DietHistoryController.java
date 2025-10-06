package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.DietHistoryQueryDto;
import cn.kmbeast.pojo.entity.DietHistory;
import cn.kmbeast.pojo.vo.DietHistoryVO;
import cn.kmbeast.pojo.vo.OptionsVO;
import cn.kmbeast.service.DietHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 食谱记录
 */
@RestController
@RequestMapping("/diet-history")
// 接口分组标签（文档中按标签归类接口，便于查找）
@Api(tags = "食谱记录管理接口", description = "处理食谱记录注册、查询、修改操作")
public class DietHistoryController {

    @Resource
    private DietHistoryService dietHistoryService;

    /**
     * 新增食谱记录
     *
     * @param dietHistoryList 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/batchSave")
    @ResponseBody
    @ApiOperation(value = "新增食谱记录")
    public Result<String> batchSave(@RequestBody List<DietHistory> dietHistoryList) {
        return dietHistoryService.batchSave(dietHistoryList);
    }
    /**
     * 修改食谱记录
     *
     * @param dietHistory 注册入参
     * @return Result<String> 响应结果
     */
    @PutMapping(value = "/update")
    @ResponseBody
    @ApiOperation(value = "修改食谱记录")
    public Result<String> updateEntity(@RequestBody DietHistory dietHistory) {
        return dietHistoryService.updateEntity(dietHistory);
    }
    /**
     * 删除食谱记录
     *
     * @param ids 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    @ApiOperation(value = "删除食谱记录列表")
    public Result<String> deleteByIds(@RequestBody List<Integer> ids) {
        return dietHistoryService.deleteByIds(ids);
    }
    /**
     * 列表查询食谱记录
     *
     * @param dietHistoryQueryDto 注册入参
     * @return Result<String> 响应结果
     */
    @Pager
    @PostMapping(value = "/queryList")
    @ResponseBody
    @ApiOperation(value = "列表查询食谱记录")
    public Result<List<DietHistoryVO>> queryList(@RequestBody DietHistoryQueryDto dietHistoryQueryDto) {
        return dietHistoryService.queryList(dietHistoryQueryDto);
    }
    /**
     * 列表查询饮食餐记录
     *
     *
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/fetchAllDiet")
    @ResponseBody
    @ApiOperation(value = "列表查询饮食餐记录")
    public Result<List<OptionsVO>> fetchAllDiet() {
        return dietHistoryService.fetchAllDiet();
    }

}

