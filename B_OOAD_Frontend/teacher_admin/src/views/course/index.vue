<script setup lang="ts">
import Card from "./CourseCard.vue";
import { courseGetAPI, courseDeleteAPI } from "@/api/course";
import { message } from "@/utils/message";
import {
  ElMessageBox,
  FormInstance,
  UploadUserFile,
  UploadProps
} from "element-plus";
import { ref, onMounted, reactive } from "vue";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import AddFill from "@iconify-icons/ri/add-circle-line";
import { courseCreateAPI } from "@/api/course";
import router from "@/router";

const svg = `
        <path class="path" d="
          M 30 15
          L 28 17
          M 25.61 25.61
          A 15 15, 0, 0, 1, 15 30
          A 15 15, 0, 1, 1, 27.99 7.5
          L 15 15
        " style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)"/>
      `;

defineOptions({
  name: "Course"
});

onMounted(() => {
  getCourseListData();
});

//==============Course Card================//
const courseList = ref([]);
const dataLoading = ref(true);
const pagination = ref({ current: 1, pageSize: 12, total: 0 });

const courseGetForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token")
});

const getCourseListData = async () => {
  try {
    //const { data } = await getCourseList();
    console.log("courseGetForm", courseGetForm);
    await courseGetAPI(courseGetForm)
      .then(res => {
        console.log("response courseGetAPI", res);
        courseList.value = res.data;
        pagination.value = {
          ...pagination.value,
          total: res.data.length
        };
        console.log("courseList", courseList.value);
      })
      .catch(e => {
        console.log(e);
      });
  } catch (e) {
    console.log(e);
  } finally {
    setTimeout(() => {
      dataLoading.value = false;
    }, 500);
  }
};

const handleDeleteCourse = course => {
  ElMessageBox.confirm(course ? `确认删除` : "", "提示", {
    type: "warning"
  })
    .then(async () => {
      await courseDeleteAPI({
        username: localStorage.getItem("username"),
        token: localStorage.getItem("token"),
        course: course.course_title
      }).then(res => {
        if (res.data === "ok") {
          message("删除成功", { type: "success" });
          router.go(0);
        } else {
          message("删除失败", { type: "error" });
        }
      });
    })
    .catch(() => {});
};

//==============新建Course================//
const dialogFormVisible = ref(false);
const ruleFormRef = ref<FormInstance>();
const fileList = ref<UploadUserFile[]>([]);

const courseForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  data: {
    title: "",
    cover: "",
    teacher: localStorage.getItem("username"),
    price: 0,
    description: "",
    status: "checking",
    field: "",
    tag: ""
  }
});

const tagValue = ref([]);

const handleTagChange = () => {
  courseForm.data.field = tagValue.value[0];
  courseForm.data.tag = tagValue.value[1];
  console.log("courseForm Tag Change", courseForm);
};

const options = [
  {
    value: "computer science",
    label: "computer science",
    children: [
      {
        value: "Artificial intelligence",
        label: "Artificial intelligence"
      },
      {
        value: "Computer vision",
        label: "Computer vision"
      }
    ]
  },
  {
    value: "e-sports",
    label: "e-sports",
    children: [
      {
        value: "CSGO",
        label: "CSGO"
      },
      {
        value: "DOTA2",
        label: "DOTA2"
      }
    ]
  },
  {
    value: "art",
    label: "art",
    children: [
      {
        value: "Music",
        label: "Music"
      },
      {
        value: "Painting",
        label: "Painting"
      }
    ]
  }
];

const closeDialog = () => {
  dialogFormVisible.value = false;
  resetForm(ruleFormRef.value);
};

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};

const submitForm = async (formEl: FormInstance | undefined) => {
  console.log("submit course form", formEl);
  if (!formEl) return;
  await formEl.validate(valid => {
    if (valid) {
      courseCreateAPI(courseForm).then(res => {
        console.log("course submit response", res.data);
        if (res.data === "ok") {
          message("提交成功", { type: "success" });
          dialogFormVisible.value = false;
          resetForm(formEl);
          router.go(0);
        } else {
          message(res.data, { type: "error" });
        }
      });
    }
  });
};

const handleRemove: UploadProps["onRemove"] = (file, uploadFiles) => {
  console.log(file, uploadFiles);
};

const handlePreview: UploadProps["onPreview"] = uploadFile => {
  console.log(uploadFile);
};

const handleUploadCoverSuccess = res => {
  console.log("upload cover success");
  console.log(res);
  courseForm.data.cover = res;
};
</script>

<template>
  <div class="main">
    <div class="w-full flex justify-between mb-4">
      <el-button
        :icon="useRenderIcon(AddFill)"
        @click="dialogFormVisible = true"
      >
        新建课程
      </el-button>
    </div>
    <div
      v-loading="dataLoading"
      :element-loading-svg="svg"
      element-loading-svg-view-box="-10, -10, 50, 50"
    >
      <template v-if="pagination.total > 0">
        <el-row :gutter="16">
          <el-col
            v-for="(course, index) in courseList"
            :key="index"
            :xs="24"
            :sm="12"
            :md="8"
            :lg="6"
            :xl="4"
          >
            <Card :courseInfo="course" @delete-course="handleDeleteCourse" />
          </el-col>
        </el-row>
      </template>
    </div>
    <el-dialog
      v-model="dialogFormVisible"
      title="新建课程"
      :width="680"
      draggable
      :before-close="closeDialog"
    >
      <!-- 表单内容 -->
      <el-form ref="ruleFormRef" :model="courseForm" label-width="100px">
        <el-form-item label="课程名称">
          <el-input
            v-model="courseForm.data.title"
            :style="{ width: '480px' }"
            placeholder="请输入课程名称"
          />
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input
            v-model="courseForm.data.description"
            type="textarea"
            :style="{ width: '480px' }"
            placeholder="请输入课程描述"
          />
        </el-form-item>
        <el-form-item label="课程价格">
          <el-input-number v-model="courseForm.data.price" />
        </el-form-item>
        <el-form-item label="课程分类">
          <el-cascader
            v-model="tagValue"
            :options="options"
            @change="handleTagChange"
          />
        </el-form-item>
        <el-form-item label="封面">
          <el-upload
            v-model:file-list="fileList"
            class="upload-demo"
            action="http://localhost:8075/upload/file"
            :on-success="handleUploadCoverSuccess"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            list-type="picture"
            accept=".jpg,.png"
          >
            <el-button type="primary">Click to upload</el-button>
            <template #tip>
              <div class="el-upload__tip">
                jpg/png files with a size less than 500kb
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>
