<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { chapterGradeGetAPI } from "@/api/grade";
import FileSaver from "file-saver";
import XLSX from "xlsx";

defineOptions({
  name: "Grade"
});

const dataLoading = ref(true);

onMounted(() => {
  getChapterGradeData();
});

const chapterGradeGetForm = reactive({
  username: localStorage.getItem("username"),
  token: localStorage.getItem("token"),
  course: localStorage.getItem("course")
});

const gradeData = ref();

const tableHeader = ref({
  name: "Name"
});
const tableData = ref([]);

const getChapterGradeData = async () => {
  try {
    console.log("chapterGradeGetForm", chapterGradeGetForm);
    await chapterGradeGetAPI(chapterGradeGetForm).then(res => {
      console.log("response chapterGradeGetAPI", res);
      gradeData.value = res.data;
    });
    let i: number;
    let j: number;
    console.log("gradeData", gradeData.value);
    console.log(gradeData.value[0].grade.length);
    const s = JSON.parse(
          gradeData.value[0].grade.replace(new RegExp("/", ""), "")
        );
    console.log("s", s);
    for (i = 0; i < s.length; i++) {
      console.log(i);
      console.log("index",s[i].index);
      tableHeader.value[s[i].index] =
        "Chapter" + s[i].index;
    }
    console.log("tableHeader", tableHeader.value);
    for (i = 0; i < gradeData.value.length; i++) {
      const gradeItem = {
        name: gradeData.value[i].name
      };
      const s1 = JSON.parse(
          gradeData.value[i].grade.replace(new RegExp("/", ""), "")
        );
      for (j = 0; j < s1.length; j++) {
        gradeItem[s1[j].index] =
          s1[j].score;
      }
      tableData.value.push(gradeItem);
    }
    console.log("tableData final", tableData.value);
  } catch (e) {
    console.log(e);
  } finally {
    setTimeout(() => {
      dataLoading.value = false;
    }, 500);
  }
};

const excelExport = () => {
  const wb = XLSX.utils.table_to_book(document.querySelector("#out-table"));

  const wbout = XLSX.write(wb, {
    bookType: "xlsx",
    bookSST: true,
    type: "array"
  });

  try {
    FileSaver.saveAs(
      new Blob([wbout], { type: "application/octet-stream" }),
      "成绩.xlsx")
  } catch (e) {
    if (typeof console !== "undefined") console.log(e, wbout);
  }
  return wbout;
};
</script>

<template>
  <div>
    <div>
      <el-card class="box-card">
        <div>
          <h2>Student Grade</h2>
          <el-button class="searchBtn" @click="excelExport()" type="primary">导出表格</el-button>
          <el-table :data="tableData" style="width: 100%" :fit="true" id="out-table">
            <el-table-column
              :prop="index"
              :label="item"
              v-for="(item, index) in tableHeader"
              :key="index"
            />
          </el-table>
        </div>
      </el-card>
    </div>
  </div>
</template>
