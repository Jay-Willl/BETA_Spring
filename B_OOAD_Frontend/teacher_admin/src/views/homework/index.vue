<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import {
  homeworkSubmitAPI,
  quizSubmitAPI,
  homeworkGetAPI,
  quizGetAPI,
  homeworkCheckAPI,
  homeworkReviewAPI
} from "@/api/homework";
import FileSaver from "file-saver";
import { useUserStoreHook } from "@/store/modules/user";
import { message } from "@/utils/message";
import { useRouter } from "vue-router";
import moment from "moment";

const router = useRouter();

defineOptions({
  name: "Homework"
});

const dialogFormVisible_homework = ref(false);
const dialogFormVisible_quiz = ref(false);
const dataLoading = ref(true);

onMounted(() => {
  getHomeworkTableData();
  getQuizTableData();
  console.log("welcome to homework");
});

const downloadQuizJSON = () => {
  const blob = new Blob([JSON.stringify(quizForm)], {
    type: "application/json"
  });
  FileSaver.saveAs(blob, "quiz.json");
};

//=======================Homework===========================//

const homeworkGetForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course")
});

const homeworkData = ref([]);
const homeworkTableData = ref([]);

const getHomeworkTableData = async () => {
  try {
    console.log("homeworkGetForm", homeworkGetForm);
    await homeworkGetAPI(homeworkGetForm).then(res => {
      console.log("response homeworkGetAPI", res);
      homeworkData.value = res.data;
      let i: number;
      for (i = 0; i < homeworkData.value.length; i++) {
        console.log(i, homeworkData.value[i]);
        const s = JSON.parse(
          homeworkData.value[i].homework_content.replace(
            new RegExp("/", ""),
            ""
          )
        );
        console.log("s", s);
        homeworkTableData.value.push({
          homework_index: homeworkData.value[i].homework_index,
          homework_date: moment(
            new Date(homeworkData.value[i].homework_time)
          ).format("YYYY-MM-DD"),
          homework_title: s.title,
          homework_content: s.content
        });
      }
      console.log("homeworkTableData", homeworkTableData.value);
    });
  } catch (e) {
    console.log(e);
  } finally {
    setTimeout(() => {
      dataLoading.value = false;
    }, 500);
  }
};

const homeworkForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course"),
  index: 0,
  data: ""
});

const homeworkFormData = reactive({
  title: "",
  content: ""
});

const cancelHomeworkDialog = () => {
  homeworkFormData.title = "";
  homeworkFormData.content = "";
  dialogFormVisible_homework.value = false;
};

const submitHomeworkDialog = async () => {
  homeworkForm.index = homeworkTableData.value.length + 1;
  homeworkForm.data = JSON.stringify(homeworkFormData);
  console.log("homeworkForm", homeworkForm);
  await homeworkSubmitAPI(homeworkForm).then(res => {
    console.log("homework submit response", res.data);
    if (res.data === "ok") {
      message("提交成功", { type: "success" });
    } else {
      message(res.data, { type: "error" });
    }
  });
  dialogFormVisible_homework.value = false;
  homeworkForm.data = "";
  router.go(0);
};

//=======================Homework Check===========================//
const homeworkCheckVisible = ref(false);
const homeworkCheckForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course"),
  index: 0
});

const homeworkCheckData = ref([]);
const homeworkCheckTableData = ref([]);

const downloadPDF = (index: number) => {
  window.open(homeworkCheckTableData.value[index].url);
};

const goHomeworkCheck = async (index: number) => {
  console.log(index);
  homeworkCheckForm.index = homeworkTableData.value[index].homework_index;
  homeworkCheckVisible.value = true;
  homeworkCheckIndex.value = homeworkTableData.value[index].homework_index;
  console.log("homeworkCheckForm", homeworkCheckForm);
  await homeworkCheckAPI(homeworkCheckForm).then(res => {
    console.log("response homeworkCheckAPI", res);
    homeworkCheckData.value = res.data;
    console.log("homeworkCheckData", homeworkCheckData.value);
    let i: number;
    for (i = 0; i < homeworkCheckData.value.length; i++) {
      console.log(i, homeworkCheckData.value[i]);
      homeworkCheckTableData.value.push({
        name: homeworkCheckData.value[i].user_name,
        date: moment(new Date(homeworkCheckData.value[i].submit_time)).format(
          "YYYY-MM-DD"
        ),
        status:
          homeworkCheckData.value[i].submit_checked === 1
            ? "checked"
            : "unchecked",
        url: homeworkCheckData.value[i].pic_url
      });
    }
    console.log("homeworkCheckTableData", homeworkCheckTableData.value);
  });
};

const handleHomeworkCheckClose = () => {
  homeworkCheckVisible.value = false;
  homeworkCheckTableData.value = [];
  homeworkCheckIndex.value = 0;
};

//=======================Homework Score===========================//
const scoreVisible = ref(false);
const scoreForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course"),
  index: 0,
  //name: reviewName.value,
  name: "",
  score: 0,
  commit: ""
});

