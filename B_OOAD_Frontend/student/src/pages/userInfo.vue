<template>
    <el-descriptions title="用户个人信息" :column="2" border size="default">
      <el-descriptions-item>
        <template #label>
          <el-icon><Picture /></el-icon>
          用户头像
        </template>
        <el-image class="userImg2" src="../../img/ava.png">
        </el-image>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon><User /></el-icon>
          用户名
        </template>
        <div class="text item">{{this.userInfo.name }}</div>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon><UserFilled /></el-icon>
          用户id
        </template>
        <div class="text item">{{this.userInfo.id }}</div>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon><Message /></el-icon>
          用户邮箱
        </template>
        <div class="text item">{{this.userInfo.email }}</div>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon><OfficeBuilding /></el-icon>
          用户身份
        </template>
        <div class="text item">{{this.userInfo.role }}</div>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon><Money /></el-icon>
          用户余额
        </template>
        <div class="text item">{{this.userInfo.gold + " SKC"}}</div>
      </el-descriptions-item>
    </el-descriptions>

  <el-card style="margin-top: 25px; height: 435px" shadow="never">
    <h3 class="courseTitle">
      <span>我的课程</span>
    </h3>
    <el-divider />
    <el-table :data="courseList" style="width: 100%" stripe size="default" class="list">
      <el-table-column label="课程名" width="120">
        <template #default="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.courseTitle }}</span>
        </template>
      </el-table-column>
      <el-table-column label="课程描述" type="expand" width="240">
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.courseDescription }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任课老师编号" width="150">
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.teacherName }}</span>
        </template>
      </el-table-column>
    
    </el-table>
  </el-card>
</template>

<script>
import cookie from 'js-cookie';
import userApi from "/src/api/user"
export default {
  data(){
    return{
      userInfo: {
        name: 'stuNotLogin',
        id: '-1',
        email: '',
        role: 'student',
        gold: 0,
      },
      userImgUrl: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      courseList:[
      ],
    }
  },
  name: "userInfo",
  methods:{
    goBack(){
      window.location.href = "/";
    },
    getUserInfo(userId){
      userApi.getUserInfo(userId).then((response) => {
        console.log(response.data)
        this.userInfo.name = response.data.user.user_name
        this.userInfo.id = response.data.user.user_id
        this.userInfo.email = response.data.user.user_email
        this.userInfo.gold = response.data.user.user_gold
        let course 
        for(course in response.data.courseList){
          this.courseList[course] = {}
          this.courseList[course].courseId = response.data.courseList[course].course_id
          this.courseList[course].courseTitle = response.data.courseList[course].course_title
          this.courseList[course].courseDescription = response.data.courseList[course].course_description
          this.courseList[course].teacherName = response.data.courseList[course].teacher_id
        }
      })
    },
    viewCourse(course_id){

    }
  },
  mounted() {
    let userId = cookie.get('user_id')
    console.log("用户id",userId);
    this.getUserInfo(userId)
  }
}
</script>

<style scoped>

.courseTitle{
  margin-left: 10px;
}

.userImg2{
  width: 60px;
  height: 60px;
  background-color: #8c939d;
  margin-right: 24px;
  margin-left: 20px;
  overflow: hidden;
  border-radius: 50%;
}

.userInfo{
  margin-top: 25px;
  margin-bottom: 25px;
  width: 100%;
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

</style>