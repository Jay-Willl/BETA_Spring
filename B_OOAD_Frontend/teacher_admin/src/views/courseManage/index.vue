<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { courseManageSubmitAPI, courseManageGetAPI } from "@/api/course";
import { useRouter } from "vue-router";
import { message } from "@/utils/message";
import { ElMessage } from "element-plus";

defineOptions({
  name: "CourseManage"
});

const router = useRouter();
const dataLoading = ref(true);

onMounted(() => {
  getCourseManageListData();
});

const courseManageList = ref([]);
const courseManageGetForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token")
});

const getCourseManageListData = async () => {
  try {
    console.log("courseManageGetForm", courseManageGetForm);
    await courseManageGetAPI(courseManageGetForm).then(res => {
      console.log("response courseManageGetAPI", res);
      courseManageList.value = res.data;
    });
  } catch (e) {
    console.log(e);
    ElMessage({
      message: "没有权限",
      type: "warning"
    });
  } finally {
    setTimeout(() => {
      dataLoading.value = false;
    }, 500);
  }
};

const manageForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: "",
  status: ""
});

interface course {
  title: string;
  teacher: string;
}

const handleSuccess = async (index: number, row: course) => {
  console.log("handle success");
  manageForm.course = courseManageList.value[index].course_id;
  manageForm.status = "success";
  console.log("manageForm", manageForm);
  await courseManageSubmitAPI(manageForm).then(res => {
    console.log("response courseManageSubmitAPI", res.data);
    if (res.data === "ok") {
      message("提交成功", { type: "success" });
    } else {
      message(res.data, { type: "error" });
    }
    router.go(0);
  });
};

const handleFail = async (index: number, row: course) => {
  console.log("handle fail");
  manageForm.course = courseManageList.value[index].course_id;
  manageForm.status = "fail";
  await courseManageSubmitAPI(manageForm).then(res => {
    console.log("response courseManageSubmitAPI", res.data);
    if (res.data === "ok") {
      message("提交成功", { type: "success" });
    } else {
      message(res.data, { type: "error" });
    }
    router.go(0);
  });
};
</script>

<template>
  <el-table :data="courseManageList" style="width: 100%">
    <el-table-column label="Title" prop="course_title" />
    <el-table-column label="Description" prop="course_description" />
    <el-table-column align="right">
      <template #default="scope">
        <el-button size="small" @click="handleSuccess(scope.$index, scope.row)"
          >Yes</el-button
        >
        <el-button
          size="small"
          type="danger"
          @click="handleFail(scope.$index, scope.row)"
          >No</el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>
