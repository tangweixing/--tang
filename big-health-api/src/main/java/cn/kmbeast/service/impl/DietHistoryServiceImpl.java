package cn.kmbeast.service.impl;

import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.DietHistoryMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.DietHistoryQueryDto;
import cn.kmbeast.pojo.em.BMIEnum;
import cn.kmbeast.pojo.em.TypeEnum;
import cn.kmbeast.pojo.entity.DietHistory;
import cn.kmbeast.pojo.vo.DietHistoryVO;
import cn.kmbeast.pojo.vo.OptionsVO;
import cn.kmbeast.service.DietHistoryService;
import cn.kmbeast.utils.AssertUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DietHistoryServiceImpl extends ServiceImpl<DietHistoryMapper,DietHistory> implements DietHistoryService {


    /**
     * 新增食谱
     *
     * @param dietHistoryList 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    @Transactional
    public Result<String> batchSave(List<DietHistory> dietHistoryList) {
        //判断能否插入
        validParam(dietHistoryList);
        //设置创建的用户ID
        Integer userId = LocalThreadHolder.getUserId();
        dietHistoryList.forEach(
                dietHistory -> {
                dietHistory.setUserId(userId);
                }
        );
        //插入数据
        saveBatch(dietHistoryList);
        return ApiResult.success("健康数据记录成功");
    }
    private void validParam(List<DietHistory> dietHistoryList) {
        dietHistoryList.forEach(
                dietHistory -> {
                    AssertUtils.notNull(dietHistory.getDetail(),"记录餐不能为空");
                    AssertUtils.notNull(dietHistory.getEnergy(),"记录能量不能为空");
                }
        );
    }
    /**
     * 修改食谱
     *
     * @param dietHistory 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> updateEntity(DietHistory dietHistory) {
        //数据修改
        updateById(dietHistory);
        return ApiResult.success("食谱修改成功");
    }
    /**
     * 删除食谱
     *
     * @param ids 注册入参
     * @return Result<String> 响应结果
     */

    @Override
    @Transactional
    public Result<String> deleteByIds(List<Integer> ids) {
        //删除健康咨询
        removeBatchByIds(ids);
        return ApiResult.success("食谱删除成功");
    }

    @Override
    public Result<List<DietHistoryVO>> queryList(DietHistoryQueryDto dietHistoryQueryDto) {
        AssertUtils.notNull(dietHistoryQueryDto.getSize(),"页面大小不能为空");
        AssertUtils.notNull(dietHistoryQueryDto.getCurrent(),"当前页参数不能为空");
//        AssertUtils.equals(LocalThreadHolder.getUserId(),RoleEnum.ADMIN.getRole(),"无操作权限");
        List<DietHistoryVO> dietHistorys = this.baseMapper.list(dietHistoryQueryDto);
        //设置吃了什么餐 (名称)
        dietHistorys.forEach(
                dietHistoryVO -> {
                    List<OptionsVO> optionsVOS = TypeEnum.getOptionsByModule("RECIPE_DIET");
                    optionsVOS.forEach(
                            optionsVO -> {
                                if (dietHistoryVO.getDetail().equals(optionsVO.getValue())){
                                    dietHistoryVO.setDietName(optionsVO.getLabel());
                                }
                            }
                    );
                }
        );
        Integer count = this.baseMapper.queryListCount(dietHistoryQueryDto);
        return ApiResult.success(dietHistorys, count);
    }

    @Override
    public Result<List<OptionsVO>> fetchAllDiet() {
        List<OptionsVO> optionsVOS = TypeEnum.getOptionsByModule("RECIPE_DIET");
        return ApiResult.success(optionsVOS);
    }


}
