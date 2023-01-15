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
              <a class="cblue top">首页</a>
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
                    <el-button plain round style="margin-bottom: 20px"
                      >个人主页</el-button
                    >
                  </router-link>
                  <el-button type="danger" @click="logout()" round
                    >退出登陆</el-button
                  >
                </div>
              </div>
            </template>
          </el-popover>
        </router-link>
      </section>
    </el-header>
  </el-container>
  <div>
    <div class="block">
      <el-carousel
        interval="4000"
        trigger="click"
        height="400px"
        style="margin-top: 15px"
      >
        <el-carousel-item v-for="item in bannerList" :key="item.course_id">
          <router-link :to="'/course/' + item.course_id">
            <el-image 
            fit="cover"
            style="height: 400px; width: 1160px"
            :src="item.course_cover" />
          </router-link>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div id="aCoursesList">
      <!-- 网校课程 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门课程</span>
            </h2>
          </header>
          <div>
            <article class="comm-course-list">
              <ul class="of" id="bna">
                <li v-for="item in coureselist" :key="item.course_id">
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <router-link :to="'/course/' + item.course_id">
                        <el-image
                          fit="cover"
                          style="height: 150px; width: 268px"
                          :src="item.course_cover"
                          class="img-responsive"
                          :alt="item.course_title"
                        />
                      </router-link>
                    </section>
                    <h3 class="hLh30 txtOf mt10">
                      <router-link
                        :to="'/course/' + item.course_id"
                        :title="item.course_title"
                        class="course-title fsize18 c-333"
                        >{{ item.course_title }}</router-link
                      >
                    </h3>
                    <section class="mt10 hLh20 of">
                      <!-- <span class="fr jgTag bg-green">
                        <i class="c-fff fsize12 f-fA">免费</i>
                      </span> -->
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">{{item.course_bought}}人学习</i>
                        <!-- |
                        <i class="c-999 f-fA">{{item.count_comment}}评论</i> -->
                      </span>
                    </section>
                  </div>
                </li>
              </ul>
              <div class="clear"></div>
            </article>

            <section class="tac pt20">
              <router-link to="/course" tag="li" class="comm-btn c-btn-2">
                <a>全部课程</a>
              </router-link>
            </section>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>



<script>
import mainpageApi from "/src/api/mainpage";
import cookie from "js-cookie";
import loginApi from "/src/api/login";
export default {
  data() {
    return {
      bannerList: [],
      coureselist: [],
      islogin: false,
      input: "",
      searchContent: "",
    };
  },
  mounted() {
    this.setData();
    this.getBannerList();
    this.getHotCourses();
    // this.bannerlist = [
    //   {
    //     id: 1,
    //     linkurl: "",
    //     imgurl: "../../banner/1.jpg",
    //   },
    //   {
    //     id: 2,
    //     linkurl: "",
    //     imgurl: "../../banner/2.jpg",
    //   },
    //   {
    //     id: 3,
    //     linkurl: "",
    //     imgurl: "../../banner/3.jpg",
    //   },
    // ];
    // this.coureselist = [
    //   {
    //     id: "1",
    //     name: "30天搞定java核心技术",
    //     imgurl: "../../course/01.jpg",
    //     linkurl: "/course/01",
    //   },
    //   {
    //     id: "2",
    //     name: "1天搞定java核心技术",
    //     imgurl: "../../course/02.jpg",
    //     linkurl: "/course/02",
    //   },
    // ];
  },
  watch: {
    "$store.state.Authorization": {
      deep: true,
      handler: function (newValue, oldValue) {
        console.log("main  ");
        console.log("old" + oldValue);
        console.log("new" + newValue);
      },
    },
  },
  methods: {
    setData() {
      if (cookie.get("user_token") != null) {
        this.islogin = true;
        console.log("login");
      } else {
        this.islogin = false;
        console.log("no login");
      }
      console.log("用户token: " + cookie.get("user_token"));
    },
    logout() {
  

      cookie.remove("user_token");
      cookie.remove("user_id");
      cookie.remove("user_name");
      cookie.remove("user_gold");
      localStorage.removeItem("Authorization");
      console.log("退出");
      this.$router.push("/");
      // location.reload();
    },
    searchCourse() {
      console.log("searchM" + this.searchContent);
      this.$router.push({
        path: `/course`,
        query:{
          content : this.searchContent,
        },
      })
    },
    getBannerList() {
      mainpageApi.getListBanner().then((response) => {
        console.log(response.data);
        this.bannerList = response.data;
        console.log(this.bannerList);
      });
    },
    getHotCourses() {
      mainpageApi.getgoodCourse().then((response) => {
        console.log(response.data);
        this.coureselist = response.data;
      });
    },
  },
 
};
</script>


