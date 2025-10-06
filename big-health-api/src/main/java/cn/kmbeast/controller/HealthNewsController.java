package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HealthNewsQueryDto;
import cn.kmbeast.pojo.entity.HealthNews;
import cn.kmbeast.pojo.vo.HealthNewsListVO;
import cn.kmbeast.pojo.vo.HealthNewsVO;
import cn.kmbeast.pojo.vo.OptionsVO;
import cn.kmbeast.service.HealthNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 健康资讯
 */
@RestController
@RequestMapping("/health-news")
// 接口分组标签（文档中按标签归类接口，便于查找）
@Api(tags = "健康资讯管理接口", description = "处理健康资讯注册、查询、修改操作")
public class HealthNewsController {

    @Resource
    private HealthNewsService healthNewsService;

    /**
     * 新增健康资讯
     *
     * @param healthNews 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/insert")
    @ResponseBody
    @ApiOperation(value = "新增健康资讯")
    public Result<String> insertEntity(@RequestBody HealthNews healthNews) {
        return healthNewsService.insertEntity(healthNews);
    }
    /**
     * 修改健康资讯
     *
     * @param healthNews 注册入参
     * @return Result<String> 响应结果
     */
    @PutMapping(value = "/update")
    @ResponseBody
    @ApiOperation(value = "修改健康资讯")
    public Result<String> updateEntity(@RequestBody HealthNews healthNews) {
        return healthNewsService.updateEntity(healthNews);
    }
    /**
     * 删除健康资讯
     *
     * @param ids 注册入参
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    @ApiOperation(value = "删除健康资讯列表")
    public Result<String> deleteByIds(@RequestBody List<Integer> ids) {
        return healthNewsService.deleteByIds(ids);
    }
    /**
     * 列表查询健康资讯
     *
     * @param healthNewsQueryDto 注册入参
     * @return Result<String> 响应结果
     */
    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    @ApiOperation(value = "列表查询健康资讯")
    public Result<List<HealthNewsListVO>> list(@RequestBody HealthNewsQueryDto healthNewsQueryDto) {
        return healthNewsService.list(healthNewsQueryDto);
    }

    /**
     * ID查询健康资讯
     *
     * @param id 注册入参
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    @ApiOperation(value = "ID查询健康资讯")
    public Result<HealthNewsVO> queryById(@PathVariable Integer id) {
        return healthNewsService.queryById(id);
    }
    /**
     * 查询健康资讯类型
     *
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/fetchHealthNewsTypes")
    @ResponseBody
    @ApiOperation(value = "查询健康资讯类型")
    public Result<List<OptionsVO>> fetchHealthNewsTypes() {
        return healthNewsService.fetchHealthNewsTypes();
    }


}

