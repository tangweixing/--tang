package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.DietHistoryQueryDto;
import cn.kmbeast.pojo.entity.DietHistory;
import cn.kmbeast.pojo.vo.DietHistoryVO;
import cn.kmbeast.pojo.vo.OptionsVO;


import java.util.List;

public interface DietHistoryService {

    Result<String> batchSave(List<DietHistory> dietHistoryList);

    Result<String> updateEntity(DietHistory dietHistory);

    Result<String> deleteByIds(List<Integer> ids);

    Result<List<DietHistoryVO>> queryList(DietHistoryQueryDto dietHistoryQueryDto);

    Result<List<OptionsVO>> fetchAllDiet();
}
