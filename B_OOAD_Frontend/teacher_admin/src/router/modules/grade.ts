export default {
  path: "/grade",
  name: "Grade",
  component: () => import("@/views/grade/index.vue"),
  meta: {
    icon: "card",
    title: "分数",
    showLink: false
  }
} as RouteConfigsTable;
