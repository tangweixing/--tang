package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.vo.ChartVO;
import cn.kmbeast.pojo.vo.DashboardVO;

import java.util.List;

public interface DashboardService {
    Result<DashboardVO> staticCount();

    Result<List<ChartVO>> typeCountHeathNews();

    Result<List<ChartVO>> typeCountRecipe();
}
