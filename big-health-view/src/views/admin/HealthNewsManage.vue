<!-- eslint-disable prettier/prettier -->
<template>
    <el-row style="background-color: #FFFFFF;padding: 10px 0;border-radius: 5px;">
        <el-row style="padding: 10px 0 20px;border-bottom: 1px solid #f1f1f1;margin: 0 10px;">
            <el-row>
                <span class="dialog-hover">健康资讯类型</span>
                <el-select v-model="healthNewsQueryDto.typeId" placeholder="请选择类别" size="small">
                    <el-option v-for="item in healthNewsTypes" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
                <span class="top-bar">健康资讯标题</span>
                <el-input size="small" style="width: 188px;" v-model="healthNewsQueryDto.title" placeholder="输入处"
                    clearable @clear="handleFilterClear">
                </el-input>
                <span class="top-bar">注册时间</span>
                <el-date-picker size="small" style="margin-left: 10px;width: 220px;" v-model="searchTime"
                    type="daterange" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间">
                </el-date-picker>
                <el-button size="small" class="customer"
                    style="margin-left: 20px;background-color: rgb(43, 121, 203);border: none;" type="primary"
                    @click="handleFilter">立即查询</el-button>
                <el-button size="small" class="customer reset"
                    style="background-color: #f1f1f1;border: none;color: #909399;border: 1px solid #f1f1f1;" type="info"
                    @click="resetQueryCondition">条件重置</el-button>
                <el-button size="small" style="background-color: rgb(43, 121, 203);border: none;" class="customer"
                    type="info" @click="add()">新增健康资讯</el-button>
                <el-button size="small" class="customer" :style="{
                    marginLeft: '10px',
                    backgroundColor: selectedRows.length ? '#a7535a' : '#F1F1F1',
                    border: 'none',
                    color: selectedRows.length ? '#FFFFFF' : '#909399'
                }" type="danger" @click="batchDelete()">批量删除</el-button>
            </el-row>
        </el-row>
        <el-row style="margin: 10px;">
            <el-table row-key="id" @selection-change="handleSelectionChange" :data="tableData" style="width: 100%">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="cover" width="368" label="封面">
                    <template slot-scope="scope">
                        <el-avatar shape="square" :size="50" :src="scope.row.cover" style="margin-top: 10px;"></el-avatar>
                    </template>
                </el-table-column>
                <el-table-column prop="title" width="1000" label="标题"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <span class="text-button" @click="handleEdit(scope.row)">编辑</span>
                        <span class="text-button" @click="handleDelete(scope.row)">删除</span>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination style="margin: 20px 0;" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-sizes="[5, 7]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="totalItems"></el-pagination>
        </el-row>
        <!-- 操作面板 -->
        <el-dialog :show-close="false" :visible.sync="dialoghealthNewsOperaion" width="24%">
            <div slot="title">
                <p class="dialog-title">
                    {{ !isOperation ? "新增健康资讯" : "编辑健康资讯信息" }}
                </p>
            </div>
            <div style="padding:0 20px;">
                <el-row>
                    <span class="dialog-hover">健康资讯封面</span>
                    <el-upload class="avatar-uploader" action="/api/personal-heath/v1.0/file/upload"
                        :show-file-list="false" :on-success="handleImageSuccess">
                        <img v-if="data.cover" :src="data.cover" class="dialog-avatar" />
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-row>
                <el-row>
                    <span class="dialog-hover">健康资讯类型</span>
                    <div>
                          <el-select v-model="data.typeId" placeholder="请选择类别" size="small">
                    <el-option v-for="item in healthNewsTypes" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
                    </div>
                    <div>
                        <span class="dialog-hover">标题</span>
                        <input class="dialog-input" v-model="data.title" placeholder="标题" />
                    </div>
                    <span class="dialog-hover">摘要</span>
                    <el-input type="text" placeholder="请输入摘要" v-model="data.summary" maxlength="200" show-word-limit />
                    <span class="dialog-hover">内容</span>
                    <Editor @on-listener="onListener" height="200px"   :visible="dialoghealthNewsOperaion" :receiveContent="data.content" />
                </el-row>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" v-if="!isOperation" style="background-color: rgb(43, 121, 203);border: none;"
                    class="customer" type="info" @click="addOperation">新增</el-button>
                <el-button size="small" v-else style="background-color: rgb(43, 121, 203);border: none;"
                    class="customer" type="info" @click="updateOperation">修改</el-button>
                <el-button class="customer" size="small" style="background-color: rgb(241, 241, 241);border: none;"
                    @click="dialoghealthNewsOperaion = false">取消</el-button>
            </span>
        </el-dialog>
    </el-row>
