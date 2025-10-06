package cn.kmbeast.controller;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.vo.ChartVO;
import cn.kmbeast.pojo.vo.DashboardVO;
import cn.kmbeast.pojo.vo.HealthNewsVO;
import cn.kmbeast.service.DashboardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/*
仪表盘
* */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Resource
    private DashboardService dashboardService;
    /**
     * 仪表盘静态数据查询
     *
     * @return Result<String> 响应结果
     */
    @GetMapping
    @ResponseBody
    @ApiOperation(value = "仪表盘数据查询")
    public Result<DashboardVO> staticCount() {
        return dashboardService.staticCount();
    }
    /**
     * 健康资讯仪表盘分类别数据查询
     *
     * @return Result<String> 响应结果
     */
    @GetMapping(value = "/health-news")
    @ResponseBody
    @ApiOperation(value = "健康资讯仪表盘分类别数据查询")
    public Result<List<ChartVO>> typeCountHeathNews() {
        return dashboardService.typeCountHeathNews();
    }

    /**
     * 食谱仪表盘分类别数据查询
     *
     * @return Result<String> 响应结果
     */
    @GetMapping("/recipe")
    @ResponseBody
    @ApiOperation(value = "食谱仪表盘分类别数据查询")
    public Result<List<ChartVO>> typeCountRecipe() {
        return dashboardService.typeCountRecipe();
    }
}
