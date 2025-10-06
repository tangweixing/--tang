package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.HealthRecordQueryDto;
import cn.kmbeast.pojo.entity.HealthRecord;
import cn.kmbeast.pojo.vo.HealthRecordVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HealthRecordMapper extends BaseMapper<HealthRecord> {
    List<HealthRecordVO> list(HealthRecordQueryDto healthRecordQueryDto);

    Integer queryListCount(HealthRecordQueryDto healthRecordQueryDto);
}
