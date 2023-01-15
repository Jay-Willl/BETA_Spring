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
              <a class="cblue top">课程</a>
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
                @click="getsearchCourse()"
                style="border-color: #00000029; background-color: white"
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
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" @click="searchByalphabetical()">全部</a>
                </li>
                <li v-for="item in taglist" :key="item">
                  <el-link  :title="item" @click="searchBytag(item)">{{ item }}</el-link>
                </li>

                <!-- <li v-for="(item,index) in subjectNestedList" :key="index" :class="{active:oneIndex==index}">
                  <a :title="item.title" href="#" @click="searchOne(item.id,index)">{{item.title}}</a>
                </li> -->
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li
                  v-for="(item, index) in subSubjectList"
                  :key="index"
                  :class="{ active: twoIndex == index }"
                >
                  <a
                    :title="item.title"
                    href="#"
                    @click="searchTwo(item.id, index)"
                    >{{ item.title }}</a
                  >
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{ 'current bg-orange': Byalphabetical != '' }">
                <a
                  title="热度"
                  href="javascript:void(0);"
                  @click="searchByalphabetical()"
                  >字母表
                  <span :class="{ hide: Byalphabetical == '' }">↓</span>
                </a>
              </li>
              <li :class="{ 'current bg-orange': Bysold != '' }">
                <a
                  title="最新"
                  href="javascript:void(0);"
                  @click="searchBysold()"
                  >购买量
                  <span :class="{ hide: Bysold == '' }">↓</span>
                </a>
              </li>
              <!-- <li :class="{'current bg-orange':priceSort!=''}">
                <a title="价格" href="javascript:void(0);" @click="searchPrice()">价格&nbsp;
                  <span :class="{hide:priceSort==''}">↓</span>
                </a>
              </li> -->
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="coursedata == {}">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam"
              >没有相关数据，小编正在努力整理中...</span
            >
          </section>
          <!-- /无数据提示 结束 -->
          <article class="comm-course-list">
            <ul class="of" id="bna">
              <li v-for="item in coursedata" :key="item.course_id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <router-link :to="'/course/' + item.course_id">
                      <el-image
                        :src="item.course_cover"
                        class="img-responsive"
                        fit="cover"
                        style="height: 150px; width: 268px"
                        :alt="item.course_title"
                      />
                    </router-link>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <router-link
                      :to="'/course/' + item.course_id"
                      :title="item.course_title"
                      class="course-title fsize18 c-333"
                    >
                      {{ item.course_title }}
                    </router-link>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span
                      v-if="Number(item.course_price) === 0"
                      class="fr jgTag bg-green"
                    >
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{ item.course_bought }}人学习</i>
                    </span>
                  </section>
                </div>
              </li>
              <!-- <li>
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img
                      src="../assets/photo/course/05.jpg"
                      class="img-responsive"
                    />
                    <div class="cc-mask">
                      <a
                        href="/course/1"
                        title="开始学习"
                        class="comm-btn c-btn-1"
                        >开始学习</a
                      >
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a
                      href="/course/1"
                      title="spring MVC"
                      class="course-title fsize18 c-333"
                      >spring MVC</a
                    >
                  </h3>
                  <section class="mt10 hLh20 of">
                   
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">9634人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li> -->
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <el-pagination
              background
              @current-change="handleCurrentChange"
              :page-size="pageSize"
              hide-on-single-page
              layout="prev, pager, next, jumper"
              :total="total"
              class="pagina"
            >
            </el-pagination>

            <div class="clear" />
          </div>
        </div>
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>

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
.cblue {
  color: #143bba;
}
#userdiv {
  float: right !important;
  height: 40px;
  width: 40px;
  margin-top: 10px;
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
  position: relative;
}
.el-aside {
  background: #ff9569;
}
.content {
  height: calc(100vh - 90px);
}
/* header */
/* course list */
#aCourseslist {
  padding-bottom: 80px;
}
.bg-fa {
  background-color: #fafafa;
}
.of {
  overflow: hidden;
}
.comm-title {
  overflow: hidden;
  clear: both;
  margin: 40px 0 30px;
}
.comm-title h2 span {
  font-size: 26px;
  font-family: simhei;
}
.fl,
.Left {
  float: left;
}
.tac {
  text-align: center;
}

.c-sort-box {
  border-top: 1px solid #e2e2e2;
  margin-top: -15px;
}

.c-s-dl dl {
  position: relative;
  padding: 15px 60px 0 0;
  border-bottom: 1px dotted #ddd;
  line-height: 30px;
}
dl {
  -webkit-margin-before: 0;
  -webkit-margin-after: 0;
}
.c-s-dl dl dt {
  float: left;
  width: 75px;
  text-align: right;
  padding-right: 15px;
}
.c-999 {
  color: #999;
}
.fsize14 {
  font-size: 14px;
}
.c-s-dl-li {
  padding-left: 90px;
}
.c-s-dl-li ul {
  overflow: hidden;
}
.clearfix {
  display: block;
  height: 1%;
  zoom: 1;
}
.c-s-dl-li ul li {
  float: left;
  margin: 0 15px 9px 0;
}
.c-s-dl dl dt {
  float: left;
  width: 75px;
  text-align: right;
  padding-right: 15px;
}
.clear {
  clear: both;
  width: 0px;
  height: 0px;
  overflow: hidden;
}
.c-s-dl-li ul li a {
  display: inline-block;
  color: #666;
  font-size: 14px;
  padding: 0 10px;
}
ol,
ul,
li {
  list-style: none;
}
a {
  text-decoration: none;
}
.js-wrap {
  background: #f4f4f4;
  height: 40px;
  line-height: 40px;
  padding: 0 15px;
  position: relative;
  overflow: hidden;
  clear: both;
}
.fr,
.Right {
  float: right;
}
.c-ccc {
  color: #ccc;
}
.f-fM {
  font-family: "Microsoft YaHei";
}

