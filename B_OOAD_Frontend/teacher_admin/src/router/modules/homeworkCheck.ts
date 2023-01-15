export default {
  path: "/homeworkCheck",
  name: "HomeworkCheck",
  component: () => import("@/views/homework/homeworkCheck.vue"),
  meta: {
    title: "作业检查",
    showLink: false
  }
} as RouteConfigsTable;
