<template>
  <el-page-header title="返回" @back="goBack" content="进行测试" class="page-header"> </el-page-header>
  <el-card class="quizCard" shadow="never">
    <template #header>
      <span class="tip">{{currentQuiz.quiz_title}}</span>
      <span class="time">距离考试结束还有{{minutes}}分{{seconds}}秒</span>
    </template>
    <el-scrollbar height="480px">
      <ul v-for="question in currentQuiz.quiz_content" :key="question.question" style="list-style: none">
      <li>
        <el-card shadow="never" class="questionCard">
          <template #header>
            <span>{{question.question}}</span>
          </template>
          <div>
            <el-radio-group v-model="answerList[question.question]">
              <el-radio v-for="option in question.content" :key="option" :label="option" size="large">{{option}}</el-radio>
            </el-radio-group>
          </div>
        </el-card>
      </li>
    </ul>
    </el-scrollbar>
    <el-button class="submitButton" size="default" type="primary" @click="submitQuiz">
      提交试卷
    </el-button>
  </el-card>

  <el-dialog
      title="提交试卷"
      v-model="quizzingDialogVisible"
      width="50%"
      :before-close="clearScore"
  >
    <div style="font-size: 15px; line-height: 20px">提交成功！</div>
    <div style="font-size: 15px; line-height: 20px">您的成绩为: {{this.score}}</div>
  </el-dialog>
</template>

<script>
import {onMounted} from "vue";
import router from "../router/index.js";
import quizApi from "../api/quiz.js"
import cookie from 'js-cookie';
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "quizzing",
  data(){
    return{
      answerList: {
      },
      currentQuiz:{"quiz_title":"quiz test 1",
        "quiz_time":1,
        "quiz_type":"judge",
        "quiz_content":
            [{"question":"question 1: 我的答案是a",
              "answer":"opt1",
              "content": {"opt1":"a","opt2":"b"}},
              {"question":"question 2: 我的答案是bb",
                "answer":"opt2",
                "content": {"opt1":"aa","opt2":"bb"}}]},
      hours: 0,
      seconds: 0,
      minutes: 0,
      timer: null,
      quizzingDialogVisible: false,
      score: 0,
    }
  },
  methods:{
    goBack(){
      router.push("/home/quiz")
    },
    timing(){
      this.timer = setInterval(() => {
        if (this.seconds === 0 && this.minutes>0) {
          this.seconds = 59;
          this.minutes--;
        } else if (this.minutes === 0 && this.hours>0) {
          this.minutes=59;
          this.hours--;
        } else if (this.minutes === 0 && this.seconds === 0 && this.hours === 0) {
          this.seconds = 0;
          this.submitQuiz()
          clearInterval(this.timer);
        } else {
          this.seconds--;
        }
      }, 1000);
    },
    submitQuiz(){
      clearInterval(this.timer);
      let answer
      let question
      for(answer in this.answerList){
        for(question in this.currentQuiz.quiz_content){
          if(answer === this.currentQuiz.quiz_content[question]["question"]
              && this.answerList[answer] === this.currentQuiz.quiz_content[question]["content"][this.currentQuiz.quiz_content[question]["answer"]]){
            this.score += 10
          }
        }
      }
      let quizInfo = {}
      quizInfo['user_id'] = cookie.get('user_id')
      quizInfo['quiz_index'] = cookie.get('quizId')
      quizInfo['course_id'] = cookie.get('courseId')
      quizInfo['quiz_score'] = this.score
      quizApi.updateQuiz(quizInfo).then(response => (
          console.log(response)
      ))
      this.quizzingDialogVisible = true
    },
    clearScore(done){
      this.score = 0
      done()
      router.push("/home/quiz")
    }
  },
  created() {
    this.currentQuiz = JSON.parse(window.localStorage.getItem("currentQuizContent"))
    console.log("title:" + this.currentQuiz.quiz_title)
    this.minutes = this.currentQuiz.quiz_time;
    this.seconds = 0;
    this.hours = 0;
    let question
    console.log(JSON.stringify(this.currentQuiz.quiz_content[0]))
    for(question in this.currentQuiz.quiz_content){
      //this.answerList[this.currentQuiz.quiz_content[question].question] = ""
    }
    onMounted(() => {
      this.timing();
    })
  },


}
</script>

<style scoped>

.page-header{
  margin-bottom: 25px;
  margin-top: 25px;
}

.tip{
  margin-left: 20px;
}

.time{
  margin-left: 400px;
}

.submitButton{
  margin-left: 10px;
}

.questionCard{
  width: 660px;
  margin-bottom: 20px;
}

.quizCard{
  margin-left: 35px;
  width: 750px;
  height: 625px;
}

</style>