package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HealthModelQueryDto;
import cn.kmbeast.pojo.entity.HealthModel;
import cn.kmbeast.pojo.vo.ChartVO;
import cn.kmbeast.pojo.vo.OptionsVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface HealthModelService extends IService<HealthModel> {
    Result<String> insertEntity(HealthModel healthModel);

    Result<String> updateEntity(HealthModel healthModel);

    Result<List<HealthModel>> list(HealthModelQueryDto healthModelQueryDto);

    Result<String> deleteByIds(List<Integer> ids);

    Result<List<HealthModel>> queryList(HealthModelQueryDto healthModelQueryDto);

    Result<List<OptionsVO>> fetchAllModelName();

    Result<List<OptionsVO>> fetchModelNameByUserId();

    Result<List<ChartVO>> queryByDay(HealthModelQueryDto healthModelQueryDto);
}
