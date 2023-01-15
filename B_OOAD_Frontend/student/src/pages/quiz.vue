<template>
  <div class="title">测试列表</div>
  <el-card class="quizCard" shadow="never">
    <span class="tip">
      选择要参加的测试：
    </span>
    <el-table :data="quizList" style="width: 100%; margin-bottom: 25px" height="225" stripe size="default" class="list">
      <el-table-column label="课程名" width="180">
        <template #default="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.courseName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="测试编号" width="180" prop="quizId" sortable>
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.quizId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" type="primary" @click="confirmQuiz(scope.$index)">
            进行测试
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <el-card class="quizCard" shadow="never">
    <span class="tip">
      查看已参加的测试：
    </span>
    <el-table :data="finishedQuizList" style="width: 100%" stripe size="default" height="225" class="list">
      <el-table-column label="课程名" width="180">
        <template #default="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.courseName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="测试编号" prop="quizId" width="180" sortable>
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.quizId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="测试成绩" prop="quizScore" width="180" sortable>
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.quizScore }}</span>
        </template>
      </el-table-column>
      <el-table-column label="提交日期">
        <template #default="scope">
          <span style="margin-left: 10px">{{formatDate(scope.row.quizDate)}}</span>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import quizApi from "../api/quiz.js"
import router from "../router/index.js";
import cookie from "js-cookie";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "quiz",
  data(){
    return{
      quizList:[

      ],
      finishedQuizList:[

      ],
    }
  },
  methods:{
    confirmQuiz(row){
      window.localStorage.setItem('currentQuizContent',JSON.stringify(this.quizList[row].quizContent))
      cookie.set("quizId",this.quizList[row].quizId)
      cookie.set("courseId",this.quizList[row].courseId)
      console.log(JSON.stringify(this.quizList[row].quizContent))
      router.push("/home/quizzing")
    },
    formatDate(value) {
      let date = new Date(value);
      let y = date.getFullYear();
      let MM = date.getMonth() + 1;
      MM = MM < 10 ? ('0' + MM) : MM;
      let d = date.getDate();
      d = d < 10 ? ('0' + d) : d;
      let h = date.getHours();
      h = h < 10 ? ('0' + h) : h;
      let m = date.getMinutes();
      m = m < 10 ? ('0' + m) : m;
      let s = date.getSeconds();
      s = s < 10 ? ('0' + s) : s;
      return y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s;
    },
    initQuiz(userId){
      quizApi.getUnfinishedQuiz(userId).then(response => {
        let quiz
        for(quiz in response.data){
          this.quizList[quiz] = {}
          this.quizList[quiz].courseId = response.data[quiz].course_id
          this.quizList[quiz].courseName = response.data[quiz].course_name
          this.quizList[quiz].quizId = response.data[quiz].index
          this.quizList[quiz].quizContent = {}
          let content1 = JSON.parse(response.data[quiz].content)
          this.quizList[quiz].quizContent.quiz_title = content1.quiz_title
          this.quizList[quiz].quizContent.quiz_time = content1.quiz_time
          this.quizList[quiz].quizContent.quiz_type = content1.quiz_type
          this.quizList[quiz].quizContent.quiz_content = []
          let question
          for (question in content1.quiz_content){
            this.quizList[quiz].quizContent.quiz_content[question] = {}
            this.quizList[quiz].quizContent.quiz_content[question].question = content1.quiz_content[question].question
            console.log(this.quizList[quiz].quizContent.quiz_content[question].question)
            this.quizList[quiz].quizContent.quiz_content[question].answer = "opt" + content1.quiz_content[question].answer
            this.quizList[quiz].quizContent.quiz_content[question].content = {}
            let option
            for (option in content1.quiz_content[question].content){
              this.quizList[quiz].quizContent.quiz_content[question].content[option] = content1.quiz_content[question].content[option]
            }
          }
        }
      })
      quizApi.getFinishedQuiz(userId).then(response => {
        let quiz
        for(quiz in response.data){
          this.finishedQuizList[quiz] = {}
          this.finishedQuizList[quiz].courseId = response.data[quiz].course_id
          this.finishedQuizList[quiz].courseName = response.data[quiz].course_name
          this.finishedQuizList[quiz].quizId = response.data[quiz].quiz_index
          this.finishedQuizList[quiz].quizScore = response.data[quiz].grade_score
          this.finishedQuizList[quiz].quizDate = response.data[quiz].grade_time
        }
      })

    }
  },
  mounted() {
    let userId = cookie.get('user_id')
    this.initQuiz(userId)
  }
}
</script>

<style scoped>

.title{
  margin-top: 25px;
  margin-left: 20px;
  margin-bottom: 25px;
  font-size: 20px;
}

.tip{
  margin-left: 20px;
}

.list{
  margin-top: 18px;
}

.quizCard{
  margin-bottom: 20px;
  height: 295px;
}

</style>