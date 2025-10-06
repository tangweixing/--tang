package cn.kmbeast.service.impl;

import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.HealthRecordMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HealthRecordQueryDto;
import cn.kmbeast.pojo.em.BMIEnum;
import cn.kmbeast.pojo.em.RoleEnum;
import cn.kmbeast.pojo.em.isGlobalEnum;
import cn.kmbeast.pojo.entity.HealthRecord;
import cn.kmbeast.pojo.vo.HealthRecordVO;
import cn.kmbeast.service.HealthRecordService;
import cn.kmbeast.utils.AssertUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper,HealthRecord> implements HealthRecordService {


    /**
     * 新增健康记录
     *
     * @param healthRecordList 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    @Transactional
    public Result<String> batchSave(List<HealthRecord> healthRecordList) {
        //判断能否插入
        validParam(healthRecordList);
        //设置创建的用户ID
        Integer userId = LocalThreadHolder.getUserId();
        healthRecordList.forEach(
                healthRecord -> {
                healthRecord.setUserId(userId);
                }
        );
        //插入数据
        saveBatch(healthRecordList);
        return ApiResult.success("健康数据记录成功");
    }
    private void validParam(List<HealthRecord> healthRecordList) {
        healthRecordList.forEach(
                healthRecord -> {
                    AssertUtils.notNull(healthRecord.getValue(),"记录值不能为空");
                    AssertUtils.notNull(healthRecord.getHealthModelId(),"存在异常模型，请重试");
                }
        );
    }
    /**
     * 修改健康记录
     *
     * @param healthRecord 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> updateEntity(HealthRecord healthRecord) {
        //数据修改
        updateById(healthRecord);
        return ApiResult.success("健康记录修改成功");
    }
    /**
     * 删除健康记录
     *
     * @param ids 注册入参
     * @return Result<String> 响应结果
     */

    @Override
    @Transactional
    public Result<String> deleteByIds(List<Integer> ids) {
        //删除健康咨询
        removeBatchByIds(ids);
        return ApiResult.success("健康记录删除成功");
    }

    @Override
    public Result<List<HealthRecordVO>> queryList(HealthRecordQueryDto healthRecordQueryDto) {
        AssertUtils.notNull(healthRecordQueryDto.getSize(),"页面大小不能为空");
        AssertUtils.notNull(healthRecordQueryDto.getCurrent(),"当前页参数不能为空");
//        AssertUtils.equals(LocalThreadHolder.getUserId(),RoleEnum.ADMIN.getRole(),"无操作权限");
        List<HealthRecordVO> healthRecords = this.baseMapper.list(healthRecordQueryDto);
        Integer count = this.baseMapper.queryListCount(healthRecordQueryDto);
        return ApiResult.success(healthRecords, count);
    }
/*
查询折线数据
* */
    @Override
    public Result<List<HealthRecordVO>> queryRecordByHealthModelId(HealthRecordQueryDto healthRecordQueryDto) {
        List<HealthRecordVO> list = this.baseMapper.list(healthRecordQueryDto);
        return ApiResult.success(list);
    }
/*
* 新增BMI数据
* */
    @Override
    public Result<String> saveBMI(HealthRecord healthRecord) {
        healthRecord.setHealthModelId(BMIEnum.BMI.getID());
          this.baseMapper.insert(healthRecord);
        return ApiResult.success("新增BMI数据成功");
    }

}
