<!-- eslint-disable prettier/prettier -->
<template>
  <el-row style="background-color: #FFFFFF;padding: 10px 0;border-radius: 5px;">
    <el-row style="padding: 10px 0 20px;border-bottom: 1px solid #f1f1f1;margin: 0 10px;">
      <el-row>
        <el-select size="small" style="width: 188px;margin-right: 10px;" v-model="queryParams.healthModelId"
          placeholder="选择记录项">
          <el-option v-for="option in modelOptions" :key="option.value" :label="option.label"
            :value="option.value"></el-option>
        </el-select>
        <el-button size="small" class="customer"
          style="margin-left: 20px;background-color: rgb(43, 121, 203);border: none;" type="primary"
          @click="handleFilter">立即查询</el-button>
        <el-button size="small" class="customer reset"
          style="background-color: #f1f1f1;border: none;color: #909399;border: 1px solid #f1f1f1;" type="info"
          @click="resetQueryCondition">条件重置</el-button>
        <el-button size="small" class="customer" :style="{
          marginLeft: '10px',
          backgroundColor: selectedRows.length ? '#a7535a' : '#F1F1F1',
          border: 'none',
          color: selectedRows.length ? '#FFFFFF' : '#909399'
        }" type="danger" @click="batchDelete()">批量删除</el-button>
      </el-row>
    </el-row>
    <el-row style="margin: 10px;">
      <el-table row-key="id" @selection-change="handleSelectionChange" :data="tableData" style="width: 100%"  class="custom-table">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="avatar" width="80" label="头像">
          <template slot-scope="scope">
            <el-avatar shape="square" :size="40" :src="scope.row.avatar" style="margin-top: 10px;"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column prop="userName" width="100" label="记录者"></el-table-column>
        <el-table-column prop="healthModelName" width="100" label="记录项"></el-table-column>
        <el-table-column prop="healthModelUnit" width="100" label="单位"></el-table-column>
        <el-table-column width="90" label="指标情况">
          <template slot-scope="scope">
            <div class="indicator-container">
              <span
                :class="isIndicatorUpToStandard(scope.row.value, scope.row.normalValue) ? 'text-success dot' : 'text-danger dot'">
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="value" width="200" label="记录值" header-align="center"></el-table-column>
        <el-table-column prop="normalValue" width="300" label="阈值">
          <template slot-scope="scope">
            {{ formatThreshold(scope.row.normalValue) }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" width="200" label="创建时间" :formatter="formatDate"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <span class="text-button" @click="handleDelete(scope.row)">删除</span>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin: 20px 0;" @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="currentPage" :page-sizes="[10]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="totalItems"></el-pagination>
    </el-row>
  </el-row>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      totalItems: 0,
      tableData: [],
      queryParams: { recordItem: "" },
      selectedRows: [],
      modelOptions: []
    };
  },
  created() {
    this.fetchFreshData();
    this.fetchModelOptions();
  },
  methods: {
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
    async fetchModelOptions() {
      try {
        const response = await this.$axios.get(
          "/health-model/fetchAllModelName"
        );
        if (response.data.code === 200) {
          this.modelOptions = response.data.data;
        } else {
          this.$message.error("获取模型下拉框数据失败");
        }
      } catch (error) {
        console.error("获取模型下拉框数据异常：", error);
        this.$message.error("获取模型下拉框数据异常，请稍后再试");
      }
    },
    // eslint-disable-next-line no-unused-vars
    formatDate(row, column) {
      const date = new Date(row.createTime);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      const hours = String(date.getHours()).padStart(2, "0");
      const minutes = String(date.getMinutes()).padStart(2, "0");
      const seconds = String(date.getSeconds()).padStart(2, "0");
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    handleSelectionChange(selection) {
      this.selectedRows = selection;
    },
    async batchDelete() {
      if (!this.selectedRows.length) {
        this.$message.warning("未选中任何数据");
        return;
      }
      const confirmed = await this.$confirm(
        "删除后不可恢复，是否继续？",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).catch(err => err);
      if (confirmed !== "confirm") return;
      try {
        const ids = this.selectedRows.map(entity => entity.id);
        const response = await this.$axios.post("/health-record/delete", ids);
        if (response.data.code === 200) {
          this.$message.success(response.data.msg);
          this.fetchFreshData();
          this.selectedRows = [];
        }
      } catch (e) {
        this.$message.error("删除失败，请稍后重试");
        console.error("健康记录删除异常：", e);
      }
    },
    resetQueryCondition() {
      this.queryParams = { recordItem: "" };
      this.currentPage = 1;
      this.fetchFreshData();
    },
    async fetchFreshData() {
      try {
        this.tableData = [];
        const params = {
          current: this.currentPage,
          size: this.pageSize,
          ...this.queryParams
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
        this.$message.error("查询失败，请稍后再试！");
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
    handleDelete(row) {
      this.selectedRows = [row];
      this.batchDelete();
    }
  }
};
</script>
<style scoped lang="scss">
.customer {
  margin: 0 8px;
}

.dialog-avatar {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
  border: 1px dashed #dcdfe6;
  border-radius: 4px;
}

.text-button {
  margin-right: 16px;
  color: #2f54eb;
  cursor: pointer;

  &:hover {
    color: #4096ff;
  }
}

.inline-input {
  display: inline-block;
  width: 40%;
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
  justify-content: center;
  align-items: center;
  height: 100%;
}

.healthModelValue {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.custom-table {
  th.el-table__cell {
    text-align: center !important;
  }
}
</style>
