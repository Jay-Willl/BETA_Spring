<script setup lang="ts">
import { message } from "@/utils/message";
import {
  ElMessage,
  ElMessageBox,
  FormInstance,
  UploadUserFile
} from "element-plus";
import type { UploadInstance } from "element-plus";
import { ref, onMounted, reactive } from "vue";
import { useRouter } from "vue-router";
import {
  chapterCreateAPI,
  chapterGetAPI,
  lessonCreateAPI,
  lessonGetAPI,
  chapterDeleteAPI,
  videoViewAPI
} from "@/api/course";

import Player from "xgplayer";

defineOptions({
  name: "Chapter"
});

const router = useRouter();
const dataLoading = ref(true);

onMounted(() => {
  getChapterListData();
  getLessonListData();
});

//======================Chapter=======================//
const pagination = ref({ current: 1, pageSize: 12, total: 0 });
const chapterList = ref([]);
const chapter_index = ref();

const chapterGetForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course")
});

const chapterDeleteForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course"),
  chapter: 0
});

const getChapterListData = async () => {
  try {
    console.log("chapterGetForm", chapterGetForm);
    await chapterGetAPI(chapterGetForm).then(res => {
      console.log("response chapterGetAPI", res);
      chapterList.value = res.data;
      pagination.value = {
        ...pagination.value,
        total: res.data.length
      };
      console.log("chapterList", chapterList.value);
      chapter_index.value = res.data.length;
      console.log("chapter length", chapter_index.value);
    });
  } catch (e) {
    console.log(e);
  } finally {
    setTimeout(() => {
      dataLoading.value = false;
    }, 500);
  }
};

const handleDeleteChapter = async (chapter_index: number) => {
  ElMessageBox.confirm("Delete this chapter?", {
    confirmButtonText: "OK",
    cancelButtonText: "Cancel",
    type: "warning"
  })
    .then(async () => {
      chapterDeleteForm.chapter = chapter_index;
      await chapterDeleteAPI(chapterDeleteForm).then(res => {
        console.log("chapter delete response", res.data);
        if (res.data === "ok") {
          message("删除成功", { type: "success" });
        } else {
          message(res.data, { type: "error" });
        }
        router.go(0);
      });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "Delete canceled"
      });
    });
};

//===============添加Chapter=======================//

const dialogFormVisible_chapter = ref(false);
const chapterFormRef = ref<FormInstance>();
const formLabelWidth = "140px";

const chapterForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course"),
  data: {
    index: 0,
    title: ""
  }
});

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};

const cancelChapterDialog = () => {
  chapterForm.data.title = "";
  chapterForm.data.index = 0;
  dialogFormVisible_chapter.value = false;
};
// TODO:
const submitChapterDialog = async () => {
  console.log("chapter create form", chapterForm);
  await chapterCreateAPI(chapterForm).then(res => {
    console.log("chapter submit response", res.data);
    if (res.data === "ok") {
      message("提交成功", { type: "success" });
      dialogFormVisible_chapter.value = false;
    } else {
      message(res.data, { type: "error" });
    }
  });
  dialogFormVisible_chapter.value = false;
  chapterForm.data.title = "";
  chapterForm.data.index = 0;
  await getChapterListData();
  await getLessonListData();
};

//======================Lesson=======================//

const lessonList = ref([]);
const lessonData = ref([]);
const lessonCount1 = ref(0);
const lessonCount2 = ref(0);

const lessonGetForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course")
});

const getLessonListData = async () => {
  try {
    console.log("lessonGetForm", lessonGetForm);
    await lessonGetAPI(lessonGetForm).then(res => {
      console.log("response lessonGetAPI", res);
      lessonData.value = res.data;

      while (lessonCount1.value < chapterList.value.length) {
        if (
          lessonCount2.value < lessonData.value.length &&
          lessonData.value[lessonCount2.value].index - 1 === lessonCount1.value
        ) {
          lessonList.value.push(
            lessonData.value[lessonCount2.value].lessonList.sort(function (
              a,
              b
            ) {
              return a.lesson_index - b.lesson_index;
            })
          );
          lessonCount2.value++;
          console.log("yes");
        } else {
          console.log("no");
          lessonList.value.push([]);
        }
        lessonCount1.value++;
      }
      console.log("lessonList", lessonList.value);
    });
  } catch (e) {
    console.log(e);
  } finally {
    setTimeout(() => {
      dataLoading.value = false;
    }, 500);
  }
};

const dialogFormVisible_lesson = ref(false);
const fileList = ref<UploadUserFile[]>([]);

const lessonForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course"),
  chapter: 0,
  data: {
    index: 0,
    title: "",
    description: "",
    id: "",
    score: 0
  }
});

const openLessonDialog = (chapter_id: number) => {
  dialogFormVisible_lesson.value = true;
  lessonForm.chapter = chapter_id;
  console.log("chapter id", chapter_id);
  console.log("open lesson dialog", lessonForm);
};

const cancelLessonDialog = () => {
  lessonForm.data.title = "";
  lessonForm.data.index = 0;
  lessonForm.data.id = "";
  lessonForm.data.description = "";
  lessonForm.data.score = 0;
  lessonForm.chapter = 0;
  dialogFormVisible_lesson.value = false;
};

const uploadRef = ref<UploadInstance>();

const submitLessonDialog = async () => {
  console.log("lesson create form", lessonForm);
  await lessonCreateAPI(lessonForm).then(res => {
    console.log("lesson submit response", res.data);
    if (res.data === "ok") {
      message("提交成功", { type: "success" });
      dialogFormVisible_lesson.value = false;
    } else {
      message(res.data, { type: "error" });
    }
  });
  dialogFormVisible_lesson.value = false;
  lessonForm.chapter = 0;
  lessonForm.data.title = "";
  lessonForm.data.index = 0;
  lessonForm.data.id = "";
  lessonForm.data.score = 0;
  lessonForm.data.description = "";
  router.go(0);
  await getChapterListData();
  await getLessonListData();
};

