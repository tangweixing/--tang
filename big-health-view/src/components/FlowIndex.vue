<template>
  <div class="flow-index-container">
    <div class="btn-group">
      <button class="like-btn" :class="{ liked: isLiked }" @click="handleLike">
        <i class="iconfont icon-like"></i>
        <span>点赞</span>
      </button>
      <button
        class="collect-btn"
        :class="{ collected: isCollected }"
        @click="handleCollect"
      >
        <i class="iconfont icon-collect"></i>
        <span>收藏</span>
      </button>
    </div>
  </div>
</template>

<script>
import { getUserId } from "@/utils/storage.js";

export default {
  name: "FlowIndex",
  props: {
    contentModule: {
      type: String,
      required: true
    },
    contentId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      collectedId: null,
      likedId: null,
      isLiked: false,
      isCollected: false,
      startTime: null,
      times: 0,
      FLOW_INDEX: {
        TYPE_1: 1, // 展现
        TYPE_2: 2, // 浏览
        TYPE_3: 3, // 点赞
        TYPE_4: 4, // 收藏
        TYPE_5: 5 // 停留
      }
    };
  },
  async created() {
    this.startTime = new Date();
    await this.handleShow();
    await this.handleBrowse();
    // 初始化时查询点赞和收藏状态
    await this.checkLikeStatus();
    await this.checkCollectStatus();
  },
  beforeDestroy() {
    const endTime = new Date();
    this.times = endTime - this.startTime;
    this.handleStay();
  },
  methods: {
    getCommonParams(type) {
      return {
        type,
        contentModule: this.contentModule,
        contentId: this.contentId,
        userId: getUserId()
      };
    },

    // 展现事件
    async handleShow() {
      try {
        console.log("xxx");
        
        await this.$axios.post(
          "/flowIndex/insert",
          this.getCommonParams(this.FLOW_INDEX.TYPE_1)
        );
      } catch (error) {
        console.error("展现事件上报失败：", error);
      }
    },

    // 浏览事件
    async handleBrowse() {  
      try {
        const { data } = await this.$axios.post(
          "/flowIndex/queryList",
          this.getCommonParams(this.FLOW_INDEX.TYPE_2)
        );
        if (!data.data || data.data.length === 0) {
          await this.$axios.post(
            "/flowIndex/insert",
            this.getCommonParams(this.FLOW_INDEX.TYPE_2)
          );
        }
      } catch (error) {
        console.error("浏览事件处理失败：", error);
      }
    },

    // 检查点赞状态
    async checkLikeStatus() {
      try {
        const { data } = await this.$axios.post(
          "/flowIndex/queryList",
          this.getCommonParams(this.FLOW_INDEX.TYPE_3)
        );
        console.log(data.data[0].id);
        this.likedId = data.data[0].id;
        // 如果有数据，说明用户已点赞
        this.isLiked = !!data && data.data.length > 0;
      } catch (error) {
        console.error("查询点赞状态失败：", error);
      }
    },

    // 检查收藏状态
    async checkCollectStatus() {
      try {
        const { data } = await this.$axios.post(
          "/flowIndex/queryList",
          this.getCommonParams(this.FLOW_INDEX.TYPE_4)
        );
        //用于删除收藏数据
        this.collectedId = data.data[0].id;
        // 如果有数据，说明用户已收藏
        this.isCollected = !!data && data.data.length > 0;
      } catch (error) {
        console.error("查询收藏状态失败：", error);
      }
    },

    // 点赞事件
    async handleLike() {
      try {
        if (this.isLiked) {
          // 已点赞，执行取消点赞
          await this.$axios.post(`/flowIndex/delete/${this.likedId}`);
          this.isLiked = false;
        } else {
          // 未点赞，执行点赞
          await this.$axios.post(
            "/flowIndex/insert",
            this.getCommonParams(this.FLOW_INDEX.TYPE_3)
          );
          this.isLiked = true;
        }
      } catch (error) {
        console.error("点赞操作失败：", error);
      }
    },

    // 收藏事件
    async handleCollect() {
      try {
        if (this.isCollected) {
          // 已收藏，执行取消收藏
          await this.$axios.post(`/flowIndex/delete/${this.collectedId}`);
          this.isCollected = false;
        } else {
          // 未收藏，执行收藏
          await this.$axios.post(
            "/flowIndex/insert",
            this.getCommonParams(this.FLOW_INDEX.TYPE_4)
          );
          this.isCollected = true;
        }
      } catch (error) {
        console.error("收藏操作失败：", error);
      }
    },

    // 停留事件
    async handleStay() {
      try {
        const params = {
          ...this.getCommonParams(this.FLOW_INDEX.TYPE_5),
          times: this.times
        };
        await this.$axios.post("/flowIndex/insert", params);
      } catch (error) {
        console.error("停留事件上报失败：", error);
      }
    }
  }
};
</script>

<style scoped>
.flow-index-container {
  display: flex;
  justify-content: center;
  padding: 20px;
}
.btn-group {
  display: flex;
  gap: 16px;
}
.like-btn,
.collect-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 16px;
  border: none;
  border-radius: 20px;
  background-color: #f5f5f5;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
}
.like-btn:hover,
.collect-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
.liked {
  background-color: #ff4d4f;
  color: #fff;
}
.collected {
  background-color: #1890ff;
  color: #fff;
}
.iconfont {
  font-size: 18px;
}
</style>
