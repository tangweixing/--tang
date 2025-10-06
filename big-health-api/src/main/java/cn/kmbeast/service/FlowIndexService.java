package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.FlowIndexQueryDto;
import cn.kmbeast.pojo.entity.FlowIndex;

import cn.kmbeast.pojo.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FlowIndexService extends IService<FlowIndex> {
    Result<String> insertEntity(FlowIndex flowIndex);

    Result<String> delete(Integer id);


    Result<List<FlowIndex>> queryList(FlowIndexQueryDto flowIndexQueryDto);


    Result<List<CollectionVO>> queryCollection();

    Result<DashboardVO> queryDashboard( FlowIndexQueryDto flowIndexQueryDto);

    Result<List<HealthNewsListVO>> recommendHealthNews();

    Result<List<RecipeVO>> recommendRecipe();
}
