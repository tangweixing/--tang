<!-- eslint-disable prettier/prettier -->
<template>
  <div class="news-detail">
    <!-- 主内容区和推荐区容器 -->
    <div class="content-container">
      <!-- 左侧详情内容 -->
      <div class="main-content">
        <h1>{{ newsDetail.title }}</h1>
        <p class="publish-time">发布时间：{{ newsDetail.createTime }}</p>
        <div class="flow-data">
          <span>看过：{{ flowData.peopleCount || 0 }}人</span>
          <span>点赞：{{ flowData.upvoteCount || 0 }}次</span>
          <span>收藏：{{ flowData.collectCount || 0 }}次</span>
        </div>

        <!-- 封面图 -->
        <img :src="newsDetail.cover" alt="资讯封面" class="news-cover" v-if="newsDetail.cover" />

        <!-- 资讯内容 -->
        <div class="news-content" v-html="newsDetail.content"></div>
        <FlowIndex contentModule="HEALTH_NEWS" :contentId="this.id" :key="id" />
        <!-- 评论点赞组件 -->
        <Evaluations :contentId="newsDetail.id" contentType="news" />
      </div>

      <!-- 右侧推荐区域 -->
      <div class="recommend-sidebar">
        <!-- 包裹返回按钮，用于控制对齐 -->
        <div class="back-btn-wrapper">
          <el-button @click="goBack" class="back-btn">返回</el-button>
        </div>
        <h2>推荐阅读</h2>
        <div class="recommend-list">
          <!-- 推荐资讯项 -->
          <div class="recommend-item" v-for="item in recommendList" :key="item.id" @click="goToNewsDetail(item.id)">
            <img :src="item.cover" alt="推荐资讯封面" class="recommend-cover" v-if="item.cover" />
            <div class="recommend-info">
              <p class="recommend-title">{{ item.title }}</p>
              <p class="recommend-time">浏览量：{{ item.viewCount }}</p>
            </div>
          </div>

          <!-- 没有推荐内容时显示 -->
          <div class="no-recommend" v-if="recommendList.length === 0">
            暂无推荐资讯
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Evaluations from "@/components/Evaluations.vue"; // 假设Evaluations.vue在components目录下
import FlowIndex from "@/components/FlowIndex.vue"; // 假设FlowIndex.vue在components目录下
export default {
  components: {
    // eslint-disable-next-line vue/no-unused-components
    Evaluations,
    FlowIndex
  },
  data() {
    return {
      newsDetail: { id: null },
      recommendList: [], // 推荐资讯列表
      id: null,
      flowData: {}
    };
  },
  created() {
    // 获取路由参数中的id
    this.id = this.$route.query.id;
    if (this.id) {
      this.fetchNewsDetail(this.id);
      this.fetchRecommendNews(); // 获取推荐资讯
    } else {
      this.$message.error("未找到资讯ID");
      this.$router.back();
    }
    this.fetchFlowData(); // 组件挂载时请求数据
  },
  watch: {
    // 监听路由整体变化
    $route: {
      // eslint-disable-next-line no-unused-vars
      handler(to, from) {
        // 路由变化时，重新获取资讯详情
        this.id = to.query.id;
        this.fetchNewsDetail(this.id);
      },
      immediate: true // 初始化时就执行一次
    }
  },
  methods: {
    goBack() {
      const keyWord = this.$route.query.keyWord;
      console.log(keyWord);

      if (keyWord) {
        // 有关键词，跳转到搜索结果页并携带关键词
        sessionStorage.setItem("keyWord", keyWord);
        this.$router.push({
          path: "/search-detail"
        });
      } else {
        // 没有关键词，直接回退
        this.$router.back();
      }
    },
    async fetchFlowData() {
      try {
        // 假设接口需要资讯 ID，从路由参数或 props 获取（需替换为实际逻辑）

        const response = await this.$axios.post(
          "/flowIndex/queryDashboard",
          {
            contentId: this.id,
            contentModule: "HEALTH_NEWS"
          } // 传递资讯 ID，需与后端接口参数匹配
        );
        if (response.data.code === 200) {
          this.flowData = response.data.data;
        } else {
          console.error("查询流量数据失败：", response.data.message);
        }
      } catch (error) {
        console.error("请求异常：", error);
      }
    },
    async fetchNewsDetail(id) {
      try {
        const response = await this.$axios.get(`/health-news/${id}`);

        if (response.status === 200) {
          this.newsDetail = response.data.data || {};
        } else {
          this.$message.error("获取资讯详情失败");
        }
      } catch (error) {
        console.error("请求资讯详情出错：", error);
        this.$message.error("网络异常，获取资讯详情失败");
      }
    },

    // 获取推荐资讯
    async fetchRecommendNews() {
      try {
        // 假设推荐接口，实际请替换为你的后端接口
        const response = await this.$axios.get(
          "/flowIndex/recommendHealthNews"
        );
        console.log(response.status);

        if (response.status === 200) {
          this.recommendList = response.data.data || [];
        } else {
          this.$message.error("获取推荐资讯失败");
        }
      } catch (error) {
        console.error("请求推荐资讯出错：", error);
      }
    },

    // 跳转到推荐资讯详情
    goToNewsDetail(id) {
      this.$router.push({
        path: "/news-detail",
        query: { id: id }
      });
    }
  }
};
</script>

