<!-- eslint-disable prettier/prettier -->
<template>
  <div>
  <div class="info-recommend-title">
    <!-- 轮播图区域 -->
    <el-carousel :interval="3000" type="card" height="300px" style="margin: 10px 0;">
      <el-carousel-item v-for="item in recommendList" :key="item.id">
        <div class="news-item-carousel" @click="goToNewsDetail(item.id)">
          <el-card class="info-card">
            <!-- 灰色小眼睛浏览量组件 -->
            <div class="view-count-container">
              <i class="el-icon-view"></i>
              <span class="view-count">{{ item.viewCount || 0 }}</span>
            </div>
            <div class="img-container">
              <img :src="item.cover" class="info-cover" :alt="item.title" />
            </div>
            <div class="info-content">
              <h3 class="info-title">{{ item.title }}</h3>
              <p class="info-summary">{{ item.summary }}</p>
            </div>
          </el-card>
        </div>
      </el-carousel-item>
    </el-carousel>
    </div>
    <el-row style="margin: 10px;">
      <!-- 分类筛选 -->
      <el-radio-group v-model="activeTypeId" size="small" @change="handleFilter">
        <el-radio :label="null">全部</el-radio>
        <el-radio v-for="item in healthNewsTypes" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
      </el-radio-group>
    </el-row>
    <el-row style="margin: 10px; display: flex; flex-wrap: wrap; justify-content: space-between;">
      <!-- 资讯列表 - 保持原有点击逻辑与结构 -->
      <div v-for="item in tableData" :key="item.id" class="news-item" @click="goToNewsDetail(item.id)">
        <el-card class="info-card">
          <!-- 灰色小眼睛浏览量组件 -->
          <div class="view-count-container">
            <i class="el-icon-view"></i>
            <span class="view-count">{{ item.viewCount || 0 }}</span>
          </div>
          
          <div class="img-container">
            <img :src="item.cover" class="info-cover" :alt="item.title" />
          </div>
          <div class="info-content">
            <h3 class="info-title">{{ item.title }}</h3>
            <p class="info-summary">{{ item.summary }}</p>
          </div>
        </el-card>
      </div>
    </el-row>
    <!-- 分页（保持原有） -->
    <el-pagination style="margin: 20px 0;" @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="currentPage" :page-sizes="[3, 6]" :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="totalItems"></el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 1,
      pageSize: 3,
      totalItems: 0,
      tableData: [],
      activeTypeId: null,
      healthNewsTypes: [],
      recommendList: [] // 存储推荐资讯的轮播数据
    };
  },
  created() {
    this.fetchFreshData();
    this.fetchHealthNewsTypes();
    this.fetchRecommendNews(); // 调用推荐资讯接口
  },
  methods: {
    goToNewsDetail(id) {
      this.$router.push({ path: "/news-detail", query: { id: id } });
    },
    async fetchHealthNewsTypes() {
      try {
        const { data } = await this.$axios.get(
          "/health-news/fetchHealthNewsTypes"
        );
        this.healthNewsTypes = data.data;
      } catch (error) {
        console.error("查询类别异常:", error);
      }
    },
    async fetchFreshData() {
      try {
        const { data } = await this.$axios.post("/health-news/list", {
          current: this.currentPage,
          size: this.pageSize,
          typeId: this.activeTypeId
        });
        this.tableData = data.data;        
        this.totalItems = data.total;
      } catch (error) {
        console.error("查询资讯异常:", error);
        this.$message.error("查询失败，请稍后再试！");
      }
    },
    // 获取推荐资讯
    async fetchRecommendNews() {
      try {
        // 假设推荐接口，实际请替换为你的后端接口
        const response = await this.$axios.get("/flowIndex/recommendHealthNews");

        if (response.status === 200) {
          this.recommendList = response.data.data || [];
          console.log(this.recommendList);
          
        } else {
          this.$message.error("获取推荐资讯失败");
        }
      } catch (error) {
        console.error("请求推荐资讯出错：", error);
      }
    },
    handleFilter() {
      this.currentPage = 1;
      this.fetchFreshData();
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.fetchFreshData();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchFreshData();
    }
  }
};
</script>

