package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HealthRecordQueryDto;
import cn.kmbeast.pojo.entity.HealthRecord;
import cn.kmbeast.pojo.vo.HealthRecordVO;

import java.util.List;

public interface HealthRecordService {
    Result<String> batchSave(List<HealthRecord> healthRecordList);

    Result<String> updateEntity(HealthRecord healthRecord);

    Result<String> deleteByIds(List<Integer> ids);

    Result<List<HealthRecordVO>> queryList(HealthRecordQueryDto healthRecordQueryDto);

    Result<List<HealthRecordVO>> queryRecordByHealthModelId(HealthRecordQueryDto healthRecordQueryDto);

    Result<String> saveBMI(HealthRecord healthRecord);
}
