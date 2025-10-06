package cn.kmbeast.service.impl;

import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.HealthModelMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HealthModelQueryDto;
import cn.kmbeast.pojo.em.RoleEnum;
import cn.kmbeast.pojo.em.isGlobalEnum;
import cn.kmbeast.pojo.entity.HealthModel;
import cn.kmbeast.pojo.vo.ChartVO;
import cn.kmbeast.pojo.vo.OptionsVO;
import cn.kmbeast.service.HealthModelService;
import cn.kmbeast.utils.AssertUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class HealthModelServiceImpl extends ServiceImpl<HealthModelMapper,HealthModel> implements HealthModelService {
    /**
     * 新增健康模型
     *
     * @param healthModel 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> insertEntity(HealthModel healthModel) {
        //判断能否插入
        validParam(healthModel);
        //判断正常值范围是否合法
        validNormalValue(healthModel.getNormalValue());
        //设置创建的用户ID
        Integer userId = LocalThreadHolder.getUserId();
        healthModel.setUserId(userId);
        if (!userId.equals(RoleEnum.ADMIN.getRole()) ){
            healthModel.setIsGlobal(isGlobalEnum.PRIVATE.getStatus());
        }
        //插入数据
        save(healthModel);
        return ApiResult.success();
    }

    private void validNormalValue(String normalValue) {
        //做兼容,出现中文逗号，兼容成英文逗号，-> ,
        if (normalValue.contains("，"))
        {
            normalValue = normalValue.replace("，", ",");
        }
        //要求格式为xxx，xxx
        AssertUtils.isTrue(normalValue.contains(","),"模型阈值非法，请重新输入");
    }

    private void validParam(HealthModel healthModel) {
        AssertUtils.notNull(healthModel,"实体类不能为空");
        AssertUtils.between(healthModel.getName().length(),0,50,"名称在50字以内");
        AssertUtils.between(healthModel.getDetail().length(),0,200,"简介在200字以内");
        AssertUtils.notBlank(healthModel.getIconUrl(),"图标不能为空");
        AssertUtils.notBlank(healthModel.getUnit(),"单位不能为空");
        AssertUtils.notBlank(healthModel.getSymbol(),"符号不能为空");
        AssertUtils.notBlank(healthModel.getNormalValue(),"正常值范围不能为空");
        AssertUtils.notNull(healthModel.getIsGlobal(),"请选定是否为全局变量");
    }
    /**
     * 修改健康模型
     *
     * @param healthModel 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> updateEntity(HealthModel healthModel) {
        //判断能否修改
        validParam(healthModel);
        //数据修改
        updateById(healthModel);
        return ApiResult.success("健康模型修改成功");
    }
    /**
     * 删除健康模型
     *
     * @param ids 注册入参
     * @return Result<String> 响应结果
     */

    @Override
    @Transactional
    public Result<String> deleteByIds(List<Integer> ids) {
        //删除健康咨询
        removeBatchByIds(ids);
        return ApiResult.success("健康模型删除成功");
    }

    @Override
    public Result<List<HealthModel>> queryList(HealthModelQueryDto healthModelQueryDto) {
        List<HealthModel> healthModels = this.baseMapper.queryList(healthModelQueryDto);
        Integer count = this.baseMapper.queryListCount(healthModelQueryDto);
        return ApiResult.success(healthModels, count);
    }

    @Override
    public Result<List<OptionsVO>> fetchAllModelName() {
        //1.查询所有模型的名称和ID
        List<OptionsVO> optionsVOS=new ArrayList<>();
        LambdaQueryWrapper<HealthModel> queryWrapper = new LambdaQueryWrapper<HealthModel>()
                .select(HealthModel::getName,HealthModel::getId);
        for (HealthModel healthModel : baseMapper.selectList(queryWrapper)) {
            OptionsVO optionsVO = new OptionsVO(
                    healthModel.getId(),
                    healthModel.getName()
            );
            //2.添加到 List<OptionsVO>
            optionsVOS.add(optionsVO);
        }
        //3.返回成功值
        return ApiResult.success(optionsVOS);

    }

    @Override
    public Result<List<OptionsVO>> fetchModelNameByUserId() {
        //1.查询所有模型的名称和ID
        List<OptionsVO> optionsVOS=new ArrayList<>();
        //查询为全局变量||userId为当前用户id的HealthModel
// 查询条件：(isGlobal为true) 或者 (isGlobal为false且userId为当前用户)
        LambdaQueryWrapper<HealthModel> queryWrapper = new LambdaQueryWrapper<HealthModel>()
                .select(HealthModel::getName, HealthModel::getId) // 只查询name和id字段
                // 条件1：isGlobal为true（所有全局模型）
                .eq(HealthModel::getIsGlobal, true)
                // 拼接OR条件
                .or()
                // 条件2：isGlobal为false 且 userId为当前线程用户ID（当前用户的非全局模型）
                // 条件2：非全局模型且属于当前用户（直接链式写，无需嵌套and()）
                .eq(HealthModel::getIsGlobal, false)
                .eq(HealthModel::getUserId, LocalThreadHolder.getUserId());

        for (HealthModel healthModel : baseMapper.selectList(queryWrapper)) {
            OptionsVO optionsVO = new OptionsVO(
                    healthModel.getId(),
                    healthModel.getName()
            );
            //2.添加到 List<OptionsVO>
            optionsVOS.add(optionsVO);
        }
        //3.返回成功值
        return ApiResult.success(optionsVOS);
    }
/*
查询健康模型创建统计数据
 */
    @Override
    public Result<List<ChartVO>> queryByDay(HealthModelQueryDto healthModelQueryDto) {
        List<ChartVO> chartVOS = this.baseMapper.queryByDay(healthModelQueryDto);
        return  ApiResult.success(chartVOS) ;

    }

    /**
     * 列表查询健康模型
     *
     * @param healthModelQueryDto 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<List<HealthModel>> list(HealthModelQueryDto healthModelQueryDto) {
        //查询List<HealthModel>
        //1.分页
        Page<HealthModel> page = new Page<>(healthModelQueryDto.getCurrent(), healthModelQueryDto.getSize());
        //2.查询语句
        LambdaQueryWrapper<HealthModel> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(HealthModel::getCover,HealthModel::getTitle,HealthModel::getId);
        // 动态添加 userId 条件：仅当 userId 不为 null 时添加
        if (healthModelQueryDto.getUserId() != null) {
            queryWrapper.eq(HealthModel::getUserId, healthModelQueryDto.getUserId());
        }

        // 动态添加 name 条件：仅当 name 不为 null 且不为空字符串时添加
        if (healthModelQueryDto.getName() != null && !healthModelQueryDto.getName().trim().isEmpty()) {
            queryWrapper.like(HealthModel::getName, healthModelQueryDto.getName());
        }
        // 动态添加 createTime 条件：仅当 createTime 不为 null 且不为空字符串时添加
        if (healthModelQueryDto.getStartTime() != null && healthModelQueryDto.getEndTime() != null && healthModelQueryDto.getStartTime().isBefore(healthModelQueryDto.getEndTime())) {
            queryWrapper.between(HealthModel::getCreateTime, healthModelQueryDto.getStartTime(), healthModelQueryDto.getEndTime());
        }
        //3.查询结果
        IPage<HealthModel> resultPage = baseMapper.selectPage(page,queryWrapper);
        List<HealthModel> records = resultPage.getRecords();
        System.out.println(records+"1");
        //3.1查询总数量
        Long total = resultPage.getTotal();
        Integer count = Integer.valueOf(total.toString());
        return ApiResult.success(records,count);
    }

}
