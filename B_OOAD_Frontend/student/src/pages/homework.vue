<template>
  <div class="title">作业列表</div>
  <el-card class="homeworkCard" shadow="never">
    <span class="tip">
            选择作业来进行提交:
    </span>
      <el-table :data="homeworkList" style="width: 100%; margin-bottom: 15px" height="120" stripe size="default" class="list">
        <el-table-column label="课程名" width="120">
          <template #default="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.courseName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="作业编号" width="120" prop="homeworkId" sortable>
          <template #default="scope">
            <span style="margin-left: 10px">{{ scope.row.homeworkId }}</span>
          </template>
        </el-table-column>
        <el-table-column label="作业标题" type="expand" width="130">
          <template #default="scope">
            <span style="margin-left: 10px">{{ scope.row.title }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="150">
          <template #default="scope">
            <span style="margin-left: 10px">{{ scope.row.submittedStatus }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" type="primary" @click="confirmSubmitHomework(scope.$index)">
              提交作业
            </el-button>
          </template>
        </el-table-column>
      </el-table>
  </el-card>
  <el-card class="homeworkCard" shadow="never">
    <span class="tip">
      查看已提交的作业:
    </span>
    <el-table :data="submittedHomeworkList" style="width: 100%; margin-bottom: 15px" height="120" stripe size="default" class="list">
      <el-table-column label="课程名" width="120">
        <template #default="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.courseName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="作业编号" width="120" prop="homeworkId" sortable>
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.homeworkId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="作业标题" type="expand" width="130">
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="150">
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.submittedStatus }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" type="primary" @click="viewHomework2(scope.$index)">
            查看作业
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <el-card class="homeworkCard" shadow="never">
    <span class="tip">
      查看已批改的作业:
    </span>
    <el-table :data="finishedHomeworkList" style="width: 100%" height="120" stripe size="default" class="list">
      <el-table-column label="课程名" width="120">
        <template #default="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.courseName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="作业编号" width="120" prop="homeworkId" sortable>
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.homeworkId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="作业标题" type="expand" width="130">
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="150">
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.submittedStatus }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" type="primary" @click="viewHomework(scope.$index)">
            查看作业
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import router from "../router/index.js";
import homeworkApi from "../api/homework.js";
import cookie from "js-cookie";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "homework",
  data(){
    return{
      homeworkList:[],
      submittedHomeworkList:[],
      finishedHomeworkList:[],
    }
  },
  methods:{
    confirmSubmitHomework(row){
      window.localStorage.setItem('currentHomework', JSON.stringify(this.homeworkList[row]))
      router.push('./homeworkSubmit')
    },
    viewHomework(row){
      window.localStorage.setItem('currentHomework', JSON.stringify(this.finishedHomeworkList[row]))
      router.push('./homeworkSubmit')
    },
    viewHomework2(row){
      window.localStorage.setItem('currentHomework', JSON.stringify(this.submittedHomeworkList[row]))
      router.push('./homeworkSubmit')
    },
    getUnfinishedHomework(userId){
      homeworkApi.getUnfinishedHomework(userId).then(response => {

        let work
        for(work in response.data){
          this.homeworkList[work] = {}
          this.homeworkList[work].courseId = response.data[work].course_id
          this.homeworkList[work].courseName = response.data[work].course_name
          this.homeworkList[work].homeworkId = response.data[work].index
          let content = JSON.parse(response.data[work].content)
          this.homeworkList[work].title = content.title
          this.homeworkList[work].content = content.content
          this.homeworkList[work].submittedStatus = '未开始'
          this.homeworkList[work].submittedUrl = ''
          this.homeworkList[work].submittedDate = 0
          this.homeworkList[work].score = 0
          this.homeworkList[work].comment = ''
        }
      })
    },
    getFinishedHomework(userId){
      homeworkApi.getFinishedHomework(userId).then(response => {
        console.log(response.data)
        let fWork
        for(fWork in response.data){
          this.finishedHomeworkList[fWork] = {}
          this.finishedHomeworkList[fWork].courseId = response.data[fWork].course_id
          this.finishedHomeworkList[fWork].courseName = response.data[fWork].course_name
          this.finishedHomeworkList[fWork].homeworkId = response.data[fWork].homework_index
          let content2 = JSON.parse(response.data[fWork].homework_content)
          this.finishedHomeworkList[fWork].title = content2.title
          this.finishedHomeworkList[fWork].content = content2.content
          this.finishedHomeworkList[fWork].submittedDate = ''
          this.finishedHomeworkList[fWork].submittedUrl = response.data[fWork].url
          this.finishedHomeworkList[fWork].submittedStatus = "已批改"
          this.finishedHomeworkList[fWork].score = response.data[fWork].homework_score
          this.finishedHomeworkList[fWork].comment = response.data[fWork].homework_comment
        }
      })
    },
    getSubmittedHomework(userId){
      homeworkApi.getSubmittedHomework(userId).then(response => {

        let work
        for(work in response.data){
          this.submittedHomeworkList[work] = {}
          this.submittedHomeworkList[work].courseId = response.data[work].course_id
          this.submittedHomeworkList[work].courseName = response.data[work].course_name
          this.submittedHomeworkList[work].homeworkId = response.data[work].submit_index
          this.submittedHomeworkList[work].title = ''
          this.submittedHomeworkList[work].content = ''
          this.submittedHomeworkList[work].submittedStatus = '已提交'
          this.submittedHomeworkList[work].submittedUrl = response.data[work].pic_url
          this.submittedHomeworkList[work].submittedDate = response.data[work].submit_time
          this.submittedHomeworkList[work].score = 0
          this.submittedHomeworkList[work].comment = ''
        }
      })
    },
  },
  mounted() {
    let userId = cookie.get('user_id')
    this.getUnfinishedHomework(userId)
    this.getSubmittedHomework(userId)
    this.getFinishedHomework(userId)
  }
}
</script>

<style scoped>

.title{
  margin-top: 10px;
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

.homeworkCard{
  margin-bottom: 12px;
  height: 205px;
}

</style>