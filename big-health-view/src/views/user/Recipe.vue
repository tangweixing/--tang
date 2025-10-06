<!-- eslint-disable prettier/prettier -->
<template>
  <div>
    <div class="recipe-recommend-title">
      <!-- 食谱轮播图区域 -->
      <el-carousel :interval="3000" type="card" height="300px" style="margin: 10px 0;">
        <el-carousel-item v-for="item in recommendRecipeList" :key="item.id">
          <div class="recipe-item-carousel" @click="goToRecipeDetail(item.id)">
            <el-card class="recipe-card">
              <!-- 灰色小眼睛浏览量组件 -->
              <div class="view-count-container">
                <i class="el-icon-view"></i>
                <span class="view-count">{{ item.viewCount || 0 }}</span>
              </div>
              <div class="img-container">
                <img :src="item.cover" class="recipe-cover" :alt="item.name" />
              </div>
              <div class="recipe-content">
                <h3 class="recipe-name">{{ item.name }}</h3>
                <div class="recipe-meta">
                  <span class="recipe-type">{{ item.typeName }}</span>
                  <span class="recipe-time">{{ formatTime(item.createTime) }}</span>
                </div>
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
        <el-radio v-for="item in recipeTypes" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
      </el-radio-group>
    </el-row>
    <el-row style="margin: 10px; display: flex; flex-wrap: wrap; justify-content: space-between;">
      <!-- 食谱列表 - 保持原有点击逻辑与结构 -->
      <div v-for="item in tableData" :key="item.id" class="recipe-item" @click="goToRecipeDetail(item.id)">
        <el-card class="recipe-card">
          <!-- 灰色小眼睛浏览量组件 -->
          <div class="view-count-container">
            <i class="el-icon-view"></i>
            <span class="view-count">{{ item.viewCount || 0 }}</span>
          </div>
          <div class="img-container">
            <img :src="item.cover" class="recipe-cover" :alt="item.name" />
          </div>
          <div class="recipe-content">
            <h3 class="recipe-name">{{ item.name }}</h3>
            <div class="recipe-meta">
              <span class="recipe-type">{{ item.typeName }}</span>
              <span class="recipe-time">{{ formatTime(item.createTime) }}</span>
            </div>
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
      recipeTypes: [],
      recommendRecipeList: [] // 存储推荐食谱的轮播数据
    };
  },
  created() {
    this.fetchFreshData();
    this.fetchRecipeTypes();
    this.fetchRecommendRecipe(); // 调用推荐食谱接口
  },
  methods: {
    // 跳转到食谱详情页方法
    goToRecipeDetail(id) {
      this.$router.push({
        path: "/recipe-detail",
        query: { id: id }
      });
    },

    // 获取食谱类型列表
    async fetchRecipeTypes() {
      try {
        const response = await this.$axios.get("/recipe/fetchRecipeTypes");
        const { data } = response;
        this.recipeTypes = data.data;
      } catch (error) {
        console.error("查询食谱类别信息异常:", error);
      }
    },

    // 加载食谱列表
    async fetchFreshData() {
      try {
        this.tableData = [];
        const params = {
          current: this.currentPage,
          size: this.pageSize,
          typeId: this.activeTypeId,
          isAudit: true
        };

        const response = await this.$axios.post("/recipe/queryList", params);
        const { data } = response;
        this.tableData = data.data;
        this.totalItems = data.total;
      } catch (error) {
        console.error("查询食谱信息异常:", error);
        this.$message.error("查询失败，请稍后再试！");
      }
    },

    // 获取推荐食谱
    async fetchRecommendRecipe() {
      try {
        const response = await this.$axios.get("/flowIndex/recommendRecipe");

        if (response.status === 200) {
          this.recommendRecipeList = response.data.data || [];
          console.log(this.recommendRecipeList);
        } else {
          this.$message.error("获取推荐食谱失败");
        }
      } catch (error) {
        console.error("请求推荐食谱出错：", error);
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
    },
    // 格式化时间为年月日
    formatTime(time) {
      if (!time) return "";
      const date = new Date(time);
      return `${date.getFullYear()}-${(date.getMonth() + 1)
        .toString()
        .padStart(2, "0")}-${date
          .getDate()
          .toString()
          .padStart(2, "0")}`;
    }
  }
};
</script>

<style scoped lang="scss">
.recipe-recommend-title {
  height: 360px;
  padding: 0 20px;
  background-color: #f5f7fa;
  margin-bottom: 20px;
}

// 外层包裹容器 - 确保点击区域完整
.recipe-item {
  width: calc(33.333% - 20px);
  margin-bottom: 20px;
  cursor: pointer; // 显示手型光标，提示可点击
}

.recipe-card {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
  // 移除卡片默认的点击事件阻止
  pointer-events: none;
}

.recipe-item {
  width: calc(33.333% - 20px);
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  ::v-deep .el-card {
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

    .recipe-cover {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .recipe-content {
      width: 100%;
      text-align: center;
      padding: 0 10px 15px;
      pointer-events: none;
    }

    .recipe-name {
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 5px;
    }

    .recipe-meta {
      display: flex;
      justify-content: center;
      width: 100%;
      gap: 10px;
      flex-wrap: wrap;
    }

    .recipe-type {
      padding: 3px 8px;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 13px;
      color: #333;
    }

    .recipe-time {
      font-size: 12px;
      color: #999;
    }
  }
}
.recipe-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}
.recipe-item-carousel {
  width: 600px;
  height: 300px;
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  ::v-deep .el-card {
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

    .recipe-cover {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .recipe-content {
      width: 100%;
      text-align: center;
      padding: 0 10px 15px;
      pointer-events: none;
    }

    .recipe-name {
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 5px;
    }

    .recipe-meta {
      display: flex;
      justify-content: center;
      width: 100%;
      gap: 10px;
      flex-wrap: wrap;
    }

    .recipe-type {
      padding: 3px 8px;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 13px;
      color: #333;
    }

    .recipe-time {
      font-size: 12px;
      color: #999;
    }
  }
}
.recipe-item-carousel:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}
</style>