.c-master {
  color: #68cb9b;
}
.c-666 {
  color: #666;
}
em,
i {
  font-style: normal;
}
.mt40 {
  margin-top: 40px;
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
.course-img {
  position: relative;
  overflow: hidden;
}
.img-responsive {
  max-width: 100%;
  height: auto;
  display: block;
  transition: 0.8s;
  -webkit-transition: 0.8s;
}
.js-tap li {
  float: left;
}
.js-tap li a {
  display: block;
  padding: 0 30px;
  line-height: 40px;
  color: #666;
  font-size: 14px;
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
.mt10 {
  margin-top: 10px;
}
.hLh30 {
  height: 30px;
  line-height: 30px;
}
.fsize18 {
  font-size: 18px;
}
.c-333 {
  color: #333;
}
.txtOf {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.hLh20 {
  height: 20px;
  line-height: 20px;
}
.jgTag {
  display: inline-block;
  padding: 1px 12px;
  text-align: center;
  height: 20px;
}
.bg-green {
  background-color: #14ba4c;
}
.c-fff {
  color: #fff;
}
.f-fA {
  font-family: SimSun;
}
.paging {
  padding: 50px 0 10px;
  text-align: center;
}
/* .paging a {
    border: 1px solid #ccc;
    border-radius: 40%;
    color: #666;
    display: inline-block;
    margin: 0 5px;
    height: 30px;
    width: 30px;
    text-align: center;
    line-height: 30px;
    transition: .3s;
    -webkit-transition: .3s;
} */
.pagina {
  margin-left: 250px;
}
</style>

<script>
import courseApi from "/src/api/course";
import cookie from "js-cookie";
export default {
  data() {
    return {
      searchContent: "",
      page: 1, //当前页
      coursedata: {}, //课程列表
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [], // 二级分类列表
      islogin: false,
      searchObj: {}, // 查询表单对象
      pagesize: 8,
      total: 80,
      oneIndex: -1,
      twoIndex: -1,
      Byalphabetical: "",
      Bysold: "",
      priceSort: "",
      taglist: [],
    };
  },
  beforeRouteEnter(to, from, next) {
    console.log("to", to);
    if (to.query.content) {
      console.log("其他页面搜索", to.query.content);
      next((vm) => {
        vm.searchContent = to.query.content;
        vm.getsearchCourse(to.query.content);
      });
    }else{
      next(true);
    }
  },
  mounted() {
    this.setData();
    this.initCourses();
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
      this.taglist = [  
        "computer science", 
        "Artificial intelligence",
        "Computer vision",
        "e-sports",
        "CSGO",
        "DOTA2",
        "art",
        "Music",
        "Painting",
        ];
    },
    logout() {
      // loginApi.submitLogOut().then((response) => {
      //   cookie.remove("user_token");
      //   cookie.remove("user_id");
      //   cookie.remove("user_name");
      //   cookie.remove("user_gold");
      //   console.log(cookie.get("user_token"));
      //   localStorage.removeItem("Authorization");
      //   console.log("退出");
      //   this.$router.push("/");
      //   location.reload();
      // });

      cookie.remove("user_token");
      cookie.remove("user_id");
      cookie.remove("user_name");
      cookie.remove("user_gold");
      localStorage.removeItem("Authorization");
      console.log("退出");
      this.$router.push("/");
    },
    initCourses() {
      if (this.searchContent == "") {
        courseApi.getCoursesByPage(1, "alphabetical").then((response) => {
          this.coursedata = response.data;
          console.log("初始化所有课程", this.coursedata);
        });
      }
    },
    handleCurrentChange(val) {
      console.log("当前页面", val);
      if (this.Bysold == "yes") {
        courseApi.getCoursesByPage(val, "sold").then((response) => {
          console.log(response);
          this.coursedata = response.data;
        });
      }

      if (this.Byalphabetical == "yes") {
        courseApi.getCoursesByPage(val, "alphabetical").then((response) => {
          console.log(response);
          this.coursedata = response.data;
        });
      }

      if (this.Byalphabetical == "" && this.Bysold == "") {
        courseApi.getCoursesByPage(val, "alphabetical").then((response) => {
          console.log(response);
          this.coursedata = response.data;
        });
      }
    },
    getsearchCourse() {
      console.log("search get " + this.searchContent);
      console.log(this.searchContent == "")
      if(this.searchContent == ""){
        courseApi.getCoursesByPage(1, "alphabetical").then((response) => {
   
        this.coursedata = response.data;
      });
      }else{
         courseApi.getsearchCourse(1, this.searchContent).then((response) => {
      
        this.coursedata = response.data;
      });
      }
     
    },
    searchBytag(tag){
      courseApi.getsearchCourse(1, tag).then((response) => {
         console.log(response.data);
        this.coursedata = response.data;
      });
    },
    searchByalphabetical() {
      console.log("字母表排序");
      this.Byalphabetical = "yes";
      this.Bysold = "";
      courseApi.getCoursesByPage(1, "alphabetical").then((response) => {
   
        this.coursedata = response.data;
      });
    },
    searchBysold() {
      console.log("销量排序");
      this.Byalphabetical = "";
      this.Bysold = "yes";
      courseApi.getCoursesByPage(1, "sold").then((response) => {
        // console.log(response.data);
        this.coursedata = response.data;
      });
    },
  },
};
</script>