package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.HealthModelQueryDto;
import cn.kmbeast.pojo.entity.HealthModel;
import cn.kmbeast.pojo.vo.ChartVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户持久化接口
 */
@Mapper
public interface HealthModelMapper extends BaseMapper<HealthModel> {
  List<HealthModel> queryList(HealthModelQueryDto healthModelQueryDto);
   Integer queryListCount(HealthModelQueryDto healthModelQueryDto);

    List<ChartVO> queryByDay(HealthModelQueryDto healthModelQueryDto);
}