const homeworkCheckIndex = ref(0);

const onScoreHomework = (index: number) => {
  scoreVisible.value = true;
  scoreForm.name = homeworkCheckTableData.value[index].name;
  scoreForm.index = homeworkCheckIndex.value;
  console.log("index", index);
  console.log("scoreForm onOpen", scoreForm);
  console.log("homeworkTableData", homeworkTableData.value);
};

const handleScoreClose = () => {
  scoreForm.score = 0;
  scoreForm.commit = "";
  scoreForm.name = "";
  scoreVisible.value = false;
};

const submitScoreForm = async () => {
  console.log("scoreForm", scoreForm);
  await homeworkReviewAPI(scoreForm).then(res => {
    console.log("score response", res.data);
    if (res.data === "ok") {
      message("提交成功", { type: "success" });
    } else {
      message(res.data, { type: "error" });
    }
  });
  scoreForm.score = 0;
  scoreForm.commit = "";
  scoreForm.name = "";
  scoreVisible.value = false;
};

//=======================Quiz===========================//
const quizGetForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course")
});

const quizData = ref([]);
const quizTableData = ref([]);

const getQuizTableData = async () => {
  try {
    console.log("quizGetForm", homeworkGetForm);
    await quizGetAPI(quizGetForm).then(res => {
      console.log("response quizGetAPI", res);
      quizData.value = res.data;
      let i: number;
      for (i = 0; i < quizData.value.length; i++) {
        console.log(i, quizData.value[i]);
        const s = JSON.parse(
          quizData.value[i].quiz_content.replace(new RegExp("/", ""), "")
        );
        quizTableData.value.push({
          quiz_index: quizData.value[i].quiz_index,
          quiz_date: moment(new Date(quizData.value[i].quiz_time)).format(
            "YYYY-MM-DD"
          ),
          quiz_title: s.quiz_title
        });
      }
      console.log("quizTableData", quizTableData.value);
    });
  } catch (e) {
    console.log(e);
  } finally {
    setTimeout(() => {
      dataLoading.value = false;
    }, 500);
  }
};

const goQuizDetail = (index: number) => {
  console.log(index);
  localStorage.setItem("homework", homeworkTableData.value[index]);
};

const quizForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course"),
  index: 0,
  data: ""
});

const quizFormData = reactive({
  quiz_title: "",
  quiz_time: 5,
  quiz_type: "",
  quiz_content: []
});

const addQuestion = (type: string) => {
  if (type === "select") {
    console.log(quizFormData.quiz_content);
    quizFormData.quiz_content.push({
      question: "",
      answer: "",
      content: {
        opt1: "",
        opt2: "",
        opt3: "",
        opt4: ""
      }
    });
  } else {
    quizFormData.quiz_content.push({
      question: "",
      answer: "",
      content: {
        opt1: "True",
        opt2: "False"
      }
    });
  }
};

const typeChange = (type: string) => {
  console.log(type);
  quizFormData.quiz_content.length = 0;
};

const cancelQuizDialog = () => {
  quizFormData.quiz_title = "";
  quizFormData.quiz_type = "";
  quizFormData.quiz_time = 5;
  quizFormData.quiz_content = [];
  dialogFormVisible_quiz.value = false;
};

const submitQuizDialog = async () => {
  quizForm.index = quizTableData.value.length + 1;
  quizForm.data = JSON.stringify(quizFormData);
  console.log("quizForm", quizForm);
  await quizSubmitAPI(quizForm).then(res => {
    console.log("quiz submit response", res.data);
    if (res.data === "ok") {
      message("提交成功", { type: "success" });
    } else {
      message(res.data, { type: "error" });
    }
  });
  dialogFormVisible_quiz.value = false;
  quizForm.data = "";
  router.go(0);
};
</script>

