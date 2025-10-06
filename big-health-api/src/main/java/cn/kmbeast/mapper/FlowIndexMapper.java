package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.FlowIndexQueryDto;
import cn.kmbeast.pojo.entity.FlowIndex;
import cn.kmbeast.pojo.vo.DashboardVO;
import cn.kmbeast.pojo.vo.FlowIndexScoreVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FlowIndexMapper extends BaseMapper<FlowIndex> {
    List<FlowIndex> queryList(FlowIndexQueryDto flowIndexQueryDto);

    Integer queryListCount(FlowIndexQueryDto flowIndexQueryDto);

    DashboardVO queryDashboard(FlowIndexQueryDto flowIndexQueryDto);

    List<Integer> recommendHealthNews();

    List<FlowIndexScoreVO> listScore(String contentModule,@Param("recipeIds")List<Integer> recipeIds);

    List<Integer> recommendRecipe();
}
