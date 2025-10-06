package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.DietHistoryQueryDto;
import cn.kmbeast.pojo.entity.DietHistory;
import cn.kmbeast.pojo.vo.DietHistoryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DietHistoryMapper extends BaseMapper<DietHistory> {
    List<DietHistoryVO> list(DietHistoryQueryDto dietHistoryQueryDto);

    Integer queryListCount(DietHistoryQueryDto dietHistoryQueryDto);
}
