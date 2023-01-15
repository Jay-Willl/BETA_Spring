<template>
  <div class="title">通知</div>
  <span class="tip">
    查看通知：
  </span>
  <el-table :data="announcementList" style="width: 90%" height="550" stripe size="default" class="list">
    <el-table-column label="课程名" width="120">
      <template #default="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.courseName }}</span>
      </template>
    </el-table-column>
    <el-table-column label="通知编号" width="120" prop="noticeId" sortable>
      <template #default="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.noticeId }}</span>
      </template>
    </el-table-column>
    <el-table-column label="通知标题" width="145" type="expand">
      <template #default="scope">
        <span style="margin-left: 10px">{{ scope.row.title }}</span>
      </template>
    </el-table-column>
    <el-table-column label="通知时间" width="220">
      <template #default="scope">
        <span style="margin-left: 10px">{{formatDate(scope.row.date)}}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" type="primary" @click="confirmAnnouncement(scope.$index)">
          查看通知
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog
      title="查看通知"
      v-model="announcementDialogVisible"
      width="50%"
  >
    <div class="text item">
      <span class="homeworkTitle">课程名: </span>
      {{this.currentAnnouncement.courseName }}
    </div>
    <div class="text item">
      <span class="homeworkTitle">通知编号: </span>
      {{this.currentAnnouncement.noticeId }}
    </div>
    <div class="text item">
      <span class="homeworkTitle">通知标题: </span>
      {{this.currentAnnouncement.title }}
    </div>
    <div class="text item">
      <div class="homeworkTitle" style="margin-bottom: 10px">通知内容: </div>
      {{this.currentAnnouncement.content }}
    </div>
    <div class="text item">
      <span class="homeworkTitle">通知时间: </span>
      {{formatDate(this.currentAnnouncement.date)}}
    </div>
  </el-dialog>
</template>

<script>
import noticeApi from "../api/notice.js"
import cookie from "js-cookie";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "announcement",
  data(){
    return{
      announcementList:[],
      currentAnnouncement:{
        courseId:1,
        courseName:'CS301',
        noticeId:1,
        title:'',
        content:'这是通知1的内容',
        date:1544942468922,
      },
      announcementDialogVisible: false,
    }
  },
  methods:{
    goBack(){
      window.location.href = "/#/";
    },
    confirmAnnouncement(row){
      this.currentAnnouncement = this.announcementList[row];
      this.announcementDialogVisible = true;
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
    getNotice(userId){
      noticeApi.getNotice(userId).then(response => {
        console.log(response.data)
        let res
        for(res in response.data){
          this.announcementList[res] = {}
          this.announcementList[res].courseId = response.data[res].course_id
          this.announcementList[res].courseName = response.data[res].course_name
          this.announcementList[res].noticeId = response.data[res].notice_id
          let content = JSON.parse(response.data[res].notice_content)
          this.announcementList[res].title = content.title
          this.announcementList[res].content = content.content
          this.announcementList[res].date = response.data[res].notice_time
        }
      })
    }
  },
  mounted() {
    let userId = cookie.get("user_id")
    this.getNotice(userId)
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

.text {
  font-size: 14px;
  text-align: left;
}

.item {
  margin-bottom: 10px;
  margin-top: 10px;
}

.homeworkTitle{
  font-weight: bold;
  color: #5fc6ff;
}
</style>