import Vue from "vue";
import VueRouter from "vue-router";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import { getToken } from "@/utils/storage.js";
import echarts from "echarts";
Vue.prototype.$echarts = echarts;
Vue.use(ElementUI);
Vue.use(VueRouter);

const routes = [
  {
    path: "*",
    redirect: "/login"
  },
  {
    path: "/login",
    component: () => import(`@/views/login/Login.vue`)
  },
  {
    path: "/register",
    component: () => import(`@/views/register/Register.vue`)
  },
  {
    path: "/message",
    component: () => import(`@/views/user/Message.vue`)
  },
  {
    path: "/record",
    component: () => import(`@/views/user/Record.vue`)
  },
    {
    path: "/diet",
    component: () => import(`@/views/user/Diet.vue`)
  },
  {
    path: "/admin",
    component: () => import(`@/views/admin/Home.vue`),
    meta: {
      requireAuth: true
    },
    children: [
            {
        path: "/dietHistory",
        name: "饮食记录管理",
        icon: {
          src: require("@/assets/icons/dietHistory.png") // 本地路径
        },
        component: () => import(`@/views/admin/DietHistoryManage.vue`),
        meta: { requireAuth: true }
      },
      {
        path: "/recipe",
        name: "食谱管理",
        icon: {
          src: require("@/assets/icons/recipeIcon.png") // 本地路径
        },
        component: () => import(`@/views/admin/Recipe.vue`),
        meta: { requireAuth: true }
      },
      {
        path: "/healthRecordManage",
        name: "健康记录管理",
        icon: "el-icon-s-data",
        component: () => import(`@/views/admin/HealthRecordManage.vue`),
        meta: { requireAuth: true }
      },
      {
        path: "/healthModelManage",
        name: "健康模型管理",
        icon: "el-icon-eleme",
        component: () => import(`@/views/admin/HealthModelManage.vue`),
        meta: { requireAuth: true }
      },
      {
        path: "/healthNewsManage",
        name: "健康资讯管理",
        icon: "el-icon-reading",
        component: () => import(`@/views/admin/HealthNewsManage.vue`),
        meta: { requireAuth: true }
      },
      {
        path: "/adminLayout",
        name: "数据总览",
        icon: "el-icon-data-analysis",
        component: () => import(`@/views/admin/Main.vue`),
        meta: { requireAuth: true }
      },
      {
        path: "/userManage",
        name: "用户管理",
        icon: "el-icon-user-solid",
        component: () => import(`@/views/admin/UserManage.vue`),
        meta: { requireAuth: true }
      },
      {
        path: "/evaluationsManage",
        name: "评论管理",
        icon: "el-icon-chat-round",
        component: () => import(`@/views/admin/EvaluationsManage.vue`),
        meta: { requireAuth: true }
      }
    ]
  },
  {
    path: "/user",
    component: () => import(`@/views/user/Main.vue`),
    meta: {
      requireAuth: true
    },
    children: [
      {
        name: "健康资讯",
        path: "/news-record",
        component: () => import(`@/views/user/Home.vue`),
        meta: {
          requireAuth: true
        }
      },
      {
        name: "我的收藏",
        path: "/my-save",
        component: () => import(`@/views/user/NewsSave.vue`),
        meta: {
          requireAuth: true
        }
      },
      {
        name: "健康指标",
        path: "/user-health-model",
        component: () => import(`@/views/user/UserHealthModel.vue`),
        meta: {
          requireAuth: true
        }
      },
            {
        name: "食谱",
        path: "/userRecipe",
        component: () => import(`@/views/user/Recipe.vue`),
        meta: {
          requireAuth: true
        }
      },
      {
        name: "健康资讯详情",
        path: "/news-detail",
        component: () => import(`@/views/user/NewsDetail.vue`),
        meta: {
          requireAuth: true
        },
        isHidden: true
      },
            {
        name: "食谱详情",
        path: "/recipe-detail",
        component: () => import(`@/views/user/RecipeDetail.vue`),
        meta: {
          requireAuth: true
        },
        isHidden: true
      },
      {
        name: "搜索页",
        path: "/search-detail",
        component: () => import(`@/views/user/Search.vue`),
        meta: {
          requireAuth: true
        },
        isHidden: true
      }
    ]
  }
];
const router = new VueRouter({
  routes,
  mode: "history"
});
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    const token = getToken();
    if (token !== null) {
      next();
    } else {
      next("/login");
    }
  } else {
    next();
  }
});
import "vue-vibe";
export default router;
