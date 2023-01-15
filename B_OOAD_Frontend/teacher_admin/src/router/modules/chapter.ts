export default {
  path: "/chapter",
  name: "Chapter",
  component: () => import("@/views/chapter/index.vue"),
  meta: {
    icon: "card",
    title: "课程",
    showLink: false
  }
} as RouteConfigsTable;