<style scoped lang="scss">
// 1. 标题样式（保持原有）
.info-recommend-title {
  height: 360px;
  padding: 0 20px;
  background-color: #f5f7fa;
  margin-bottom: 20px;
  
}

// 2. 外层容器（保持原有）
.news-item {
  width: calc(33.333% - 20px);
  margin-bottom: 20px;
  cursor: pointer;
  /* 过渡：所有样式变化在 0.3 秒内完成，缓动效果为 ease */
  transition: all 0.3s ease;
  /* 初始状态：无阴影，位置不变 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  ::v-deep .el-card {
    // 基础样式保留，去除所有过渡和悬浮相关样式
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-sizing: border-box;
    pointer-events: none;
    border: 1px solid #e8e8e8;
    overflow: hidden;
    position: relative;

    // 4. 灰色小眼睛组件（保持基础样式）
    .view-count-container {
      position: absolute;
      top: 15px;
      right: 15px;
      background-color: rgba(255, 255, 255, 0.9);
      padding: 6px 12px;
      border-radius: 20px;
      font-size: 16px;
      color: #666;
      display: flex;
      align-items: center;
      z-index: 999;
      pointer-events: none;

      .el-icon-eye {
        margin-right: 6px;
        color: #999;
        font-size: 20px;
      }

      .view-count {
        font-weight: bold;
      }
    }

    // 5. 图片容器（保持原有）
    .img-container {
      width: 100%;
      height: 200px;
      display: flex;
      justify-content: center;
      align-items: center;
      overflow: hidden;
      margin-bottom: 10px;
      pointer-events: none;
    }

    // 6. 图片样式（去除放大效果）
    .info-cover {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    // 7. 内容区域（保持原有）
    .info-content {
      width: 100%;
      text-align: center;
      padding: 0 10px 15px;
      pointer-events: none;
    }

    // 8. 标题样式（去除变色效果）
    .info-title {
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 5px;
    }

    // 9. 摘要样式（保持原有）
    .info-summary {
      font-size: 14px;
      color: #666;
      line-height: 1.5;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
}
.news-item:hover {
  /* 悬浮时：上移 5px，阴影增强 */
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}
.news-item-carousel{
  width: 600px;
  height: 300px;
  margin-bottom: 20px;
  cursor: pointer;
  /* 过渡：所有样式变化在 0.3 秒内完成，缓动效果为 ease */
  transition: all 0.3s ease;
  /* 初始状态：无阴影，位置不变 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  ::v-deep .el-card {
    // 基础样式保留，去除所有过渡和悬浮相关样式
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-sizing: border-box;
    pointer-events: none;
    border: 1px solid #e8e8e8;
    overflow: hidden;
    position: relative;

    // 4. 灰色小眼睛组件（保持基础样式）
    .view-count-container {
      position: absolute;
      top: 15px;
      right: 15px;
      background-color: rgba(255, 255, 255, 0.9);
      padding: 6px 12px;
      border-radius: 20px;
      font-size: 16px;
      color: #666;
      display: flex;
      align-items: center;
      z-index: 999;
      pointer-events: none;

      .el-icon-eye {
        margin-right: 6px;
        color: #999;
        font-size: 20px;
      }

      .view-count {
        font-weight: bold;
      }
    }

    // 5. 图片容器（保持原有）
    .img-container {
      width: 100%;
      height: 200px;
      display: flex;
      justify-content: center;
      align-items: center;
      overflow: hidden;
      margin-bottom: 10px;
      pointer-events: none;
    }

    // 6. 图片样式（去除放大效果）
    .info-cover {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    // 7. 内容区域（保持原有）
    .info-content {
      width: 100%;
      text-align: center;
      padding: 0 10px 15px;
      pointer-events: none;
    }

    // 8. 标题样式（去除变色效果）
    .info-title {
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 5px;
    }

    // 9. 摘要样式（保持原有）
    .info-summary {
      font-size: 14px;
      color: #666;
      line-height: 1.5;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
}
.news-item-carousel:hover {
  /* 悬浮时：上移 5px，阴影增强 */
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}
</style>