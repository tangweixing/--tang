package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HealthNewsQueryDto;
import cn.kmbeast.pojo.entity.HealthNews;
import cn.kmbeast.pojo.vo.HealthNewsListVO;
import cn.kmbeast.pojo.vo.HealthNewsVO;
import cn.kmbeast.pojo.vo.OptionsVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface HealthNewsService extends IService<HealthNews> {
    Result<String> insertEntity(HealthNews healthNews);

    Result<String> updateEntity(HealthNews healthNews);

    Result<List<HealthNewsListVO>> list(HealthNewsQueryDto healthNewsQueryDto);

    Result<HealthNewsVO> queryById(Integer id);

    Result<List<OptionsVO>> fetchHealthNewsTypes();

    Result<String> deleteByIds(List<Integer> ids);
}
