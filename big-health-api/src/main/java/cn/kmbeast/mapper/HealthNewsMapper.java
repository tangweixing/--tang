package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.HealthNewsQueryDto;
import cn.kmbeast.pojo.dto.query.extend.UserQueryDto;
import cn.kmbeast.pojo.entity.HealthNews;
import cn.kmbeast.pojo.entity.User;
import cn.kmbeast.pojo.vo.HealthNewsListVO;
import cn.kmbeast.pojo.vo.HealthNewsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户持久化接口
 */
@Mapper
public interface HealthNewsMapper extends BaseMapper<HealthNews> {

    List<HealthNewsListVO> selectHealthNewsListWithViewCount(HealthNewsQueryDto healthNewsQueryDto);

    Integer selectHealthNewsListWithViewCountCount(HealthNewsQueryDto healthNewsQueryDto);

    List<HealthNewsListVO> selectHealthNewsListWithViewCountByIds(List<Integer> ids);
}
