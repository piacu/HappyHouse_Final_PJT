import Vue from "vue";
import VueRouter from "vue-router";
import HouseView from "@/views/HouseView.vue";
import MemberInter from "@/views/MemberInter.vue";
import ChattingView from "@/views/ChattingView.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "house",
    component: HouseView,
  },
  {
    path: "/chatting",
    name: "chattingview",
    component: ChattingView,
  },
  {
    path: "/userinter",
    name: "userinter",
    component: MemberInter,
  },
  {
    path: "/board",
    name: "board",
    component: () => import("@/views/BoardView.vue"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "write",
        name: "boardRegister",
        component: () => import("@/components/board/BoardRegister.vue"),
      },
      {
        path: "detail/:articleno",
        name: "boardDetail",
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "modify/:articleno",
        name: "boardModify",
        component: () => import("@/components/board/BoardModify.vue"),
      },
      {
        path: "delete/:articleno",
        name: "boardDelete",
        component: () => import("@/components/board/BoardDelete.vue"),
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/MemberView.vue"),
    children: [
      {
        path: "signin",
        name: "signIn",
        component: () => import("@/components/user/MemberLogin.vue"),
      },
      {
        path: "signup",
        name: "signUp",
        component: () => import("@/components/user/MemberRegister.vue"),
      },
      {
        path: "info",
        name: "info",
        component: () => import("@/components/user/MemberInfo.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
