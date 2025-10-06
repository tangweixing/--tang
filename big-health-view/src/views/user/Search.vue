<!-- eslint-disable prettier/prettier -->
<template>
  <div>
    <!-- 搜索结果标题 -->
    <div class="search-title">
      搜索结果：<span style="color:#409EFF">{{ keyWord }}</span>
    </div>

    <!-- 搜索结果列表 -->
    <el-row style="margin: 10px; display: flex; flex-wrap: wrap; justify-content: space-between;">
      <div v-for="item in tableData" :key="item.id + '-' + item.type" class="result-item" @click="goToDetail(item)">
        <el-card class="result-card">
          <!-- 浏览量 -->
          <div class="view-count-container">
            <i class="el-icon-view"></i>
            <span class="view-count">{{ item.viewCount || 0 }}</span>
          </div>

          <!-- 图片 -->
          <div class="img-container">
            <img :src="item.cover" class="result-cover" :alt="item.title || item.name" />
          </div>

          <!-- 内容 -->
          <div class="result-content">
            <h3 class="result-title">{{ item.title || item.name }}</h3>

            <!-- 资讯显示摘要，食谱显示类型和时间 -->
            <div v-if="item.type === 'news'" class="result-desc">
              {{ item.summary }}
            </div>
            <div v-else class="recipe-meta">
              <span class="recipe-type">{{ item.typeName }}</span>
              <span class="recipe-time">{{ formatTime(item.createTime) }}</span>
            </div>
          </div>
        </el-card>
      </div>
    </el-row>

    <!-- 没有结果时显示 -->
    <div v-if="tableData.length === 0" class="no-result">
      暂无相关数据
    </div>

    <!-- 分页 -->
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
      tableData: [], // 合并后的结果
      keyWord: ""
    };
  },
  // watch: {
  //   keyword(newVal) {
  //     if (newVal) {
  //       // 若关键字不为空，才触发搜索
  //       this.fetchSearchResult();
  //     }
  //   }
  // },
  created() {
    // 从 sessionStorage 获取初始的 keyword
    this.keyWord = sessionStorage.getItem("keyWord");
    this.fetchSearchResult();
    // 监听来自 Header 组件的更新 keyword 事件
    this.$bus.$on("updateKeyword", newKeyword => {
      this.keyWord = newKeyword;
      this.fetchSearchResult();
    });
  },
  methods: {
    // 跳转到详情页
    goToDetail(item) {
      if (item.type === "news") {
        this.$router.push({
          path: "/news-detail",
          query: { id: item.id, keyWord: this.keyWord }
        });
      } else {
        this.$router.push({
          path: "/recipe-detail",
          query: { id: item.id, keyWord: this.keyWord }
        });
      }
    },

    // 同时请求健康资讯和食谱接口，合并结果
    async fetchSearchResult() {
      try {
        // 1. 健康资讯
        const newsRes = this.$axios.post("/health-news/list", {
          current: this.currentPage,
          size: this.pageSize,
          title: this.keyWord
        });

        // 2. 食谱
        const recipeRes = this.$axios.post("/recipe/queryList", {
          current: this.currentPage,
          size: this.pageSize,
          isAudit: true,
          name: this.keyWord
        });

        // 3. 并行请求
        const [newsData, recipeData] = await Promise.all([newsRes, recipeRes]);

        // 4. 给数据添加类型标识
        const newsList = (newsData.data.data || []).map(item => ({
          ...item,
          type: "news"
        }));
        const recipeList = (recipeData.data.data || []).map(item => ({
          ...item,
          type: "recipe"
        }));

        // 5. 合并数据
        this.tableData = [...newsList, ...recipeList];
        this.totalItems = this.tableData.length;
      } catch (error) {
        console.error("搜索异常:", error);
        this.$message.error("搜索失败，请稍后再试！");
      }
    },

    // 分页事件
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.fetchSearchResult();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchSearchResult();
    },

    // 格式化时间
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
/* 样式不变，省略 */
search-title {
  font-size: 18px;
  font-weight: bold;
  margin: 10px 0 20px;
  padding-left: 10px;
  border-left: 4px solid #409eff;
}

.result-item {
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
    border: 1px solid #e8e8e8;
    overflow: hidden;
    position: relative;

    .view-count-container {
      position: absolute;
      top: 15px;
      right: 15px;
      background-color: rgba(255, 255, 255, 0.9);
      padding: 4px 10px;
      border-radius: 20px;
      font-size: 14px;
      color: #666;
      display: flex;
      align-items: center;
      z-index: 999;

      .el-icon-view {
        margin-right: 4px;
        color: #999;
        font-size: 16px;
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
    }

    .result-cover {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.3s ease;
    }

    .result-content {
      width: 100%;
      text-align: center;
      padding: 0 10px 15px;
    }

    .result-title {
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 5px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .result-desc {
      font-size: 14px;
      color: #666;
      line-height: 1.5;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .recipe-meta {
      display: flex;
      justify-content: center;
      gap: 10px;
      flex-wrap: wrap;

      .recipe-type {
        padding: 2px 8px;
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

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
  }
}

.no-result {
  text-align: center;
  padding: 50px 0;
  color: #999;
  font-size: 16px;
}
</style>
