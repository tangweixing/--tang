package cn.kmbeast.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.kmbeast.mapper.HealthNewsMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HealthNewsQueryDto;
import cn.kmbeast.pojo.em.TypeEnum;
import cn.kmbeast.pojo.entity.HealthNews;
import cn.kmbeast.pojo.vo.HealthNewsListVO;
import cn.kmbeast.pojo.vo.HealthNewsVO;
import cn.kmbeast.pojo.vo.OptionsVO;
import cn.kmbeast.service.HealthNewsService;
import cn.kmbeast.utils.AssertUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HealthNewsServiceImpl extends ServiceImpl<HealthNewsMapper,HealthNews> implements HealthNewsService {
    /**
     * 新增健康资讯
     *
     * @param healthNews 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> insertEntity(HealthNews healthNews) {
        //判断能否插入
        validParam(healthNews);
        //插入数据
        save(healthNews);
        return ApiResult.success();
    }

    private void validParam(HealthNews healthNews) {
        AssertUtils.notNull(healthNews,"实体类不能为空");
        AssertUtils.notBlank(healthNews.getCover(),"封面不能为空");
        AssertUtils.notBlank(healthNews.getTitle(),"标题不能为空");
        AssertUtils.notBlank(healthNews.getContent(),"内容不能为空");
        AssertUtils.notBlank(healthNews.getSummary(),"摘要不能为空");
        AssertUtils.notNull(healthNews.getTypeId(),"请选定类型");
        AssertUtils.between(healthNews.getTitle().length(),0,50,"标题请控制在50字以内");
        AssertUtils.between(healthNews.getSummary().length(),0,200,"摘要请控制在200字以内");
    }
    /**
     * 修改健康资讯
     *
     * @param healthNews 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> updateEntity(HealthNews healthNews) {
        //判断能否修改
        validParam(healthNews);
        //数据修改
        updateById(healthNews);
        return ApiResult.success("健康资讯修改成功");
    }
    /**
     * 删除健康资讯
     *
     * @param ids 注册入参
     * @return Result<String> 响应结果
     */

    @Override
    @Transactional
    public Result<String> deleteByIds(List<Integer> ids) {
        //删除健康咨询
        removeBatchByIds(ids);
        return ApiResult.success("健康资讯删除成功");
    }

    /**
     * 列表查询健康资讯
     *
     * @param healthNewsQueryDto 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<List<HealthNewsListVO>> list(HealthNewsQueryDto healthNewsQueryDto) {


// 使用自定义XML查询或注解SQL
        List<HealthNewsListVO> result = baseMapper.selectHealthNewsListWithViewCount(healthNewsQueryDto);
             Integer  resultTotal=baseMapper.selectHealthNewsListWithViewCountCount(healthNewsQueryDto);
        return ApiResult.success(result,resultTotal);
    }
    /**
     * ID查询健康资讯
     *
     * @param id 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<HealthNewsVO> queryById(Integer id) {
        //1.根据id查询健康资讯
        HealthNews healthNews = lambdaQuery().eq(HealthNews::getId, id).one();
        if (ObjectUtil.isEmpty(healthNews)){
            return ApiResult.error("资讯不存在");
        }
        //2.数据封装
        HealthNewsVO healthNewsVO = BeanUtil.copyProperties(healthNews, HealthNewsVO.class);
        //TODO 设置类型名称
        return ApiResult.success(healthNewsVO);
    }
    @Override
    public Result<List<OptionsVO>> fetchHealthNewsTypes() {
        return ApiResult.success(TypeEnum.getOptionsByModule("NEWS"));
    }



}
