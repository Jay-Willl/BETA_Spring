<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { noticeGetAPI, noticeSubmitAPI } from "@/api/notice";
import { useRouter } from "vue-router";
import { message } from "@/utils/message";
import moment from "moment";

defineOptions({
  name: "Notice"
});

const router = useRouter();

onMounted(() => {
  getNoticeData();
});

const noticeGetForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course")
});

const noticeData = ref([]);
const noticeTableData = ref([]);

interface noticeGetType {
  notice_title: string;
  notice_content: string;
  notice_date: Date;
  notice_type: string;
}

const getNoticeData = async () => {
  try {
    console.log("noticeGetForm", noticeGetForm);
    await noticeGetAPI(noticeGetForm).then(res => {
      console.log("response noticeGetAPI", res);
      noticeData.value = res.data;
      let i: number;
      for (i = 0; i < noticeData.value.length; i++) {
        console.log(i, noticeData.value[i]);
        const s = JSON.parse(
          noticeData.value[i].notice_content.replace(new RegExp("/", ""), "")
        );
        console.log("s", s);
        noticeTableData.value.push({
          notice_title: s.title,
          notice_content: s.content,
          notice_date: moment(new Date(noticeData.value[i].notice_time)).format(
            "YYYY-MM-DD"
          ),
          notice_type: noticeData.value[i].notice_email ? "email" : "system"
        });
      }
      console.log("noticeTableData", noticeTableData.value);
    });
  } catch (e) {
    console.log(e);
  }
};

const dialogFormVisible = ref(false);
const formLabelWidth = "140px";

const noticeForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course"),
  useEmail: "false",
  data: ""
});

const noticeFormData = reactive({
  title: "",
  content: ""
});

const cancelNoticeDialog = () => {
  noticeForm.useEmail = "false";
  noticeForm.data = "";
  dialogFormVisible.value = false;
};
const submitNoticeDialog = async () => {
  console.log("notice data", noticeFormData);
  noticeForm.data = JSON.stringify(noticeFormData);
  console.log("submit noticeForm", noticeForm);

  await noticeSubmitAPI(noticeForm).then(res => {
    console.log("notice submit response", res.data);
    if (res.data === "ok") {
      message("提交成功", { type: "success" });
    } else {
      message(res.data, { type: "error" });
    }
  });
  dialogFormVisible.value = false;
  router.go(0);
  noticeForm.data = "";
};

const checked = ref(false);
const onChange = (status: boolean) => {
  checked.value = status;
  status ? (noticeForm.useEmail = "true") : (noticeForm.useEmail = "false");
};
</script>

<template>
  <div>
    <h1>Notice</h1>
    <el-table
      :data="noticeTableData"
      style="width: 100%"
      max-height="500"
      size="large"
    >
      <el-table-column fixed prop="notice_title" label="Title" width="300" />
      <el-table-column prop="notice_date" label="Date" width="300" />
      <el-table-column prop="notice_type" label="Type" width="300" />
      <el-table-column prop="notice_content" label="Content" width="500" />
    </el-table>
    <el-button
      class="mt-4"
      style="width: 100%"
      @click="dialogFormVisible = true"
    >
      新建通知
    </el-button>
    <!-- 新建通知 -->
    <div>
      <el-dialog v-model="dialogFormVisible" title="新建通知">
        <el-form :model="noticeFormData">
          <el-form-item label="通知标题" :label-width="formLabelWidth">
            <el-input v-model="noticeFormData.title" autocomplete="off" />
          </el-form-item>
          <el-form-item label="通知方式" :label-width="formLabelWidth">
            <el-check-tag :checked="checked" @change="onChange"
              >Use Email</el-check-tag
            >
          </el-form-item>
          <el-form-item label="通知内容" :label-width="formLabelWidth">
            <el-input
              v-model="noticeFormData.content"
              :autosize="{ minRows: 5, maxRows: 8 }"
              type="textarea"
              placeholder="Please input"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="cancelNoticeDialog">Cancel</el-button>
            <el-button type="primary" @click="submitNoticeDialog">
              Confirm
            </el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>