<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <!-- Homework表格 -->
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>Homework</span>
              <el-button
                style="float: right"
                @click="dialogFormVisible_homework = true"
              >
                新建作业
              </el-button>
            </div>
          </template>
          <el-scrollbar height="250px">
            <div>
              <div>
                <el-table :data="homeworkTableData" style="width: 100%">
                  <el-table-column
                    prop="homework_title"
                    label="Title"
                    width="250"
                  />
                  <el-table-column
                    prop="homework_date"
                    label="Date"
                    width="300"
                  />
                  <el-table-column fixed="right" label="Operator" width="100">
                    <template #default="scope">
                      <el-button
                        size="small"
                        @click="goHomeworkCheck(scope.$index)"
                        >进入</el-button
                      >
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </div>
          </el-scrollbar>
        </el-card>
      </el-col>
      <el-col :span="12">
        <!-- Quiz表格 -->
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>Quiz</span>
              <el-button
                style="float: right"
                @click="dialogFormVisible_quiz = true"
              >
                新建Quiz
              </el-button>
            </div>
          </template>
          <el-scrollbar height="250px">
            <div>
              <div>
                <el-table :data="quizTableData" style="width: 100%">
                  <el-table-column
                    prop="quiz_title"
                    label="Quiz Title"
                    width="250"
                  />
                  <el-table-column prop="quiz_date" label="Date" width="300" />
                  <el-table-column fixed="right" label="Operator" width="100">
                    <template #default="scope">
                      <el-button
                        size="small"
                        @click="goQuizDetail(scope.$index)"
                        >进入</el-button
                      >
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </div>
          </el-scrollbar>
        </el-card>
      </el-col>
    </el-row>
    <!-- Homework Score -->
    <div>
      <el-dialog v-model="scoreVisible" @close="handleScoreClose" title="评分">
        <el-form :model="scoreForm">
          <el-form-item label="Score">
            <el-input-number
              v-model="scoreForm.score"
              :min="0"
              :max="100"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item label="Commit">
            <el-input
              v-model="scoreForm.commit"
              :rows="2"
              type="textarea"
              placeholder="Please input"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button type="primary" @click="submitScoreForm"> 确定 </el-button>
        </template>
      </el-dialog>
    </div>

    <!-- Homework Check -->
    <div>
      <el-dialog
        v-model="homeworkCheckVisible"
        @close="handleHomeworkCheckClose"
        title="Homework Review"
        width="70%"
      >
        <el-scrollbar height="250px">
          <div>
            <div>
              <el-table :data="homeworkCheckTableData" style="width: 100%">
                <el-table-column prop="name" label="Name" width="250" />
                <el-table-column prop="date" label="Date" width="250" />
                <el-table-column prop="status" label="Status" width="250" />
                <el-table-column fixed="right" label="Operator" width="200">
                  <template #default="scope">
                    <span>
                      <el-button size="small" @click="downloadPDF(scope.$index)"
                        >下载</el-button
                      >
                      <el-button size="small" @click="onScoreHomework(scope.$index)"
                        >批改</el-button
                      >
                    </span>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </el-scrollbar>
      </el-dialog>
    </div>

    <!-- 新建Homework -->
    <div>
      <el-dialog
        v-model="dialogFormVisible_homework"
        title="添加Homework"
        width="57%"
      >
        <el-form :model="homeworkFormData">
          <el-form-item label="Title">
            <el-input v-model="homeworkFormData.title" autocomplete="off" />
          </el-form-item>
          <el-form-item label="Content">
            <el-input
              v-model="homeworkFormData.content"
              autocomplete="off"
              type="textarea"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="cancelHomeworkDialog">Cancel</el-button>
            <el-button type="primary" @click="submitHomeworkDialog">
              Confirm
            </el-button>
          </span>
        </template>
      </el-dialog>
    </div>
    <!-- 新建Quiz -->
    <div>
      <el-dialog v-model="dialogFormVisible_quiz" title="添加Quiz" width="57%">
        <el-form :model="quizFormData">
          <el-form-item label="Quiz Title">
            <el-input v-model="quizFormData.quiz_title" autocomplete="off" />
          </el-form-item>
          <el-form-item label="Quiz Time">
            <el-input-number v-model="quizFormData.quiz_time" :step="1" />
          </el-form-item>
          <el-form-item label="Type">
            <el-radio-group
              v-model="quizFormData.quiz_type"
              class="ml-4"
              @change="typeChange(quizFormData.quiz_type)"
            >
              <el-radio label="judge">Judge</el-radio>
              <el-radio label="select">Select</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-scrollbar height="500px">
            <div
              v-for="(item, index) in quizFormData.quiz_content"
              :key="index"
            >
              <el-card class="box-card">
                <el-form-item>
                  <h3>Question {{ index + 1 }}</h3>
                </el-form-item>
                <el-form-item
                  label="Question"
                  :prop="'quiz_content.' + index + '.question'"
                >
                  <el-input v-model="item.question" />
                </el-form-item>
                <div v-if="quizFormData.quiz_type === 'judge'">
                  <el-form-item label="Options">
                    <el-radio-group v-model="item.answer">
                      <el-radio label="1"> True </el-radio>
                      <el-radio label="2"> False </el-radio>
                    </el-radio-group>
                  </el-form-item>
                </div>
                <div v-if="quizFormData.quiz_type === 'select'">
                  <el-form-item label="Options">
                    <el-radio-group v-model="item.answer">
                      <el-radio label="1">
                        <el-input placeholder="A" v-model="item.content.opt1" />
                      </el-radio>
                      <el-radio label="2">
                        <el-input placeholder="B" v-model="item.content.opt2" />
                      </el-radio>
                      <el-radio label="3">
                        <el-input placeholder="C" v-model="item.content.opt3" />
                      </el-radio>
                      <el-radio label="4">
                        <el-input placeholder="D" v-model="item.content.opt4" />
                      </el-radio>
                    </el-radio-group>
                  </el-form-item>
                </div>
              </el-card>
            </div>
          </el-scrollbar>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="addQuestion(quizFormData.quiz_type)"
              >添加问题</el-button
            >
            <el-button @click="cancelQuizDialog">Cancel</el-button>
            <el-button type="primary" @click="submitQuizDialog">
              Confirm
            </el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>
