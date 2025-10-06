package cn.kmbeast.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.FlowIndexMapper;
import cn.kmbeast.mapper.HealthNewsMapper;
import cn.kmbeast.mapper.RecipeMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.FlowIndexQueryDto;
import cn.kmbeast.pojo.em.RoleEnum;
import cn.kmbeast.pojo.em.TypeEnum;
import cn.kmbeast.pojo.entity.FlowIndex;
import cn.kmbeast.pojo.entity.HealthNews;
import cn.kmbeast.pojo.entity.Recipe;
import cn.kmbeast.pojo.vo.*;

import cn.kmbeast.service.FlowIndexService;
import cn.kmbeast.service.RecipeService;
import cn.kmbeast.utils.AssertUtils;
import cn.kmbeast.utils.ItemBasedCFUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlowIndexServiceImpl extends ServiceImpl<FlowIndexMapper,FlowIndex> implements FlowIndexService {

    @Autowired
    private HealthNewsMapper healthNewsMapper;

    @Autowired
    private RecipeMapper recipeMapper;
    /**
     * 新增食谱
     *
     * @param flowIndex 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> insertEntity(FlowIndex flowIndex) {
        //判断能否插入
        validParam(flowIndex);

        //设置创建的用户ID
        Integer userId = LocalThreadHolder.getUserId();
        flowIndex.setUserId(userId);
        //插入数据
        save(flowIndex);
        return ApiResult.success();
    }

    private void validParam(FlowIndex flowIndex) {
        AssertUtils.notNull(flowIndex,"实体类不能为空");
        AssertUtils.notNull(flowIndex.getType(),"流量类型不能为空");
        AssertUtils.notBlank(flowIndex.getContentModule(),"内容模块不能为空");
        AssertUtils.notNull(flowIndex.getContentId(),"内容ID不能为空");
    }

    /**
     * 删除食谱
     *
     * @param  id 注册入参
     * @return Result<String> 响应结果
     */

    @Override
    @Transactional
    public Result<String> delete(Integer id) {
        //删除健康咨询

        removeById(id);
        return ApiResult.success("食谱删除成功");
    }

    @Override
    public Result<List<FlowIndex>> queryList(FlowIndexQueryDto flowIndexQueryDto) {
        //1.查询出flowIndexs
        List<FlowIndex> flowIndexs = this.baseMapper.queryList(flowIndexQueryDto);

        //2.计算总查询数
        Integer count = this.baseMapper.queryListCount(flowIndexQueryDto);
        return ApiResult.success(flowIndexs, count);
    }

    @Override
    public Result<List<CollectionVO>> queryCollection() {
        List<CollectionVO> collectionVOList = new ArrayList<>();
        // 查询用户的收藏记录，type 为 4（收藏）
        FlowIndexQueryDto queryDto = new FlowIndexQueryDto();
        Integer userId=LocalThreadHolder.getUserId();
        queryDto.setUserId(userId);
        queryDto.setType(4);
        List<FlowIndex> flowIndexList = this.baseMapper.queryList(queryDto);
        for (FlowIndex flowIndex : flowIndexList) {
            CollectionVO collectionVO = new CollectionVO();
            collectionVO.setId(flowIndex.getId());
            collectionVO.setModule(flowIndex.getContentModule());
            collectionVO.setContentId(flowIndex.getContentId());
            if ("HEALTH_NEWS".equals(flowIndex.getContentModule())) {
                HealthNews healthNews = healthNewsMapper.selectById(flowIndex.getContentId());
                if (healthNews != null) {
                    collectionVO.setTitle(healthNews.getTitle());
                    collectionVO.setCover(healthNews.getCover());

                }
            } else if ("RECIPE".equals(flowIndex.getContentModule())) {
                Recipe recipe = recipeMapper.selectById(flowIndex.getContentId());
                if (recipe != null) {
                    collectionVO.setTitle(recipe.getName());
                    collectionVO.setCover(recipe.getCover());
                }
            }
            collectionVOList.add(collectionVO);
        }
        return ApiResult.success(collectionVOList);
    }
/*
查询流量指标
 */
    @Override
    public Result<DashboardVO> queryDashboard(FlowIndexQueryDto flowIndexQueryDto) {
        DashboardVO dashboardVO = this.baseMapper.queryDashboard(flowIndexQueryDto);
        return ApiResult.success(dashboardVO);
    }
/*
推荐展现量最多的3位健康资讯
 */
    @Override
    public Result<List<HealthNewsListVO>> recommendHealthNews() {
        //1.获得推荐资讯 默认为展现量最高的3位
       List<Integer> ids=this.baseMapper.recommendHealthNews();
        System.out.println("ids"+ids);
       //2.查询出资讯数据返回给前端
        List<HealthNewsListVO> healthNewsListVOS = healthNewsMapper.selectHealthNewsListWithViewCountByIds(ids);

        return ApiResult.success(healthNewsListVOS);
    }
/*
协同过滤算法推荐食谱
 */
    @Override
    public Result<List<RecipeVO>> recommendRecipe() {
    //1.查询所有通过审核的食谱id
       List<Integer> recipeIds=recipeMapper.listIds();
       if (BeanUtil.isEmpty(recipeIds)){
           return ApiResult.success(Collections.emptyList());
       }
        System.out.println("recipeIds"+recipeIds);
       //2.查询用户对应内容的评分矩阵
      List<FlowIndexScoreVO> flowIndexScoreVOS=this.baseMapper.listScore("RECIPE",recipeIds);
        System.out.println("flowIndexScoreVOS"+flowIndexScoreVOS);
      //2.1查询评分
        List<ItemBasedCFUtil.Score> scoreList = flowIndexScoreVOS.stream()
                .map(vo -> new ItemBasedCFUtil.Score(
                        vo.getContentId(), // 食谱ID
                        vo.getUserId(),    // 用户ID
                        vo.getScore()      // 评分
                ))
                .collect(Collectors.toList());
        //3.算法工具类查询出推荐食谱ids （推荐数为分最高的3位）
        List<Integer> recommendIDS = ItemBasedCFUtil.recommend(scoreList, LocalThreadHolder.getUserId(), 3);
        //4.查询出食谱列表返回给前端
        List<RecipeVO> recipevos=new ArrayList<>();
        if (!BeanUtil.isEmpty(recommendIDS)) {
            //查询协同过滤算法推荐食谱
            recipevos = recipeMapper.listByRecommendIDS(recommendIDS);
        }
        if (recipevos.isEmpty()){
            System.out.println("保底");
            //1.获得推荐资讯 默认为展现量最高的3位
            List<Integer> ids=this.baseMapper.recommendRecipe();
            //2.查询出资讯数据返回给前端
            recipevos = recipeMapper.selectRecipeListWithViewCountByIds(ids);
        }
        //4.1 数据封装
        recipevos.forEach(
                recipeVO -> {
                    if (recipeVO.getTypeId().equals(TypeEnum.RECIPE_1.getId())){
                        recipeVO.setTypeName(TypeEnum.RECIPE_1.getName());
                    }else if (recipeVO.getTypeId().equals(TypeEnum.RECIPE_2.getId())){
                        recipeVO.setTypeName(TypeEnum.RECIPE_2.getName());
                    }
                }
        );
        return ApiResult.success(recipevos);
    }
}
