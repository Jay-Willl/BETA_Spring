export default {
  path: "/course",
  name: "Course",
  component: () => import("@/views/course/index.vue"),
  meta: {
    icon: "card",
    title: "课程",
    showParent: true
  }
} as RouteConfigsTable;
