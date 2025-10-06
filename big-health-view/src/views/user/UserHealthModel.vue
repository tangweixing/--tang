<!-- eslint-disable prettier/prettier -->
<template>
  <el-row style="background-color: #FFFFFF; padding: 10px 0; border-radius: 5px; position: relative;">

    <!-- 折线图区域：使用 LineChart 组件 -->
    <el-row style="margin-bottom: 20px;">
      <!-- 控制选择框的列，设置合适的宽度 -->
      <el-col :span="3">
        <el-select @change="fetchChartData" v-model="healthModelId" placeholder="请选择">
          <el-option v-for="item in listModelName" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-col>
      <!-- 控制图表的列，占据剩余宽度 -->
      <el-col :span="13">
        <!-- 保留原有的@time-change和@on-selected事件绑定 -->
        <line-chart @time-change="fetchChartData" @on-selected="handleSelected" :values="chartValues" :date="chartDate"
          tag="健康指标趋势" height="300px" />
      </el-col>
      <!-- 红色方框区域：BMI测算功能 -->
      <el-col :span="8" style="position: absolute; right: 20px; top: 20px; width: 320px;">
        <div
          style="border: 2px solid white; padding: 40px; border-radius: 6px; min-height: 350px; display: flex; flex-direction: column; justify-content: center; box-sizing: border-box;">
          <h3 style="text-align: center; margin-bottom: 30px;">BMI体质指数测算</h3>

          <el-form :model="bmiForm" label-width="80px" style="max-width: 320px; margin: 0 auto;">
            <el-form-item label="身高(cm)" prop="height">
              <el-input v-model.number="bmiForm.height" placeholder="请输入身高" type="number" min="50" max="250"
                style="width: 100%;"></el-input>
            </el-form-item>

            <el-form-item label="体重(kg)" prop="weight">
              <el-input v-model.number="bmiForm.weight" placeholder="请输入体重" type="number" min="10" max="300"
                style="width: 100%;"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="calculateBMI" style="width: 100%; margin-top: 10px;">
                计算BMI值
              </el-button>
            </el-form-item>

            <!-- 测算结果展示 -->
            <el-form-item v-if="showResult" style="margin-top: 10px;">
              <div class="result-container">
                <p class="result-item">BMI值: <span class="value">{{ bmiResult.value }}</span></p>
                <p class="result-item">体型判断: <span :class="resultClass">{{ bmiResult.assessment }}</span></p>
                <div class="reference" style="margin-top: 15px; font-size: 13px; color: #666; line-height: 1.5;">
                  <p>参考标准: </p>
                  <p>偏瘦(&lt;18.5) | 正常(18.5-23.9) | 超重(24-27.9) | 肥胖(&ge;28)</p>
                </div>
              </div>
            </el-form-item>
            <!-- 新增“记录BMI值”按钮 -->
            <el-form-item v-if="showResult">
              <el-button type="success" @click="saveBMI" style="width: 100%; margin-top: 10px;">
                记录BMI值
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
    <!-- 数据表格和 BMI 测算区域 -->
    <el-row style="margin: 10px; margin-top: 80px;">
      <!-- 原有数据表格 -->
      <el-col :span="20">
        <el-table :data="tableData" style="width: 100%" class="custom-table">
          <el-table-column prop="healthModelName" width="100" label="记录项"></el-table-column>
          <el-table-column prop="value" width="100" label="记录值" header-align="center"></el-table-column>
          <el-table-column prop="healthModelUnit" width="350" label="单位"></el-table-column>
          <el-table-column width="190" label="指标情况">
            <template slot-scope="scope">
              <div class="indicator-container">
                <span
                  :class="isIndicatorUpToStandard(scope.row.value, scope.row.normalValue) ? 'text-success dot' : 'text-danger dot'">
                </span>
                <span>{{ isIndicatorUpToStandard(scope.row.value, scope.row.normalValue) ? '正常' : '异常'
                }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="normalValue" width="300" label="阈值">
            <template slot-scope="scope">
              {{ formatThreshold(scope.row.normalValue) }}
            </template>
          </el-table-column>
          <el-table-column prop="createTime" width="200" label="创建时间" :formatter="formatDate"></el-table-column>
        </el-table>
      </el-col>


    </el-row>

    <!-- 分页 -->
    <el-pagination style="margin: 20px 0;" @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="currentPage" :page-sizes="[10]" :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="totalItems"></el-pagination>
  </el-row>
</template>

<script>
import { getUserId } from "../../utils/storage";
import LineChart from "@/components/LineChart.vue";

export default {
  components: {
    LineChart
  },
  data() {
    return {
      healthModelId: null,
      currentPage: 1,
      pageSize: 10,
      totalItems: 0,
      tableData: [],
      chartValues: [],
      chartDate: [],
      listModelName: [],
      receivedValue: 0,

      // BMI测算相关数据
      bmiForm: {
        height: null,
        weight: null
      },
      bmiResult: {
        value: null,
        assessment: ""
      },
      showResult: false,
      countGlobalModel: 0,
      countPersonalModel: 0
    };
  },
  created() {
    this.fetchFreshData();
    this.getListModelName();
  },
  computed: {
    // 根据BMI结果动态设置样式
    resultClass() {
      if (!this.bmiResult.value) return "";
      const bmi = parseFloat(this.bmiResult.value);
      if (bmi < 18.5) return "text-blue";
      if (bmi < 24) return "text-green";
      if (bmi < 28) return "text-orange";
      return "text-red";
    }
  },
  methods: {
    // 原有方法保持不变
    calculateDateRange(days) {
      const end = new Date();
      const start = new Date();
      start.setDate(start.getDate() - days);
      return {
        startTime: this.formatDateTime(start),
        endTime: this.formatDateTime(end)
      };
    },
    formatDateTime(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      const hours = String(date.getHours()).padStart(2, "0");
      const minutes = String(date.getMinutes()).padStart(2, "0");
      const seconds = String(date.getSeconds()).padStart(2, "0");
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    async fetchChartData() {
      if (this.healthModelId === null || this.receivedValue === 0) {
        return;
      }
      const { startTime, endTime } = this.calculateDateRange(
        this.receivedValue
      );
      try {
        const params = {
          current: this.currentPage,
          size: this.pageSize,
          userId: getUserId(),
          healthModelId: this.healthModelId,
          startTime,
          endTime
        };

        const response = await this.$axios.post(
          "/health-record/queryRecordByHealthModelId",
          params
        );

        const { data } = response;

        this.chartValues = [];
        this.chartDate = [];

        if (Array.isArray(data.data)) {
          const sortedData = [...data.data].sort((a, b) => {
            return new Date(a.createTime) - new Date(b.createTime);
          });

          sortedData.forEach(item => {
            this.chartValues.push(Number(item.value) || 0);
            this.chartDate.push(this.formatChartDate(item.createTime));
            // 统计isGlobal
            if (item.isGlobal) {
              this.countGlobalModel++;
            } else {
              this.countPersonalModel++;
            }
          });
        }
      } catch (error) {
        console.error("按日期范围查询健康记录异常:", error);
      }
    },
    formatChartDate(dateString) {
      const date = new Date(dateString);
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      const hours = String(date.getHours()).padStart(2, "0");
      const minutes = String(date.getMinutes()).padStart(2, "0");
      return `${month}-${day} ${hours}:${minutes}`;
    },
    handleSelected(valueFromChild) {
      this.receivedValue = valueFromChild;
    },
    async getListModelName() {
      const { data } = await this.$axios.get(
        "/health-model/fetchModelNameByUserId"
      );
      try {
        if (data.code === 200) {
          this.listModelName = data.data;
        }
      } catch (error) {
        console.error("查询健康模型名称信息异常:", error);
      }
    },
    isIndicatorUpToStandard(value, threshold) {
      if (!value || !threshold) return false;
      const [lower, upper] = threshold.split(",");
      return (
        parseFloat(value) >= parseFloat(lower) &&
        parseFloat(value) <= parseFloat(upper)
      );
    },
    formatThreshold(threshold) {
      if (!threshold) return "";
      const [lower, upper] = threshold.split(",");
      return `下限值为${lower},上限值为${upper}`;
    },
    formatDate(row) {
      const date = new Date(row.createTime);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      const hours = String(date.getHours()).padStart(2, "0");
      const minutes = String(date.getMinutes()).padStart(2, "0");
      const seconds = String(date.getSeconds()).padStart(2, "0");
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    async fetchFreshData() {
      try {
        const params = {
          current: this.currentPage,
          size: this.pageSize,
          userId: getUserId()
        };
        const response = await this.$axios.post(
          "/health-record/queryList",
          params
        );
        const { data } = response;
        this.tableData = data.data;
        this.totalItems = data.total;
      } catch (error) {
        console.error("查询健康记录信息异常:", error);
      }
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

    // BMI测算方法
    calculateBMI() {
      // 验证输入
      if (!this.bmiForm.height || !this.bmiForm.weight) {
        this.$message.warning("请输入身高和体重");
        return;
      }
      if (this.bmiForm.height <= 0 || this.bmiForm.weight <= 0) {
        this.$message.error("身高和体重必须为正数");
        return;
      }

      // 计算BMI (体重kg / 身高m的平方)
      const heightM = this.bmiForm.height / 100;
      const bmi = this.bmiForm.weight / (heightM * heightM);

      // 保留两位小数
      this.bmiResult.value = bmi.toFixed(2);

      // 判断体型
      if (bmi < 18.5) {
        this.bmiResult.assessment = "偏瘦";
      } else if (bmi < 24) {
        this.bmiResult.assessment = "正常";
      } else if (bmi < 28) {
        this.bmiResult.assessment = "超重";
      } else {
        this.bmiResult.assessment = "肥胖";
      }

      // 显示结果
      this.showResult = true;
    },

    // 记录BMI值方法
    saveBMI() {
      const params = {
        value: this.bmiResult.value,
        userId: getUserId()
      };
      this.$axios
        .post("/health-record/saveBMI", params)
        .then(response => {
          if (response.data.code === 200) {
            this.$message.success("BMI值记录成功");
            // 可根据需求添加记录成功后的操作，如刷新表格等
            this.fetchFreshData();
          } else {
            this.$message.error("BMI值记录失败");
          }
        })
        .catch(error => {
          console.error("记录BMI值异常:", error);
          this.$message.error("网络异常，BMI值记录失败");
        });
    }
  }
};
</script>
<style scoped lang="scss">
.custom-table {
  th.el-table__cell {
    text-align: center !important;
  }
}

.dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin-right: 5px;
}

.text-success.dot {
  background-color: green;
}

.text-danger.dot {
  background-color: red;
}

.indicator-container {
  display: flex;
  align-items: center;
}

/* BMI测算相关样式 */
.result-container {
  padding: 20px;
  border-radius: 4px;
  background-color: #f5f7fa;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.result-item {
  margin: 10px 0;
  font-size: 16px;
}

.value {
  color: #1890ff;
  font-weight: bold;
}

.text-blue {
  color: #1890ff;
  font-weight: bold;
}

.text-green {
  color: #52c41a;
  font-weight: bold;
}

.text-orange {
  color: #fa8c16;
  font-weight: bold;
}

.text-red {
  color: #ff4d4f;
  font-weight: bold;
}
</style>
