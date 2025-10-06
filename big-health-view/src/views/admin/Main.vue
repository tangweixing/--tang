<!-- eslint-disable prettier/prettier -->
<template>
  <div class="dashboard">
    <h3 class="dashboard-title">仪表盘</h3>
    <div class="dashboard-content">
      <div class="left-panel">
        <!-- 静态数据块：循环渲染后端返回的统计数据 -->
        <div class="static-data">
          <div class="data-item" v-for="(item, index) in dashboardData" :key="index">
            <div class="data-count">{{ item.count }}</div>
            <div class="data-label">{{ item.label }}</div>
          </div>
        </div>
        <div class="line-chart">
          <p class="chart-label">折线图</p>
          <div class="chart-placeholder">
            <LineChart tag="健康资讯新建数据统计" height="400px" :values="chartValues" :date="chartData"
              @on-selected="handleSelected" width="100%" />
          </div>
        </div>
      </div>
      <div class="right-panel">
        <div class="pie-chart">
          <p class="chart-label">饼状图</p>
          <ul class="chart-desc">
            <li>1. 资讯类别下面的统计</li>
            <div class="chart-placeholder">
              <PieChart class="PChart" :types="newsTypes" :values="newsValues" tag="资讯类别统计" />
            </div>
            <li>2. 食谱类型下面的统计</li>
            <div class="chart-placeholder">
              <!-- 食谱饼图：传递 types 和 values -->
              <PieChart class="PChart" :types="recipeTypes" :values="recipeValues" tag="食谱类别统计"  />
            </div>
          </ul>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LineChart from "@/components/LineChart.vue";
import PieChart from "@/components/PieChart.vue";
export default {
  name: "Main",
  components: {
    LineChart,
    PieChart
  },
  data() {
    return {
      // 资讯饼图数据
      newsTypes: [],
      newsValues: [],
      // 食谱饼图数据
      recipeTypes: [],
      recipeValues: [],
      searchTime: null,
      chartValues: [], // 用于存储x轴数据（日期）
      chartData: [], // 用于存储y轴数据（数量）
      dashboardData: [] // 存储后端返回的统计数据
    };
  },
  mounted() {
    this.fetchDashboardData(); // 组件挂载时请求静态数据
    this.fetchLineChartData(); // 组件挂载时请求折线图数据
    this.fetchNewsPieData(); // 新增：请求资讯饼图数据
    this.fetchRecipePieData(); // 新增：请求食谱饼图数据
  },
  methods: {
    // 新增：请求“资讯类别”饼图数据
    fetchNewsPieData() {
      this.$axios
        .get("/dashboard/health-news")
        .then(res => {
          const data = res.data.data || {};
          console.log(data);

          // 提取name（过滤异常数据）
          this.newsTypes = data
            .filter(item => item.name)
            .map(item => item.name);

          // 提取count（过滤异常数据）
          this.newsValues = data
            .filter(item => item.count !== undefined && item.count !== null)
            .map(item => item.count);
        })
        .catch(err => {
          console.error("获取资讯饼图数据失败：", err);
        });
    },

    // 新增：请求“食谱类别”饼图数据
    fetchRecipePieData() {
      this.$axios
        .get("/dashboard/recipe")
        .then(res => {
          const data = res.data.data || {};
          console.log(data);
          // 提取name（过滤异常数据）
          this.recipeTypes = data
            .filter(item => item.name)
            .map(item => item.name);

          // 提取count（过滤异常数据）
          this.recipeValues = data
            .filter(item => item.count !== undefined && item.count !== null)
            .map(item => item.count);
        })
        .catch(err => {
          console.error("获取食谱饼图数据失败：", err);
        });
    },
    // 计算日期范围
    calculateDateRange(days) {
      const end = new Date();
      const start = new Date();
      start.setDate(start.getDate() - days);
      return {
        startTime: this.formatDateTime(start),
        endTime: this.formatDateTime(end)
      };
    },

    // 格式化日期时间为yyyy-MM-dd HH:mm:ss格式
    formatDateTime(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      const hours = String(date.getHours()).padStart(2, "0");
      const minutes = String(date.getMinutes()).padStart(2, "0");
      const seconds = String(date.getSeconds()).padStart(2, "0");
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },

    // 处理子组件选择事件
    handleSelected(valueFromChild) {
      this.searchTime = valueFromChild;
      // 当选择时间范围变化时，重新获取折线图数据
      this.fetchLineChartData();
    },

    // 获取仪表盘静态数据
    fetchDashboardData() {
      this.$axios
        .get("/dashboard")
        .then(response => {
          const {
            userCount,
            modelCount,
            healthNewsCount,
            recipeCount
          } = response.data.data;
          this.dashboardData = [
            { count: userCount, label: "存量用户" },
            { count: modelCount, label: "收录模型" },
            { count: healthNewsCount, label: "收录资讯" },
            { count: recipeCount, label: "收录食谱" }
          ];
        })
        .catch(error => {
          console.error("获取仪表盘数据失败：", error);
        });
    },

    // 获取折线图数据并赋值给chartValues和chartData
    fetchLineChartData() {
      // 确定查询的日期范围，默认为最近7天
      let dateRange;

      dateRange = this.calculateDateRange(this.searchTime);

      // 调用后端接口获取每日数据
      this.$axios
        .post("/health-model/queryByDay", {
          startTime: dateRange.startTime,
          endTime: dateRange.endTime
        })
        .then(response => {
          // 假设后端返回格式为 {data: [{name: '2023-10-01', count: 10}, ...]}
          const result = response.data.data || [];
          console.log(result);

          // 分别提取日期和数量到chartValues和chartData
          this.chartValues = result.map(item => item.count);
          this.chartData = result.map(item => item.name);
          console.log(this.chartData);
        })
        .catch(error => {
          console.error("获取折线图数据失败：", error);
          // 错误处理：清空图表数据或显示错误信息
          this.chartValues = [];
          this.chartData = [];
        });
    }
  }
};
</script>

<style scoped>
/* 让最外层容器占满视口高度 */
.dashboard {
  padding: 10px;
  margin: 0;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.dashboard-title {
  text-align: center;
  margin-bottom: 10px;
}

/* 内容区域自适应剩余高度 */
.dashboard-content {
  display: flex;
  justify-content: space-between;
  flex-grow: 1;
}

.left-panel,
.right-panel {
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.left-panel {
  width: 60%;
  margin-right: 20px;
}

.static-data {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 200px;
  padding: 10px;
  margin-bottom: 10px;
  text-align: center;
  border: 1px solid #eee;
  /* 可选：添加边框便于区分区域 */
}

.data-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.data-count {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 5px;
}

.data-label {
  font-size: 14px;
  color: #666;
}

.line-chart {
  padding: 10px;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.chart-label {
  font-weight: bold;
  margin-bottom: 10px;
}

.chart-placeholder {
  width: 100%;
  height: 400px;
  flex-grow: 1;
  border: 1px solid #ccc;
  background-color: #f9f9f9;
}

.right-panel {
  width: 35%;
}

.pie-chart {
  padding: 10px;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.chart-desc {
  list-style: none;
  padding: 0;
  margin-bottom: 10px;
}

.chart-desc li {
  margin-bottom: 5px;
}
</style>
