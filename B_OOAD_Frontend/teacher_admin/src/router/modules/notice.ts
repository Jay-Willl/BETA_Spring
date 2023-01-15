export default {
  path: "/notice",
  name: "Notice",
  component: () => import("@/views/notice/index.vue"),
  meta: {
    title: "通知",
    showLink: false
  }
} as RouteConfigsTable;
