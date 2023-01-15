<template>
     <el-backtop :bottom="100">
    <div
      style="
        height: 100%;
        width: 100%;
        background-color: var(--el-bg-color-overlay);
        box-shadow: var(--el-box-shadow-lighter);
        text-align: center;
        line-height: 40px;
        color: #1989fa;
      "
    >
      Top
    </div>
  </el-backtop>
  <el-container>
   <el-header>
      <section class="container">
        <a href="#" title="saki" id="logo">
          <img src="../assets/img/logo.jpg" alt="saki" />
        </a>
        <div class="linkbox">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a class="c666 top">首页</a>
            </router-link>
            <router-link to="/course" tag="li" active-class="current">
              <a class="c666 top">课程</a>
            </router-link>
          </ul>
        </div>

        <div class="search-box">
          <el-input
            placeholder="请输入内容"
            v-model="searchContent"
            class="searchClass"
          >
            <template #append>
              <el-button
                icon="search"
                style="border-color: #00000029; background-color: white"
                @click="searchCourse()"
              />
            </template>
          </el-input>
        </div>

        <router-link to="/login" v-if="!islogin" active-class="current">
          <el-button class="login" type="primary" round> 注册/登录 </el-button>
        </router-link>
        <router-link v-if="islogin" to="/home/quiz" active-class="current">
          <a class="c666 topright">测验</a>
        </router-link>
        <router-link v-if="islogin" to="/home/homework" active-class="current">
          <a class="c666 topright2">作业</a>
        </router-link>

        <router-link
          v-if="islogin"
          to="/home"
          active-class="current"
          id="userdiv"
        >
          <el-popover
            :width="80"
            popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
          >
            <template #reference>
              <el-avatar src="../../img/ava.png" />
            </template>
            <template #default>
              <div
                class="demo-rich-conent"
                style="display: flex; gap: 16px; flex-direction: column"
              >
                <div>
                  <router-link v-if="islogin" to="/home" active-class="current">
                    <el-button plain round style="margin-bottom : 20px">个人主页</el-button>
                  </router-link>
                    <el-button type="danger" @click="logout()" round>退出登陆</el-button>
                </div>
              </div>
            </template>
          </el-popover>
        </router-link>
      </section>
    </el-header>
  </el-container>
  <div class="back">
    <div class="userInfoContainer">
      <el-row class="tac">
        <el-col :span=5>
          <el-card class="userInfoCard">
          <h5>
            <el-image class="userImg" src="../../img/ava.png">
            </el-image>
          </h5>
          <span class="userText">
              你好,学生 {{user_name}}
            </span>
          <el-menu default-active='/home/userinfo' router active-text-color="#00c3ff">
            <el-menu-item index='/home/userinfo'>
              <el-icon><User /></el-icon>
              <span class="tips">用户信息</span>
            </el-menu-item>
            <el-menu-item index="/home/quiz">
              <el-icon><Edit /></el-icon>
              <span class="tips">测试</span>
            </el-menu-item>
            <el-menu-item index="/home/homework">
              <el-icon><Document /></el-icon>
              <span class="tips">作业</span>
            </el-menu-item>
            <el-menu-item index="/home/announcement">
              <el-icon><BellFilled /></el-icon>
              <span class="tips">通知</span>
            </el-menu-item>
            <el-menu-item index="/home/payment">
              <el-icon><Money /></el-icon>
              <span class="tips">充值</span>
            </el-menu-item>
          </el-menu>
          </el-card>
        </el-col>
        <el-col :span=1 class="gutter">
        </el-col>
        <el-col :span=18>
          <el-card class="userInfoCard">
            <router-view ></router-view>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import cookie from "js-cookie";

export default {
   data(){
    return{
      activeName: 'userInfo',
      userImgUrl: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      quizList:[],
      islogin: false,
      searchContent: "",
      user_name:"",
    }
  },
  mounted(){
    this.setData();
  },
    methods :{
      searchCourse() {
      console.log("searchM" + this.searchContent);
      this.$router.push({
        path: `/course`,
        query:{
          content : this.searchContent,
        },
      })
    },
      setData() {
        if (cookie.get("user_token") != null) {
          this.islogin = true;
          console.log("login");
        } else {
          this.islogin = false;
          console.log("no login");
        }
        //console.log("课程id: " + this.$route.params.courseid);
        console.log("用户token: " + cookie.get("user_token"));
        this.user_name = cookie.get("user_name");
        //this.courseId = this.$route.params.courseid;
      },
      logout() {
    

        cookie.remove("user_token");
        cookie.remove("user_id");
        cookie.remove("user_name");
        cookie.remove("user_gold");
        localStorage.removeItem("Authorization");
        console.log("退出");
        this.$router.push("/");

      },
    }
}
</script>

<style scoped>
/* header */
.login {
  margin-top: 15px;
  float: right;
}
.container {
    margin-left: auto;
    margin-right: auto;
    width: 1160px;
}
.back{
  background-color: #f5f5f5;
  height: 800px;
}


.userInfoContainer{
  margin-left: auto;
  margin-right: auto;
  width: 1160px;
}
.linkbox {
  height: 50px;
  display: inline-block;
}

.search-box {
  display: inline-block;

  margin: 0;
  width: 300px;
}

#header {
  background-color: #fff;
  box-shadow: 0 2px 3px rgb(7 0 2 / 4%);
  position: relative;
}
.c666{
   color: #666;
}
.top {
  border-bottom: 2px solid transparent;

  display: inline-block;
  font-size: 20px;
  line-height: 50px;
  padding: 0 10px;
  margin: -10px 10px 0 10px;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.cblue{
  color: #143bba;
}
#userdiv{
  float: right!important;
  height: 40px;
  width: 40px;
  margin-top: 10px;
}
.topright{
   border-bottom: 2px solid transparent;

  display: inline-block;
  font-size: 20px;
  line-height: 50px;
  padding: 0 10px;
  margin-left: 160px;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.topright2{
   border-bottom: 2px solid transparent;

  display: inline-block;
  font-size: 20px;
  line-height: 50px;
  padding: 0 10px;
  margin-left: 10px;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.nav {
  width: 300px;
  padding: 15px 0 0 50px;
  display: inline-block;
}

#logo {
  float: left !important;
  width: 200px;
  height: 50px;

  overflow: hidden;
  margin: 0 auto;
}
.el-header {
  background: white;
  height: 55px;
  background-color: #fff;
  box-shadow: 0 2px 3px rgb(7 0 2 / 4%);
  position: relative;
}
.el-aside {
  background: #ff9569;
}
.content {
  height: calc(100vh - 90px);
}
/* header */
.userImg{
  width: 110px;
  height: 110px;
  background-color: #8c939d;
  margin: 25px 24px 25px 25px;
  overflow: hidden;
  border-radius: 50%;
}

.userText{
  width: 60%;
  height: 100%;
  line-height: 30px;
  font-size: 15px;
  margin-left: 30px;
  font-weight: bold;
}

.tips {
  font-size:18px;
  margin: 15px 15px 15px 15px;
  text-align: center;
}

.userInfoCard{
  height: 750px;
  margin-top: 20px;
}

.el-menu {
  border-right: 0px;
  list-style: none;
  position: relative;
  margin: 0;
  padding-left: 0;
  background-color: var(--el-menu-bg-color);
  box-sizing: border-box;
}
</style>