<style scoped>
/* header */
.login {
  margin-top: 15px;
  float: right;
}
.container {
  /* position: fixed; */
  top: 0%;
}
.linkbox {
  height: 50px;
  display: inline-block;
}
.cblue {
  color: #143bba;
}
#userdiv {
  float: right !important;
  height: 40px;
  width: 40px;
  margin-top: 10px;
}
.search-box {
  display: inline-block;

  margin: 0;
  width: 300px;
}
#editquiz {
  margin-left: 100px;
}
#header {
  background-color: #fff;
  box-shadow: 0 2px 3px rgb(7 0 2 / 4%);
  position: relative;
}
.c666 {
  color: #666;
}
.top {
  border-bottom: 2px solid transparent;

  display: inline-block;
  font-size: 20px;
  line-height: 50px;
  padding: 0 10px;
  margin: -10px 10px 0px 10px;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.topright {
  border-bottom: 2px solid transparent;

  display: inline-block;
  font-size: 20px;
  line-height: 50px;
  padding: 0 10px;
  margin-left: 160px;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.topright2 {
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
  position: fixed;
  width: 100%;
  z-index: 99;
}
.el-aside {
  background: #ff9569;
}
.content {
  height: calc(100vh - 90px);
}

/* header .*/
a {
  text-decoration: none;
  color: #424242;
}
.block {
  width: 1160px;
  /* margin-left: 240px; */
  margin-left: 165px;
  margin-top: 60px;
}
#aCoursesList {
  padding-bottom: 80px;
}
.container {
  margin-left: auto;
  margin-right: auto;
  width: 1160px;
}
.comm-title {
  overflow: hidden;
  clear: both;
  margin: 40px 0 30px;
}
.tac {
  text-align: center;
}
.comm-title h2 span {
  font-size: 26px;
  font-family: simhei;
}
.comm-course-list ul {
  margin-left: -30px;
}

.comm-course-list li {
  float: left;
  width: 25%;
}

.comm-course-list li .cc-l-wrap {
  display: block;
  margin: 0 0 50px 30px;
  overflow: hidden;
}

.img-responsive {
  max-width: 100%;
  height: auto;
  display: block;
  transition: 0.8s;
  -webkit-transition: 0.8s;
}
.course-img {
  position: relative;
  overflow: hidden;
}

.cc-mask {
  background: rgba(0, 0, 0, 0.4);
  height: 100%;
  left: 0;
  opacity: 0;
  filter: alpha(opacity=0);
  position: absolute;
  right: 0;
  top: 0;
  transition: all 0.3s ease 0s;
  width: 100%;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.mask {
  left: 50%;
  margin-left: -72px;
  margin-top: -16px;
  position: absolute;
  top: 100%;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.cc-mask .c-btn-1 {
  left: 50%;
  margin-left: -72px;
  margin-top: -16px;
  position: absolute;
  top: 100%;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.c-btn-1 {
  border-color: #fff;
  color: #fff;
  width: 120px;
  font-size: 18px;
}
.comm-btn {
  border: 1px solid;
  border-radius: 16px;
  display: inline-block;
  text-align: center;
  height: 32px;
  line-height: 32px;
  padding: 0 12px;
}
.mt10 {
  margin-top: 10px;
}
.hLh30 {
  height: 30px;
  line-height: 30px;
}
.txtOf {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.fsize18 {
  font-size: 18px;
}
.hLh20 {
  height: 20px;
  line-height: 20px;
}
.of {
  overflow: hidden;
}
.jgTag {
  display: inline-block;
  padding: 1px 12px;
  text-align: center;
  height: 20px;
}
.f-fA {
  font-family: SimSun;
}
em,
i {
  font-style: normal;
}
.fr,
.Right {
  float: right;
}
.fl,
.Left {
  float: left;
}
.c-ccc {
  color: #ccc;
}
.c-999 {
  color: #999;
}
.bg-green {
  background-color: #14ba4c;
}
.c-333 {
  color: #333;
}
.c-fff {
  color: #fff;
}
.fsize12 {
  font-size: 12px;
}
.f-fA {
  font-family: SimSun;
}
.pt20 {
  padding-top: 20px;
}
.tac {
  text-align: center;
}
.c-btn-2 {
  border-radius: 20px;
  width: 160px;
  font-size: 20px;
  height: 36px;
  line-height: 36px;
}
em,
i {
  font-style: normal;
}
li {
  list-style: none;
}
</style>