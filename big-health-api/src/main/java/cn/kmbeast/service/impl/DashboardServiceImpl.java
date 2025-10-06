package cn.kmbeast.service.impl;

import cn.kmbeast.mapper.DashboardMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.em.TypeEnum;
import cn.kmbeast.pojo.vo.ChartVO;
import cn.kmbeast.pojo.vo.DashboardVO;
import cn.kmbeast.service.DashboardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Resource
    private DashboardMapper dashboardMapper;
    @Override
    /*
    获取静态数据
     */
    public Result<DashboardVO> staticCount() {
        return ApiResult.success(dashboardMapper.staticCount());
    }

    @Override
    public Result<List<ChartVO>> typeCountHeathNews() {
        //设置具体的名称
        List<ChartVO> chartVOS = dashboardMapper.typeCountHeathNews();
       chartVOS.forEach(
               chartVO -> {
                   if (chartVO.getName().equals(TypeEnum.NEWS_1.getId().toString())){
                       chartVO.setName(TypeEnum.NEWS_1.getName());
                   } else if (chartVO.getName().equals(TypeEnum.NEWS_2.getId().toString())) {
                       chartVO.setName(TypeEnum.NEWS_2.getName());
                   }
               }
       );
        return ApiResult.success(chartVOS);
    }

    @Override
    public Result<List<ChartVO>> typeCountRecipe() {

        //设置具体的名称
        List<ChartVO> chartVOS = dashboardMapper.typeCountRecipe();
        chartVOS.forEach(
                chartVO -> {
                    if (chartVO.getName().equals(TypeEnum.RECIPE_1.getId().toString())){
                        chartVO.setName(TypeEnum.RECIPE_1.getName());
                    } else if (chartVO.getName().equals(TypeEnum.RECIPE_2.getId().toString())) {
                        chartVO.setName(TypeEnum.RECIPE_2.getName());
                    }
                }
        );
        return ApiResult.success(chartVOS);
    }
}