const handleUploadVideoSuccess = res => {
  console.log("upload video success");
  console.log(res);
  lessonForm.data.id = res;
};

//======================视频预览=========================//
const videoViewVisible = ref(false);

const videoURL = ref("");
let player = null;
const initPlayer = (status: number) => {
  if (status === 1) {
    if (videoURL.value === "") return console.warn("url is null");
    const config = {
      id: "video-player",
      url: videoURL.value,
      fluid: true,
      /**倍速播放 */
      playbackRate: [0.5, 0.75, 1, 1.5, 2],
      defaultPlaybackRate: 1,
      /**初始化首帧 */
      videoInit: true,
      autoplay: true
    };
    player = new Player(config);
  }
};

const handleVideoClose = (status: number) => {
  videoViewVisible.value = false;
  console.log("status", status);
  if (status === 1) {
    player.destroy();
  }
};

const handleVideoView = async (chapter_index: number, lesson_index: number) => {
  videoViewVisible.value = true;
  console.log("chapter index", chapter_index);
  console.log("video index", lesson_index);
  console.log(
    "lesson id",
    lessonList.value[chapter_index][lesson_index].lesson_id
  );
  await videoViewAPI(lessonList.value[chapter_index][lesson_index].lesson_id)
    .then(res => {
      console.log("res", res.data);
      videoURL.value = res.data.playInfoList[3].playURL;
      console.log("lesson url", videoURL.value);
      initPlayer(1);
    })
    .catch(e => {
      initPlayer(0);
      ElMessage.error("请等待，视频上传中");
    });
};
</script>

<template>
  <div class="main">
    <div class="w-full flex justify-between mb-4">
      <el-button @click="dialogFormVisible_chapter = true">
        新建章节
      </el-button>
    </div>
    <div v-loading="dataLoading">
      <template v-if="pagination.total > 0">
        <el-row :gutter="16">
          <el-col
            v-for="(chapter, index) in chapterList"
            :key="index"
            :xs="24"
            :sm="12"
            :md="8"
            :lg="6"
            :xl="4"
          >
            <el-card class="box-card">
              <template #header>
                <div class="card-header">
                  <span>
                    第{{ chapter.chapter_index }}章 {{ chapter.chapter_title }}
                  </span>
                  <el-button
                    style="float: right"
                    @click="handleDeleteChapter(chapter.chapter_index)"
                    >删除章节</el-button
                  >
                </div>
              </template>
              <el-scrollbar height="130px">
                <div
                  v-for="(lesson, i) in lessonList[index]"
                  :key="i"
                  class="text item"
                >
                  <el-button
                    class="button"
                    text
                    @click="handleVideoView(index, i)"
                  >
                    {{ lessonList[index][i].lesson_title }}
                  </el-button>
                </div>
              </el-scrollbar>

              <el-button
                style="width: 100%"
                @click="openLessonDialog(chapter.chapter_id)"
                >添加视频</el-button
              >
            </el-card>
          </el-col>
        </el-row>
      </template>
    </div>

    <!-- 预览视频 -->
    <div>
      <el-dialog
        v-model="videoViewVisible"
        title="视频预览"
        @close="handleVideoClose(1)"
      >
        <div id="video-player" />
      </el-dialog>
    </div>

    <!-- 新建视频 -->
    <div>
      <el-dialog v-model="dialogFormVisible_lesson" title="添加视频">
        <el-form :model="lessonForm">
          <el-form-item label="Lesson Index" :label-width="formLabelWidth">
            <el-input-number
              v-model="lessonForm.data.index"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item label="Lesson Title" :label-width="formLabelWidth">
            <el-input v-model="lessonForm.data.title" autocomplete="off" />
          </el-form-item>
          <el-form-item label="Lesson Score" :label-width="formLabelWidth">
            <el-input-number
              v-model="lessonForm.data.score"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item label="Description" :label-width="formLabelWidth">
            <el-input
              v-model="lessonForm.data.description"
              autocomplete="off"
              type="textarea"
            />
          </el-form-item>
          <el-form-item label="Lesson Video" :label-width="formLabelWidth">
            <el-upload
              v-model:file-list="fileList"
              :on-success="handleUploadVideoSuccess"
              ref="uploadRef"
              class="upload-demo"
              action="http://localhost:8075/teacher/create/video"
              accept=".mp4"
            >
              <template #trigger>
                <el-button type="primary"
                  >select file (only accept .mp4)</el-button
                >
              </template>
            </el-upload>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="cancelLessonDialog">Cancel</el-button>
            <el-button type="primary" @click="submitLessonDialog">
              Confirm
            </el-button>
          </span>
        </template>
      </el-dialog>
    </div>

    <!-- 新建章节 -->
    <div>
      <el-dialog v-model="dialogFormVisible_chapter" title="添加章节">
        <el-form ref="chapterFormRef" :model="chapterForm">
          <el-form-item label="Chapter Index" :label-width="formLabelWidth">
            <el-input-number
              v-model="chapterForm.data.index"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item label="Chapter Title" :label-width="formLabelWidth">
            <el-input v-model="chapterForm.data.title" autocomplete="off" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="cancelChapterDialog">Cancel</el-button>
            <el-button type="primary" @click="submitChapterDialog()">
              Confirm
            </el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<style scoped>
.scrollbar-flex-content {
  display: flex;
}
</style>
