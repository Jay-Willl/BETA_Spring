export default {
  path: "/homework",
  name: "Homework",
  component: () => import("@/views/homework/index.vue"),
  meta: {
    title: "作业",
    showLink: false
  }
} as RouteConfigsTable;
