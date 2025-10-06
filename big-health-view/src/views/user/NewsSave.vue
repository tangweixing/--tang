<!-- eslint-disable prettier/prettier -->
<template>
  <div class="news-save-page">
    <!-- 健康资讯收藏列表 -->
    <div class="collection-list health-news-collection">
      <h2>健康资讯收藏</h2>
      <div class="collection-empty" v-if="healthNewsCollection.length === 0">
        <p>暂无收藏的健康资讯</p>
      </div>
      <ul v-else>
        <li v-for="item in healthNewsCollection" :key="item.id" class="collection-item"
          @click="goToNewsDetail(item.contentId)">
          <div class="item-info">
            <img :src="item.cover || defaultCover" :alt="item.title" class="item-cover">
            <div class="item-details">
              <h3 class="item-title">{{ item.title }}</h3>
            </div>
          </div>
          <button class="cancel-btn" @click.stop="handleCancelCollection(item.id, 'HEALTH_NEWS')">
            取消收藏
          </button>
        </li>
      </ul>
    </div>

    <!-- 食谱收藏列表 -->
    <div class="collection-list recipe-collection">
      <h2>食谱收藏</h2>
      <div class="collection-empty" v-if="recipeCollection.length === 0">
        <p>暂无收藏的食谱</p>
      </div>
      <ul v-else>
        <li v-for="item in recipeCollection" :key="item.id" class="collection-item"
          @click="goToRecipeDetail(item.contentId)">
          <div class="item-info">
            <img :src="item.cover || defaultCover" :alt="item.title" class="item-cover">
            <div class="item-details">
              <h3 class="item-title">{{ item.title }}</h3>
            </div>
          </div>
          <button class="cancel-btn" @click.stop="handleCancelCollection(item.id, 'RECIPE')">
            取消收藏
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { getUserId } from "@/utils/storage.js";

export default {
  name: "NewsSave",
  data() {
    return {
      healthNewsCollection: [],
      recipeCollection: [],
      defaultCover: "https://picsum.photos/100/100",
      newsDetail: {},
      recipeDetail: {}
    };
  },
  mounted() {
    this.fetchCollections();
  },
  methods: {
    // 获取所有收藏数据并分类（移除可选链，改用兼容性判断）
    async fetchCollections() {
      try {
        const { data } = await this.$axios.get("/flowIndex/queryCollection");
        const collectionList = data.data || [];

        // 修复：用 item && item.module 代替 item?.module，兼容低版本ES
        this.healthNewsCollection = collectionList.filter(
          item => item && item.module === "HEALTH_NEWS" && item.contentId
        );
        this.recipeCollection = collectionList.filter(
          item => item && item.module === "RECIPE" && item.contentId
        );
      } catch (error) {
        console.error("获取收藏列表失败：", error);
        this.$message.error("加载收藏数据失败，请重试");
      }
    },

    // 健康资讯详情查询
    async fetchNewsDetail(contentId) {
      try {
        const response = await this.$axios.get(`/health-news/${contentId}`);
        if (response.status === 200) {
          this.newsDetail = response.data.data || {};
          return this.newsDetail;
        } else {
          this.$message.error("获取资讯详情失败");
          return null;
        }
      } catch (error) {
        console.error("请求资讯详情出错：", error);
        this.$message.error("网络异常，获取资讯详情失败");
        return null;
      }
    },

    // 食谱详情查询
    async fetchRecipeDetail(contentId) {
      try {
        const response = await this.$axios.get(`/recipe/${contentId}`);
        if (response.status === 200) {
          this.recipeDetail = response.data.data || {};
          return this.recipeDetail;
        } else {
          this.$message.error("获取食谱详情失败");
          return null;
        }
      } catch (error) {
        console.error("请求食谱详情出错：", error);
        this.$message.error("网络异常，获取食谱详情失败");
        return null;
      }
    },

    // 跳转到健康资讯详情页
    async goToNewsDetail(contentId) {
      if (!contentId) {
        this.$message.warning("资讯ID无效，无法查看详情");
        return;
      }
      const detail = await this.fetchNewsDetail(contentId);
      if (detail) {
        this.$router.push({
          path: "/news-detail",
          query: {
            id: contentId,
            type: "HEALTH_NEWS"
          }
        });
      }
    },

    // 跳转到食谱详情页
    async goToRecipeDetail(contentId) {
      if (!contentId) {
        this.$message.warning("食谱ID无效，无法查看详情");
        return;
      }
      const detail = await this.fetchRecipeDetail(contentId);
      if (detail) {
        this.$router.push({
          path: "/news-detail",
          query: {
            id: contentId,
            type: "RECIPE"
          }
        });
      }
    },

    // 取消收藏
    async handleCancelCollection(collectionId, module) {
      try {
        await this.$axios.post(`/flowIndex/delete/${collectionId}`);

        if (module === "HEALTH_NEWS") {
          this.healthNewsCollection = this.healthNewsCollection.filter(
            item => item.id !== collectionId
          );
        } else if (module === "RECIPE") {
          this.recipeCollection = this.recipeCollection.filter(
            item => item.id !== collectionId
          );
        }

        this.$message.success("取消收藏成功");
      } catch (error) {
        console.error("取消收藏失败：", error);
        this.$message.error("取消收藏失败，请重试");
      }
    }
  }
};
</script>

<style scoped>
.news-save-page {
  display: flex;
  min-height: 100vh;
  padding: 20px;
  gap: 20px;
  background-color: #f5f7fa;
}

.collection-list {
  flex: 1;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 20px;
}

.collection-list h2 {
  margin: 0 0 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
  font-size: 18px;
  color: #333;
}

.collection-empty {
  text-align: center;
  padding: 40px 0;
  color: #999;
  background-color: #fafafa;
  border-radius: 4px;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.collection-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
  transition: background-color 0.2s;
  cursor: pointer;
}

.collection-item:hover {
  background-color: #fafafa;
}

.item-info {
  display: flex;
  align-items: center;
  flex: 1;
  gap: 12px;
}

.item-cover {
  width: 80px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
  background-color: #f0f0f0;
}

.item-details {
  flex: 1;
  min-width: 0;
}

.item-title {
  margin: 0;
  font-size: 16px;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.cancel-btn {
  padding: 6px 12px;
  background-color: #ff4d4f;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
  font-size: 14px;
  z-index: 1;
}

.cancel-btn:hover {
  background-color: #f5222d;
}

@media (max-width: 768px) {
  .news-save-page {
    flex-direction: column;
  }

  .item-cover {
    width: 60px;
    height: 45px;
  }
}
</style>