</template>

<script>
// 引入项目中的 Editor 组件
import Editor from "@/components/Editor.vue";
export default {
  components: { Editor },
  data() {
    return {
      data: { cover: "" }, // 初始化封面为空，避免undefined
      currentPage: 1,
      pageSize: 7,
      totalItems: 0,
      dialoghealthNewsOperaion: false, // 操作弹窗开关
      isOperation: false, // 标识：false=新增，true=编辑
      tableData: [],
      searchTime: [], // 时间范围搜索
      selectedRows: [], // 表格选中行（批量删除用）
      healthNewsQueryDto: {}, // 搜索条件DTO
      healthNewsTypes: [] // 健康资讯类型列表
    };
  },
  watch: {
    // 弹窗关闭时重置表单数据（原逻辑优化：避免isOperation重复切换）
    dialoghealthNewsOperaion(visible) {
      if (!visible) {
        this.data = { cover: "" }; // 重置表单
      }
    }
  },
  created() {
    this.fetchFreshData(); // 初始化加载列表
    this.fetchHealthNewsTypes(); // 初始化加载类型列表
  },
  methods: {
    // 富文本编辑器内容回调
    onListener(content) {
      this.data.content = content;
    },
    // 获取健康资讯类型列表
    async fetchHealthNewsTypes() {
      try {
        const response = await this.$axios.get(
          "/health-news/fetchHealthNewsTypes"
        );
        const { data } = response;
        this.healthNewsTypes = data.data;
      } catch (error) {
        console.error("查询健康资讯类别信息异常:", error);
      }
    },
    // 封面上传成功回调
    async handleImageSuccess(res) {
      if (res.code !== 200) {
        this.$message.error("健康资讯封面上传异常");
        return;
      }
      this.$message.success("健康资讯封面上传成功");
      this.data.cover = res.data;
    },
    // 表格选中行变化（批量删除用）
    handleSelectionChange(selection) {
      this.selectedRows = selection;
    },
    // 批量删除
    async batchDelete() {
      if (!this.selectedRows.length) {
        this.$message.warning("未选中任何数据");
        return;
      }
      const confirmed = await this.$swalConfirm({
        title: "删除健康资讯数据",
        text: "删除后不可恢复，是否继续？",
        icon: "warning"
      });
      if (confirmed) {
        try {
          // 提取选中行的id列表
          const ids = this.selectedRows.map(entity => entity.id);
          const response = await this.$axios.post("/health-news/delete", ids);
          if (response.data.code === 200) {
            this.$swal.fire({
              title: "删除提示",
              text: response.data.msg,
              icon: "success",
              showConfirmButton: false,
              timer: 2000
            });
            this.fetchFreshData(); // 重新加载列表
            this.selectedRows = []; // 清空选中
          }
        } catch (e) {
          this.$swal.fire({
            title: "错误提示",
            text: "删除失败，请稍后重试",
            icon: "error",
            showConfirmButton: false,
            timer: 2000
          });
          console.error("健康资讯信息删除异常：", e);
        }
      }
    },
    // 重置查询条件
    resetQueryCondition() {
      this.healthNewsQueryDto = {};
      this.searchTime = [];
      this.currentPage = 1; // 重置到第一页
      this.fetchFreshData();
    },
    // 编辑-提交修改
    async updateOperation() {
      try {
        const response = await this.$axios.put(
          "/health-news/update",
          this.data
        );
        this.$swal.fire({
          title: "修改提示",
          text: response.data.msg,
          icon: response.data.code === 200 ? "success" : "error",
          showConfirmButton: false,
          timer: 1000
        });
        if (response.data.code === 200) {
          this.dialoghealthNewsOperaion = false; // 关闭弹窗
          this.fetchFreshData(); // 重新加载列表
        }
      } catch (error) {
        console.error("提交修改时出错:", error);
        this.$message.error("提交失败，请稍后再试！");
      }
    },
    // 新增-提交表单
    async addOperation() {
      try {
        const response = await this.$axios.post(
          "/health-news/insert",
          this.data
        );
        this.$message[response.data.code === 200 ? "success" : "error"](
          response.data.msg
        );
        if (response.data.code === 200) {
          this.dialoghealthNewsOperaion = false; // 关闭弹窗
          this.fetchFreshData(); // 重新加载列表
        }
      } catch (error) {
        console.error("提交新增时出错:", error);
        this.$message.error("提交失败，请稍后再试！");
      }
    },
    // 加载健康资讯列表（核心查询方法）
    async fetchFreshData() {
      try {
        this.tableData = []; // 加载前清空列表，避免旧数据闪现
        let startTime = null;
        let endTime = null;

        // 处理时间范围（格式化为：YYYY-MM-DD HH:mm:ss）
        if (this.searchTime != null && this.searchTime.length === 2) {
          const [startDate, endDate] = this.searchTime;
          startTime = `${startDate.toISOString().split("T")[0]}T00:00:00`;
          endTime = `${endDate.toISOString().split("T")[0]}T23:59:59`;
        }

        // 构造查询参数
        const params = {
          current: this.currentPage,
          size: this.pageSize,
          startTime,
          endTime,
          ...this.healthNewsQueryDto
        };

        const response = await this.$axios.post("/health-news/list", params);
        const { data } = response;
        this.tableData = data.data; // 列表数据
        this.totalItems = data.total; // 总条数（分页用）
      } catch (error) {
        console.error("查询健康资讯信息异常:", error);
        this.$message.error("查询失败，请稍后再试！");
      }
    },
    // 打开新增弹窗
    add() {
      this.isOperation = false; // 标识为新增
      this.dialoghealthNewsOperaion = true;
      // 类型列表已在created时加载，无需重复请求
    },
    // 执行查询（筛选条件变更时）
    handleFilter() {
      this.currentPage = 1; // 筛选时重置到第一页
      this.fetchFreshData();
    },
    // 标题输入框清空回调
    handleFilterClear() {
      this.healthNewsQueryDto.title = ""; // 明确清空标题，避免filterText冗余
      this.handleFilter();
    },
    // 分页-每页条数变更
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1; // 条数变更时重置到第一页
      this.fetchFreshData();
    },
    // 分页-当前页变更
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchFreshData();
    },
    // 打开编辑弹窗
    handleEdit(row) {
      this.isOperation = true; // 标识为编辑
      this.data = { ...row }; // 深拷贝行数据，避免修改时联动表格
      this.dialoghealthNewsOperaion = true;
    },
    // 单条删除（复用批量删除逻辑）
    handleDelete(row) {
      this.selectedRows = [row]; // 选中当前行
      this.batchDelete();
    }
  }
};
</script>
<style scoped lang="scss">
.tag-tip {
  display: inline-block;
  padding: 5px 10px;
  border-radius: 5px;
  background-color: rgb(245, 245, 245);
  color: rgb(104, 118, 130);
}

.input-def {
  height: 40px;
  line-height: 40px;
  outline: none;
  border: none;
  font-size: 20px;
  color: rgb(102, 102, 102);
  font-weight: 900;
  width: 100%;
}

.dialog-footer {
  display: flex;
  justify-content: center;
  align-items: center;
}

.customer {
  margin: 0 8px;
}

/* 补充dialog-avatar样式（避免封面图样式异常） */
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
</style>
