<template>
  <el-page-header title="返回" @back="goBack" content="作业提交" class="page-header"> </el-page-header>
    <div class="tip">
      确认作业提交
    </div>
    <div class="homeworkRow" style="font-weight: bold">
      请确认您的作业提交信息:
    </div>
    <div class="homeworkRow">
      <span class="homeworkTitle">课程名: </span>
      <span>{{this.currentHomework.courseName}}</span>
    </div>
    <div class="homeworkRow">
      <span class="homeworkTitle">作业编号: </span>
      <span>{{this.currentHomework.homeworkId}}</span>
    </div>
    <div class="homeworkRow" v-if="this.currentHomework.submittedStatus !== '已提交'">
      <span class="homeworkTitle">作业标题: </span>
      <span>{{this.currentHomework.title}}</span>
    </div>
    <div class="homeworkRow" v-if="this.currentHomework.submittedStatus !== '已提交'">
      <div style="margin-bottom: 10px" class="homeworkTitle">作业内容: </div>
      <span>{{this.currentHomework.content}} </span>
    </div>
    <div class="homeworkRow">
      <span class="homeworkTitle">当前状态: </span>
      <span style="color:#b01175" v-if="this.currentHomework.submittedStatus === '未开始'">{{this.currentHomework.submittedStatus}} </span>
      <span style="color:#51b011" v-if="this.currentHomework.submittedStatus !== '未开始'">{{this.currentHomework.submittedStatus}} </span>
    </div>
    <div class="homeworkRow" v-if="this.currentHomework.submittedStatus !== '未开始'">
      <span class="homeworkTitle">提交作业: </span>
      <a :href=this.currentHomework.submittedUrl target="_blank">查看已提交的作业 </a>
    </div>
    <div class="homeworkRow" v-if="this.currentHomework.submittedStatus === '已批改'">
      <span class="homeworkTitle">已有成绩: </span>
      <span style="color:#b01175">{{this.currentHomework.score}} </span>
    </div>
    <div class="homeworkRow" v-if="this.currentHomework.submittedStatus === '已批改'">
      <div style="margin-bottom: 10px" class="homeworkTitle">反馈评价: </div>
      <span >{{this.currentHomework.comment}} </span>
    </div>
    <el-upload
        class="upload"
        ref="upload"
        :action=homeworkUrl
        accept=".pdf"
        :file-list="fileList"
        limit="1"
        :on-success="setReceivedUrl"
        v-if="this.currentHomework.submittedStatus === '未开始'"
    >
      <template #trigger>
        <el-button size="default" type="primary">选择文件</el-button>
      </template>
      <el-button
          style="margin-left: 10px;"
          size="default"
          type="success"
          @click="submitHomeworkInfo"

      >提交作业</el-button
      >
    </el-upload>
</template>

<script>

import router from "../router/index.js";
import homeworkApi from "../api/homework.js";
import cookie from "js-cookie";
export default {
  name: "homeworkSubmit",
  data(){
    return{
      currentHomework:{
        courseId:1,
        courseName:'CS301',
        homeworkId:1,
        content:'这是作业1的内容',
        submittedStatus:'未开始',
        submittedUrl:'',
        submittedDate:new Date(1544942468922),
        score:0,
        comment:'这是作业1的评价',
      },
      fileList:[],
      homeworkUrl:"http://localhost:8075/upload/file",
      receivedId:"",
      receivedUrl:"",
    }
  },
  methods:{
    goBack(){
      router.push('./homework')
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
    setReceivedUrl(response){
      homeworkApi.obtainFileUrl(response).then((response2) => {
        this.receivedUrl = response2.data
      })
    },
    submitHomeworkInfo(){
      if(this.receivedUrl === ""){
        return;
      }
      let homeworkInfo = {}
      homeworkInfo['user_id'] = cookie.get('user_id')
      homeworkInfo['homework_index'] = this.currentHomework.homeworkId
      homeworkInfo['course_id'] = this.currentHomework.courseId
      homeworkInfo['pdf_url'] = this.receivedUrl
      homeworkApi.submitHomeworkInfo(homeworkInfo).then(response => {
        console.log(response.data)
      })
      router.push("./homework")
    }
  },
  created() {
    this.currentHomework =  JSON.parse(window.localStorage.getItem('currentHomework'))
  }
}
</script>

<style scoped>

.page-header{
  margin-bottom: 20px;
  margin-top: 25px;
}

.tip{
  margin-left: 20px;
}

.homeworkRow{
  margin-left: 20px;
  margin-top: 20px;
}

.upload{
  margin-top: 20px;
  margin-left: 20px;
}

.homeworkTitle{
  font-weight: bold;
  color: #5fc6ff;
}

</style>