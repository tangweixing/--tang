package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HealthRecordQueryDto;
import cn.kmbeast.pojo.entity.HealthRecord;
import cn.kmbeast.pojo.vo.HealthRecordVO;
import cn.kmbeast.service.HealthRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 健康记录
 */
@RestController
@RequestMapping("/health-record")
// 接口分组标签（文档中按标签归类接口，便于查找）
@Api(tags = "健康记录管理接口", description = "处理健康记录注册、查询、修改操作")
public class HealthRecordController {

    @Resource
    private HealthRecordService healthRecordService;

    /**
     * 新增健康记录
     *
     * @param healthRecordList 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/batchSave")
    @ResponseBody
    @ApiOperation(value = "新增健康记录")
    public Result<String> batchSave(@RequestBody List<HealthRecord> healthRecordList) {
        return healthRecordService.batchSave(healthRecordList);
    }
    /**
     * 新增健康记录
     *
     * @param healthRecord 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/saveBMI")
    @ResponseBody
    @ApiOperation(value = "新增健康记录")
    public Result<String> saveBMI(@RequestBody HealthRecord healthRecord) {
        return healthRecordService.saveBMI(healthRecord);
    }
    /**
     * 修改健康记录
     *
     * @param healthRecord 注册入参
     * @return Result<String> 响应结果
     */
    @PutMapping(value = "/update")
    @ResponseBody
    @ApiOperation(value = "修改健康记录")
    public Result<String> updateEntity(@RequestBody HealthRecord healthRecord) {
        return healthRecordService.updateEntity(healthRecord);
    }
    /**
     * 删除健康记录
     *
     * @param ids 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    @ApiOperation(value = "删除健康记录列表")
    public Result<String> deleteByIds(@RequestBody List<Integer> ids) {
        return healthRecordService.deleteByIds(ids);
    }
    /**
     * 列表查询健康记录
     *
     * @param healthRecordQueryDto 注册入参
     * @return Result<String> 响应结果
     */
    @Pager
    @PostMapping(value = "/queryList")
    @ResponseBody
    @ApiOperation(value = "列表查询健康记录")
    public Result<List<HealthRecordVO>> queryList(@RequestBody HealthRecordQueryDto healthRecordQueryDto) {
        return healthRecordService.queryList(healthRecordQueryDto);
    }
    /**
     * 列表查询指定模型和时间健康记录
     *
     * @param healthRecordQueryDto 注册入参
     * @return Result<String> 响应结果
     */
    @Pager
    @PostMapping(value = "/queryRecordByHealthModelId")
    @ResponseBody
    @ApiOperation(value = "列表查询健康记录")
    public Result<List<HealthRecordVO>> queryRecordByHealthModelId(@RequestBody HealthRecordQueryDto healthRecordQueryDto) {
        return healthRecordService.queryRecordByHealthModelId(healthRecordQueryDto);
    }


}