<style scoped>
.news-detail {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

/* 包裹返回按钮的容器，设置右对齐 */
.back-btn-wrapper {
  text-align: right;
  margin-bottom: 10px;
}

.back-btn {
  background-color: #f5f7fa;
  border: none;
}

/* 主内容区和推荐区布局 */
.content-container {
  display: flex;
  gap: 30px;
}

/* 左侧详情内容 */
.main-content {
  flex: 3;
  /* 占3/4宽度 */
}

.main-content h1 {
  font-size: 24px;
  margin-bottom: 15px;
  color: #333;
}

.publish-time {
  color: #999;
  font-size: 14px;
  margin-bottom: 20px;
}

.news-cover {
  max-width: 100%;
  height: auto;
  margin: 0 0 20px 0;
  border-radius: 4px;
}

.news-content {
  line-height: 1.8;
  color: #333;
  font-size: 16px;
}

.news-content img {
  max-width: 100%;
  height: auto;
  margin: 16px 0;
}

/* 右侧推荐区域 */
.recommend-sidebar {
  flex: 1;
  /* 占1/4宽度 */
  min-width: 280px;
}

.recommend-sidebar h2 {
  font-size: 18px;
  padding-bottom: 10px;
  border-bottom: 2px solid #f1f1f1;
  margin-bottom: 15px;
  color: #333;
}

.recommend-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.recommend-item {
  display: flex;
  gap: 10px;
  cursor: pointer;
  height: 100px;
  padding: 10px;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.recommend-item:hover {
  background-color: #f5f7fa;
}

.recommend-cover {
  width: 80px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
}

.recommend-info {
  flex: 1;
  overflow: hidden;
}

.recommend-title {
  font-size: 14px;
  color: #333;
  margin-bottom: 5px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  /* 标准属性 */
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.recommend-time {
  font-size: 12px;
  color: #999;
}

.no-recommend {
  text-align: center;
  padding: 20px;
  color: #999;
  background-color: #f5f7fa;
  border-radius: 4px;
}

/* 响应式调整 - 在小屏幕上堆叠显示 */
@media (max-width: 768px) {
  .content-container {
    flex-direction: column;
  }

  .recommend-sidebar {
    margin-top: 30px;
  }
}

.flow-data {
  color: #999;
  font-size: 14px;
  margin-bottom: 20px;
}

/* 给“看过”元素后面的 span（即“点赞”元素）添加右侧 margin */
.flow-data span:not(:last-child) {
  margin-right: 10px;
}
</style>
