<!-- eslint-disable no-unused-vars -->
<!-- eslint-disable prettier/prettier -->
<template>
  <div>
    <div style="padding: 0 50px;">
      <div>
        <p style="font-size: 24px;padding: 10px 0;font-weight: bolder;">
          <span @click="goBack" style="cursor: pointer;display: inline-block;padding: 0 20px 0 0;">
            <i class="el-icon-arrow-left"></i>
            返回首页
          </span>
          饮食记录
        </p>
      </div>
    </div>
    <div style="height: 6px;background-color: rgb(248, 248, 248);"></div>
    <div style="padding: 10px 50px;">
      <el-row>
        <el-col :span="6" style="border-right: 1px solid #f1f1f1;min-height: calc(100vh - 250px);">
          <!-- 修复：将事件绑定到el-tabs的tab-click事件 -->
          <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-right: 40px;">
            <el-tab-pane label="增肌" name="first"></el-tab-pane>
            <el-tab-pane label="减脂" name="second"></el-tab-pane>
          </el-tabs>
          <div style="padding: 20px 0 30px 0;">
            <!-- 保留原有新增按钮绑定 -->
            <span @click="add"
              style="cursor: pointer;padding: 10px 20px;background-color: #000;border-radius: 5px;color: #fff;">
              新增食谱
              <i class="el-icon-right"></i>
            </span>
          </div>
          <div>
            <span style="margin-right: 20px;">配置名</span>
            <el-input style="width: 148px;" v-model="userrecipe.name" placeholder="输入处" clearable
              @clear="handleFilterClear">
            </el-input>
            <el-button class="customer" style="margin-left: 20px;background-color: rgb(43, 121, 203);border: none;"
              type="primary" @click="searModel">搜索</el-button>
          </div>
          <div style="padding: 10px 0;margin-right: 40px;">
            <div @click="modelSelected(model)" class="item-model" v-for="(model, index) in modelList" :key="index">
              <el-tooltip class="item" effect="dark" :content="'该项配置【' + model.name + '】，点击即可选中'" placement="bottom">
                <el-row style="padding: 20px 0;">
                  <el-col :span="4">
                    <img :src="model.cover" style="width: 50px;height: 50px;margin-top: 5px;" />
                  </el-col>
                  <el-col :span="20">
                    <div style="padding: 0 10px;">
                      <div style="font-size: 24px;font-weight: bolder;">
                        {{ model.name }}
                      </div>
                      <div style="font-size: 14px;margin-top: 5px;">
                        <span>{{ model.typeName }}</span>
                        <span style="margin-left: 10px;">{{ model.symbol }}</span>

                      </div>
                    </div>
                  </el-col>
                </el-row>
              </el-tooltip>
            </div>
            <el-pagination style="margin: 20px 0;" @size-change="handleSizeChange" @current-change="handleCurrentChange"
              :current-page="currentPage" :page-sizes="[5, 7]" :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper" :total="totalItems"></el-pagination>
          </div>
        </el-col>
        <el-col :span="18">
          <div style="padding: 0 150px;box-sizing: border-box;">
            <div style="padding: 15px 0;font-size:24px;">
              数据录入面板
              <span @click="clearData" style="font-size: 14px;margin-left: 20px;">重置</span>
            </div>
            <el-row>
              <el-col :span="12" v-for="(model, index) in selectedModel" :key="index">
                <h3>{{ model.name }}({{ model.typeName }})</h3>
                            <el-select size="small" style="width: 188px;margin-right: 10px;" v-model="model.detail" placeholder="请选择类型">
              <el-option v-for="option in dietOptions" :key="option.value" :label="option.label"
                :value="option.value"></el-option>
            </el-select>
                <input type="text" v-model="model.energy" class="input-model"
                  placeholder="请输入热量" />
              </el-col>
            </el-row>
          </div>
          <div style="padding: 50px 150px;">
            <span @click="toRecord"
              style="cursor: pointer;padding: 10px 20px;background-color: #000;border-radius: 5px;color: #fff;">
              立即记录
              <i class="el-icon-right"></i>
            </span>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-dialog :show-close="false" :visible.sync="dialogrecipeOperaion" width="26%">
      <div slot="title">
        <p class="dialog-title">
          {{ !isOperation ? "新增食谱" : "编辑食谱信息" }}
        </p>
      </div>
      <div style="padding:0 20px;">
        <el-row>
          <div>
            <span class="dialog-hover">名称</span>
            <input class="dialog-input" v-model="data.name" placeholder="名称" />
          </div>
          <span class="dialog-hover">食谱图标</span>
          <el-upload class="avatar-uploader" action="/api/personal-heath/v1.0/file/upload" :show-file-list="false"
            :on-success="handleImageSuccess">
            <img v-if="data.cover" :src="data.cover" class="dialog-avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>

          <span class="dialog-hover">食谱类型</span>
          <div>
            <el-select size="small" style="width: 188px;margin-right: 10px;" v-model="data.typeId" placeholder="请选择类型">
              <el-option v-for="option in recipeOptions" :key="option.value" :label="option.label"
                :value="option.value"></el-option>
            </el-select>
          </div>
          <span class="dialog-hover">内容</span>
          <Editor @on-listener="onListener" height="200px" :visible="dialogrecipeOperaion"
            :receiveContent="data.content" />
        </el-row>
      </div>
      <div class="dialog-footer">
        <el-button size="small" v-if="!isOperation" style="background-color: rgb(43, 121, 203);border: none;"
          class="customer" type="info" @click="addOperation">新增</el-button>
        <el-button size="small" v-else style="background-color: rgb(43, 121, 203);border: none;" class="customer"
          type="info" @click="updateOperation">修改</el-button>
        <el-button class="customer" size="small" style="background-color: rgb(241, 241, 241);border: none;"
          @click="handleCancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Logo from "@/components/Logo";
