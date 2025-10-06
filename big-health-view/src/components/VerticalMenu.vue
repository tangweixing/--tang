<!-- eslint-disable prettier/prettier -->
<!-- eslint-disable prettier/prettier -->
<template>
  <el-menu :collapse-transition="false" :collapse="flag" style="padding: 5px 20px;max-width: 253px;"
    :default-active="activeIndex" :background-color="bag" text-color="#666" @select="handleSelect">
    <el-menu-item v-for="(item, index) in routes" :key="index" style="width: 100%;"
      v-if="!item.children || item.children.length === 0" :index="item.path"
      :class="{ 'is-active': activeIndex === item.path }">
      <!-- 本地图片图标：item.icon 是对象且含 src -->
      <img v-if="item.icon && typeof item.icon === 'object' && item.icon.src" :src="item.icon.src"
        class="local-menu-icon" alt="菜单图标"
        style="width: 20px; height: 20px; vertical-align: middle; margin-right: 8px;">
      <!-- Element UI 图标：item.icon 是字符串 -->
      <i v-else-if="item.icon && typeof item.icon === 'string'" :class="item.icon"
        style="font-size: 20px; vertical-align: middle; margin-right: 8px;"></i>
      <span slot="title" style="font-size: 14px;">{{ item.name }}</span>
    </el-menu-item>
  </el-menu>
</template>
<script>
export default {
  name: "AdminMenu",
  data() {
    return {
      activeIndex: "1",
      isCollapse: true,
      selectedMenuItem: ""
    };
  },
  props: {
    routes: {
      type: Array,
      required: true
    },
    flag: {
      type: Boolean,
      required: true
    },
    bag: {
      type: String,
      default: "#FFFFFF"
    }
  },
  created() {
    // 上次选中路径
    const saveLastPath = sessionStorage.getItem("activeMenuItem");
    if (saveLastPath === null) {
      // 加载首页
      this.handleSelect("/adminLayout");
    } else {
      this.handleSelect(saveLastPath);
    }
  },
  methods: {
    handleSelect(index) {
      this.activeIndex = index;
      this.$emit("select", this.activeIndex);
      sessionStorage.setItem("activeMenuItem", this.activeIndex);
    }
  }
};
</script>

<style scoped>
.is-active {
  background-color: rgb(241, 241, 241) !important;
  color: #1c1c1c !important;
  font-weight: bold;
  border-radius: 6px;
}

.el-menu-item,
.el-submenu__title {
  height: 45px !important;
  line-height: 45px !important;
  user-select: none;
  color: #333;
}

.el-menu-item:focus,
.el-menu-item:hover {
  box-sizing: border-box;
  border-radius: 5px;
}

.el-menu-item {
  height: 45px !important;
  line-height: 45px !important;
  margin: 3px;
}

.el-menu-item:hover {
  background-color: rgb(241, 241, 241) !important;
}
</style>
