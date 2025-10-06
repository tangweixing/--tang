package cn.kmbeast.mapper;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.vo.ChartVO;
import cn.kmbeast.pojo.vo.DashboardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DashboardMapper {

    DashboardVO staticCount();

    List<ChartVO> typeCountHeathNews();

    List<ChartVO> typeCountRecipe();
}