import Editor from "@/components/Editor.vue";
import { getUserId } from "@/utils/storage.js";
// eslint-disable-next-line no-unused-vars
import router from "@/router";
export default {
  // eslint-disable-next-line vue/no-unused-components
  components: { Logo, Editor },
  data() {
    return {
      userId: 0,
      // 补充缺失的响应式数据
      userrecipe: { name: "" },
      modelList: [],
      selectedModel: [],
      // 保留原有数据定义
      data: { cover: "", typeId: 1, content: "" },
      currentPage: 1,
      pageSize: 7,
      totalItems: 0,
      dialogrecipeOperaion: false,
      isOperation: false,
      tableData: [],
      searchTime: [],
      selectedRows: [],
      recipeQueryDto: { typeId: 1 },
      userIdList: [],
      switchValue: false,
      activeName: "first",
      recipeOptions: [],
      dietOptions: []
    };
  },
  created() {
    this.userId = getUserId();
    this.fetchFreshData();
    this.fetchRecipeOptions();
        this.fetchDietOptions();
  },
  methods: {
     async fetchDietOptions() {
      try {
        const response = await this.$axios.get("/diet-history/fetchAllDiet");
        if (response.data.code === 200) {
          this.dietOptions = response.data.data;
        } else {
          this.$message.error("获取食谱类别下拉框数据失败");
        }
      } catch (error) {
        console.error("获取食谱类别下拉框数据异常：", error);
        this.$message.error("获取食谱类别下拉框数据异常，请稍后再试");
      }
    },
    async fetchRecipeOptions() {
      try {
        const response = await this.$axios.get("/recipe/fetchRecipeTypes");
        if (response.data.code === 200) {
          this.recipeOptions = response.data.data;
          console.log(this.recipeOptions);
        } else {
          this.$message.error("获取食谱类别下拉框数据失败");
        }
      } catch (error) {
        console.error("获取食谱类别下拉框数据异常：", error);
        this.$message.error("获取食谱类别下拉框数据异常，请稍后再试");
      }
    },
    handleCancel() {
      // 方案1：先清空属性再置为null（确保响应式更新）
      // 重置data为初始的空对象，保留响应式
      this.data = { cover: "", typeId: 1 };

      this.dialogrecipeOperaion = false;
    },
    // 新增返回首页方法（原代码缺失）
    goBack() {
      console.log("返回首页被点击");
      // 可根据实际路由添加返回逻辑
      this.$router.push("/news-record");
    },
    // 修复标签页点击事件
    handleClick(tab) {
      if (tab.name == "first") {
        this.recipeQueryDto.typeId = 1;
        this.recipeQueryDto.userId = null;
      } else if (tab.name == "second") {
        this.recipeQueryDto.typeId = 2;
        this.recipeQueryDto.userId = null;
      }
      this.fetchFreshData();
    },
    // 保留原有方法
    handleSwitchChange(val) {
      this.data.typeId = val;
    },
    formatDate(row, column) {
      const date = new Date(row.createTime);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
    onListener(content) {
      this.data.content = content;
    },
    async handleImageSuccess(res) {
      if (res.code !== 200) {
        this.$message.error("食谱封面上传异常");
        return;
      }
      this.$message.success("食谱封面上传成功");
      this.data.cover = res.data;
      console.log(this.data.cover);
    },
    handleSelectionChange(selection) {
      this.selectedRows = selection;
    },
    async batchDelete() {
      if (!this.selectedRows.length) {
        this.$message.warning("未选中任何数据");
        return;
      }
      const confirmed = await this.$swalConfirm({
        title: "删除食谱数据",
        text: "删除后不可恢复，是否继续？",
        icon: "warning"
      });
      if (confirmed) {
        try {
          const ids = this.selectedRows.map(entity => entity.id);
          const response = await this.$axios.post("/recipe/delete", ids);
          if (response.data.code === 200) {
            this.$swal.fire({
              title: "删除提示",
              text: response.data.msg,
              icon: "success",
              showConfirmButton: false,
              timer: 2000
            });
            this.fetchFreshData();
            this.selectedRows = [];
          }
        } catch (e) {
          this.$swal.fire({
            title: "错误提示",
            text: "删除失败，请稍后重试",
            icon: "error",
            showConfirmButton: false,
            timer: 2000
          });
          console.error("食谱信息删除异常：", e);
        }
      }
    },
    resetQueryCondition() {
      this.recipeQueryDto = {};
      this.searchTime = [];
      this.currentPage = 1;
      this.fetchFreshData();
    },
    async updateOperation() {
      try {
        const response = await this.$axios.put("/recipe/update", this.data);
        this.$swal.fire({
          title: "修改提示",
          text: response.data.msg,
          icon: response.data.code === 200 ? "success" : "error",
          showConfirmButton: false,
          timer: 1000
        });
        if (response.data.code === 200) {
          this.dialogrecipeOperaion = false;
          this.fetchFreshData();
          this.data = { cover: "", typeId: 1 };
        }
      } catch (error) {
        console.error("提交修改时出错:", error);
        this.$message.error("提交失败，请稍后再试！");
      }
    },
    async addOperation() {
      try {
        const response = await this.$axios.post("/recipe/insert", this.data);
        this.$message[response.data.code === 200 ? "success" : "error"](
          response.data.msg
        );
        if (response.data.code === 200) {
          this.dialogrecipeOperaion = false;
          this.fetchFreshData();
          this.data = { cover: "", typeId: 1 };
        }
      } catch (error) {
        console.error("提交新增时出错:", error);
        this.$message.error("提交失败，请稍后再试！");
      }
    },
    async fetchFreshData() {
      try {
        this.tableData = [];
        let startTime = null;
        let endTime = null;

        if (this.searchTime != null && this.searchTime.length === 2) {
          const [startDate, endDate] = this.searchTime;
          startTime = `${startDate.toISOString().split("T")[0]}T00:00:00`;
          endTime = `${endDate.toISOString().split("T")[0]}T23:59:59`;
        }
        console.log(this.recipeQueryDto.typeId);

        const params = {
          current: this.currentPage,
          size: this.pageSize,
          startTime,
          endTime,
          ...this.recipeQueryDto
        };

        const response = await this.$axios.post("/recipe/queryList", params);
        const { data } = response;
        this.tableData = data.data;
        this.totalItems = data.total;
        // 补充模型列表数据赋值（原代码缺失）
        console.log(data.data);
        this.modelList = data.data;
      } catch (error) {
        console.error("查询食谱信息异常:", error);
        this.$message.error("查询失败，请稍后再试！");
      }
    },
    // 保留原有新增方法
    add() {
      console.log("新增按钮被点击");
      this.isOperation = false;
      this.dialogrecipeOperaion = true;
    },
    handleFilter() {
      this.currentPage = 1;
      this.fetchFreshData();
    },
    handleFilterClear() {
      this.recipeQueryDto.title = "";
      this.handleFilter();
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
    handleEdit(row) {
      this.isOperation = true;
      this.data = { ...row };
      this.dialogrecipeOperaion = true;
    },
    handleDelete(row) {
      this.selectedRows = [row];
      this.batchDelete();
    },
    // 补充缺失的方法实现
    searModel() {
      console.log("搜索按钮被点击");
      this.fetchFreshData();
    },
    clearData() {
      console.log("重置按钮被点击");
      this.selectedModel = [];
    },
    async toRecord() {
      try {
        const params = this.selectedModel.map(entity => {
          return {
            recipeId: entity.id,
            energy: entity.energy,
            userId: this.userId,
            detail: entity.detail
          };
        });
        const { data } = await this.$axios.post(
          "/diet-history/batchSave",
          params
        );
        if (data.code === 200) {
          this.$message.success("记录成功");
          this.selectedModel = [];
        }
      } catch (e) {
        this.$message.error("记录失败，请稍后重试");
        console.error("健康记录异常：", e);
      }
    },
    modelSelected(model) {
      if (this.selectedModel.includes(model)) {
        // 按对象引用删除（也可替换为按属性匹配删除）
        const deleteIndex = this.selectedModel.indexOf(model);
        this.selectedModel.splice(deleteIndex, 1);

        this.$message.success("模型删除成功"); // 若用了 Element UI 可加提示
      } else {
        this.selectedModel.push(model);
      }
    },
    updateModel(model) {
      console.log("修改模型被点击:", model);
      this.handleEdit(model);
    },
    deleteModel(model) {
      console.log("删除模型被点击:", model);
      this.handleDelete(model);
    }
  }
};
</script>
<style scoped lang="scss">
.item-model:hover {
  cursor: pointer;
  background-color: #fafafa;
  border-radius: 5px;
}

.input-model {
  font-size: 20px;
  font-weight: bold;
  padding: 0 20px;
  user-select: none;
  border-radius: 5px;
  border: none;
  outline: none;
  background-color: #f1f1f1;
  height: 50px;
  width: 85%;
}

.inline-input {
  display: inline-block;
  width: 35%;
}
</style>
