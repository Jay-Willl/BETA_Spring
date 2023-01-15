export default {
  path: "/courseManage",
  name: "CourseManage",
  component: () => import("@/views/courseManage/index.vue"),
  meta: {
    icon: "card",
    title: "课程管理",
  }
} as RouteConfigsTable;
