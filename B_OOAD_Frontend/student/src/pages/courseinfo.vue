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
                @click="searchCourse()"
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
            :width="60"
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
    <!-- /课程详情 开始 -->
    <section class="c-container">
      <section class="path-wrap txtOf hLh30">
        <a href="/" title class="c-999 fsize14">首页</a>
        \
        <a href="/course" title class="c-999 fsize14">课程列表</a>
        \{{ course_info.course_title }}
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px">
          <section class="p-h-video-box" id="videoPlay">
            <el-image
              fit="cover"
              :src="course_info.course_cover"
              class="dis c-v-pic"
            />
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{ course_info.course_title }}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格: </span>
              <b class="c-yellow" style="font-size: 24px">
                {{ course_info.course_price }} skc</b
              >
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲： {{course_info.teacher_name}}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <a class="c-fff vam" title="收藏" href="#"></a>
              </span>
            </section>
            <section v-if="isBuy" class="c-attr-mt">
              <a href="#chapter" title="立即观看" class="comm-btn c-btn-3"
                >已购买</a
              >
            </section>
            <section v-else class="c-attr-mt">
              <el-button
                @click="createOrders()"
                href="#"
                title="立即购买"
                class="comm-btn c-btn-4"
                >立即购买</el-button
              >
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol clearfix">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{course_info.course_bought}}</h6>
              </aside>
            </li>
            <!-- <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br />
                <h6 class="c-fff f-fM mt10">20</h6>
              </aside>
            </li> -->
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">评论数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{course_info.course_comment_num}}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <p v-html="course_info.course_description" class="fsize16"></p>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div id="chapter" class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <!-- 文件目录 -->
                          <li
                            class="lh-menu-stair"
                            v-for="chapter in chapterVideoList"
                            :key="chapter.chapter_index"
                          >
                            <a
                              href="javascript: void(0)"
                              :title="chapter.chapter_title"
                              class="current-1"
                            >
                              {{
                                chapter.chapter_index +
                                " " +
                                chapter.chapter_title
                              }}
                            </a>

                            <ol class="lh-menu-ol" style="display: block">
                              <li
                                class="lh-menu-second ml30"
                                v-for="video in chapter.lessonList"
                                :key="video.lesson_index"
                              >
                                <el-button
                                   @click="jumpTo(courseId,video.lesson_id,chapter.chapter_index,video.lesson_index)"
                                   text
                                >
                  
                                  {{
                                    chapter.chapter_index +
                                    "-" +
                                    video.lesson_index +
                                    " " +
                                    video.lesson_title
                                  }}
                                </el-button>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <!-- /课程大纲 -->
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto">
                  <li>
                    <div class="u-face">
                      <a href="#">
                        <img src="../../img/teacher.jpg" width="50" height="50" alt />
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" href="#">{{
                        course_info.teacher_name
                      }}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">老师</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
    <div class="mt50 commentHtml container">
      <div>
        <h6 class="c-c-content c-infor-title" id="i-art-comment">
          <span class="commentTitle">课程评论</span>
        </h6>
        <section class="lh-bj-list pr mt20 replyhtml">
          <ul>
            <li class="unBr">
              
              <div class="of">
                <div class="n-reply-wrap">
                  <div class="answertext">
                    <el-input
                      v-model="comment.comment_content"
                      :rows="2"
                      type="textarea"
                      maxlength="120"
                      show-word-limit
                      placeholder="发一条友善的评论"
                    />
                  </div>
                  <div class="answerbtn">
                    <el-button type="primary" @click="addcomment()">
                      <el-icon style="vertical-align: middle">
                        <edit />
                      </el-icon>
                      <span
                        style="vertical-align: middle"
                      >
                        回复
                      </span>
                    </el-button>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </section>
        <section>
          <section class="question-list lh-bj-list pr">
            <ul class="pr10">
              <li v-for="(comment, index) in commentdata" v-bind:key="index">
                <aside class="noter-pic">
                  <img
                    width="50"
                    height="50"
                    class="picImg"
                    src="../../img/ava.png"
                  />
                </aside>
                <div class="of">
                  <span class="fl">
                    <p class="fsize14 c-666">{{ comment.user_id }}</p>
                    <p class="fsize12 c-999 ml5"></p
                  ></span>
                </div>
                <div class="noter-txt mt5">
                  <p>{{ comment.comment_content }}</p>
                </div>
                <div class="of mt5">
                  <span class="fl mt5"
                    ><p class="fsize12 c-999 ml5">
                      {{ comment.comment_time }}
                    </p></span
                  >
                </div>
              </li>
            </ul>
          </section>
        </section>

        <!-- 公共分页 开始 -->
        <div class="paging">
          <div class="clear" />
        </div>
        <!-- 公共分页 结束 -->
      </div>
    </div>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from "element-plus";
import courseApi from "/src/api/course";
import commentApi from "/src/api/comment";
import userApi from "/src/api/user";
import cookie from "js-cookie";
import { parseTime } from "/src/utils/date.js";
import user from '../api/user';
export default {
  data() {
    return {
      islogin: false,
      searchContent: "",
      commentdata: [],

      courseId: "",
      comment: {
        user_id: "",
        course_id: "",
        comment_content: "",
      },
      course_info: {},
      chapterVideoList: {},
      isbuyCourse: false,
      isBuy: false,
      money: 0,
      user_name: "",
    };
  },

  mounted() {
    this.setData();
    this.initCourseInfo();
    this.getBought();
    this.getComment();
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
      console.log("课程id: " + this.$route.params.courseid);
      console.log("用户token: " + cookie.get("user_token"));
      this.courseId = this.$route.params.courseid;
      this.money = cookie.get("user_gold");
      console.log("余额", this.money);
      // document.cookie = "authorized-token=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
      // console.log("cookie", document.cookie)
      
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
    jumpTo(courseid,lessonid,chapterindex,lessonindex){
      if(this.isBuy == true){ 
        this.$router.push(
         { 
          path: `/course/${courseid}/${lessonid}`,
          query: {
            chapterlist: JSON.stringify(this.chapterVideoList),
            mychapter_index: chapterindex,
            //  mychapter_index: 1,
            mylesson_index: lessonindex
            // mylesson_index: 1
            }
          }
        )
        }else{
           ElMessageBox.confirm("你还没有购买课程，请先购买。", "Warning", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
        }
    },
    createOrders() {
      if (this.islogin == false) {
        ElMessageBox.confirm("你还没有登录，请先登录。", "Warning", {
          confirmButtonText: "去登录",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.$router.push("/login");
          })
          .catch(() => {
            ElMessage({
              type: "info",
              message: "取消购买",
            });
          });
      } else {
        console.log("价格", this.course_info.course_price);
        let price = parseInt(this.course_info.course_price);
        console.log("余额", this.money);
        console.log(this.money > price);
        if (this.money > parseInt(this.course_info.course_price)) {
          this.money = this.money - parseInt(this.course_info.course_price);
          this.postBuy();
    
        } else {
          ElMessageBox.confirm("你的余额不足，请先充值。", "Warning", {
            confirmButtonText: "去充值",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
        
              this.$router.push("/home/payment");
            })
            .catch(() => {
              ElMessage({
                type: "info",
                message: "取消购买",
              });
            });
        }
      }
    },
    addcomment() {
      console.log("加评论")
      if (this.islogin == false) {
        ElMessageBox.confirm("你还没有登录，请先登录。", "Warning", {
          confirmButtonText: "去登录",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.$router.push("/login");
          })
          .catch(() => {
            ElMessage({
              type: "info",
              message: "取消评论",
            });
          });
      }
      else if(this.isBuy ==false){
         ElMessageBox.confirm("你还没有购买课程，请先购买。", "Warning", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
      } else {
        this.comment.course_id = this.courseId;
        this.comment.user_id = cookie.get("user_id");
        console.log("评论提交表单", this.comment);
        // this.comment.teacherId = this.courseWebVo.teacherId;
        commentApi
          .addComment(this.comment)
          .then((response) => {
            if (response.data) {
              console.log(response.data);
              if (response.data != "ok") {
                ElMessage({
                  message: "评论失败",
                  type: "error",
                });
              } else {
                this.comment.comment_content = "";
                this.getComment();
                ElMessage({
                  message: "成功评论",
                  type: "success",
                });
                console.log("success comment");
              }
            }
          })
          .catch((err) => {
            ElMessage({
              message: "评论失败",
              type: "error",
            });
          });
      }
    },
    initCourseInfo() {
      courseApi.getCourseInfo(this.courseId).then((response) => {
   
        this.course_info = response.data;

        let chapterdetail = JSON.parse(response.data.chapter_detail);
        console.log("课程信息");
        console.log(this.course_info);
        console.log("chapterdetail.length", chapterdetail.length);
        this.chapterVideoList = chapterdetail;
        
       
        // localStorage.setItem('chapterList', JSON.stringify(chapterdetail));
      });
    },
    getComment() {
      commentApi.getComment(this.courseId).then((response) => {
        let commenttmp = response.data;
        console.log("commenttmp", response.data);
        this.commentdata = [];
        for (let i = 0; i < commenttmp.length; i++) {
          console.log("时间戳：" + commenttmp[i].comment_time);
          this.commentdata.push({
            comment_content: commenttmp[i].comment_content,
            comment_id: commenttmp[i].comment_id,
            user_id: commenttmp[i].user_id,
            comment_time: parseTime(commenttmp[i].comment_time),
          });
        }
      });
    },
    // getCommentUser(id){
    //   userApi.getUserInfo(id).then((response) =>{

    //   })
    // },
    postBuy() {
      courseApi
        .postBuy(this.courseId, cookie.get("user_id"))
        .then((response) => {
          console.log(response.data);
           ElMessage({
            message: "成功购买，你现在可以观看视频了。",
            type: "success",
          });
          this.isBuy = true;
          console.log("success buy");
        });
    },
    getBought() {
      console.log("getBought api "+this.courseId+ " "+cookie.get("user_id"))
      courseApi.getBought(this.courseId, cookie.get("user_id"))
        .then((response) => {
          console.log(response);
          if(response.data == 'ok'){
            this.isBuy = true;
          }else{
            this.isBuy = false;
          }

        });
        console.log("getBought api end")
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
.c-999 {
  color: #999;
}
.ml5 {
  margin-left: 5px;
}
.fsize14 {
  font-size: 14px;
}
.c-333 {
  color: #333;
}
.clear {
  clear: both;
  width: 0px;
  height: 0px;
  overflow: hidden;
}
a {
  text-decoration: none;
  color: #424242;
}
/* header */
#aCoursesList {
  padding-bottom: 80px;
  padding-top: 30px;
}
.c-container {
  margin-left: auto;
  margin-right: auto;
  width: 1160px;
}
.c-blue {
  color: blue;
}
.fsize12 {
  font-size: 12px;
}
.fsize16{
  font-size: 16px;
}
.bg-fa {
  background-color: #fafafa;
}
.of {
  overflow: hidden;
}
.path-wrap {
  margin: 20px 0;
  color: #ccc;
  line-height: 30px;
}
.txtOf {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.c-v-pic-wrap {
  float: left;
  transition: 0.3s;
  position: relative;
  width: 640px;
}
.p-h-video-box {
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  height: 100%;
  width: 100%;
  z-index: 2;
}
.c-v-pic {
  display: block;
  max-width: 100%;
  height: 357px;
  width: 640px;
}
.c-attr-wrap {
  background: #264863;
  float: right;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.c-attr-wrap {
  width: 400px;
  height: 357px;
}
.mr15 {
  margin-right: 15px;
}
.ml20 {
  margin-left: 20px;
}

.mt15 {
  margin-top: 15px;
}
.hLh30 {
  height: 30px;
  line-height: 30px;
}
.hLh20 {
  height: 20px;
  line-height: 20px;
}
.c-fff {
  color: #fff;
}
.fsize24 {
  font-size: 24px;
}
.c-attr-mt {
  margin-top: 30px;
}
.thr-attr-box {
  background: #985054;
  float: right;
  transition: 0.3s;
  -webkit-transition: 0.3s;
  width: 120px;
  height: 357px;
}
.clearfix {
  display: block;
  height: 1%;
  zoom: 1;
}
.thr-attr-ol {
  padding: 0 10px;
  overflow: hidden;
  height: 100%;
}
.thr-attr-ol li {
  border-bottom: 1px solid #ad7376;
  height: 33.33%;
  width: 100%;
  text-align: center;
  display: block;
}
.thr-attr-ol li aside {
  display: inline-block;
  vertical-align: middle;
}
.thr-attr-ol li span {
  font-size: 14px;
  vertical-align: middle;
}
.thr-attr-ol li h6 {
  display: inline-block;
  font-size: 20px;
  vertical-align: middle;
}
.mt10 {
  margin-top: 10px;
}
.mt20 {
  margin-top: 20px;
}
.fl,
.Left {
  float: left;
}
.mr30 {
  margin-right: 30px;
}
.i-box {
  background-color: #fff;
  border: 1px solid #f0f0f0;
  padding: 0 15px 20px;
  overflow: hidden;
}
.c-infor-tabTitle.c-tab-title {
  margin: 20px 0;
  text-align: left;
}

.c-tab-title {
  border-bottom: 1px solid #e2e2e2;
  height: 34px;
  text-align: center;
  margin-bottom: 50px;
}
.c-infor-tabTitle.c-tab-title a {
  font-size: 20px;
  margin-left: 0;
}
.pt20 {
  padding-top: 20px;
}
.mr10 {
  margin-right: 10px;
}

.ml10 {
  margin-left: 10px;
}
.c-infor-title {
  height: 18px;
  overflow: hidden;
}
.c-infor-title span {
  border-left: 5px solid #333;
  color: #333;
  font-size: 18px;
  display: inline-block;
  line-height: 18px;
  padding-left: 10px;
}
h6 {
  font-size: 12px;
  line-height: 16px;
  font-weight: normal;
}

.course-txt-body {
  overflow: hidden;
  color: #888;
  font-size: 14px;
  line-height: 30px;
  white-space: normal;
  word-break: break-all;
  margin-top: 20px;
  padding: 0 10px;
  text-align: justify;
}
.mt50 {
  margin-top: 50px;
}
.col-3 {
  width: 25%;
}

.c-tab-title a {
  border-bottom: 1px solid transparent;
  display: inline-block;
  line-height: 34px;
  margin: 0 20px;
  padding: 0 10px;
  font-size: 16px;
  color: #666;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.c-infor-tabTitle.c-tab-title {
  margin: 20px 0;
  text-align: left;
}
.stud-act-list ul {
  height: auto;
  overflow: hidden;
}
.stud-act-list li {
  border-bottom: 1px solid #f2f2f2;
  padding: 17px 0 12px 75px;
  position: relative;
  height: 90px;
}
.u-face {
  position: absolute;
  left: 0;
  top: 10px;
}
.u-face img {
  display: block;
  width: 60px;
  height: 60px;
  border-radius: 50%;
}
ol,
ul,
li {
  list-style: none;
}
.lh-bj-list ul li {
  border-bottom: 1px solid #e9e9e9;
  padding: 12px 5px 2px 65px;
  position: relative;
  min-height: 50px;
}
.unBr {
  border: none !important;
}
.noter-pic {
  position: absolute;
  left: 0;
  top: 12px;
}
.noter-pic img {
  border-radius: 50%;
  display: block;
  height: 50px;
  width: 50px;
}
.n-reply-wrap textarea {
  background: #fff;
  border: 1px solid #ddd;
  width: 98%;
  height: 48px;
  padding: 4px;
  font-size: 14px;
  color: #666;
  overflow-x: hidden;
  overflow-y: auto;
}
.answertext {
  float: left;
}
textarea {
  resize: none;
}
.pr10 {
  padding-right: 10px;
}
.pl10 {
  padding-left: 10px;
}
.mt5 {
  margin-top: 5px;
}
/* button, input[type="button"] {
    color: #FFF;
    background-color: #00baf2;
    line-height: 20px;
    border: none;
    padding: 0px 5px;
    cursor: pointer;
}

input, button, input[type="button"] {
    outline: none;
    font-size: 14px;
} */
.lh-reply-btn {
  background: #6acc7e;
  border: 1px solid #55c76c;
  border-radius: 3px;
  display: inline-block;
  height: 18px;
  width: 38px;
  text-align: center;
  font: 12px/18px Arial;
  color: #fff;
}
.pr {
  position: relative;
}
.paging {
  padding: 50px 0 20px;
  text-align: center;
}
.paging a.current,
.paging a.undisable {
  pointer-events: none;
  cursor: text;
}
.paging a {
  border: 1px solid #ccc;
  border-radius: 40%;
  color: #666;
  display: inline-block;
  margin: 0 5px;
  height: 30px;
  width: 30px;
  text-align: center;
  line-height: 30px;
  transition: 0.3s;
  -webkit-transition: 0.3s;
}
.c-btn-2,
.c-btn-3,
.c-btn-4,
.c-btn-5,
.c-btn-7 {
  border-radius: 20px;
  width: 160px;
  font-size: 20px;
  height: 36px;
  line-height: 36px;
}

.comm-btn {
  padding: 0 8px;
}
.c-yellow {
  color: #fc0;
}
.c-attr-mt {
  margin-top: 30px;
}
.ml10 {
  margin-left: 10px;
}
.vam {
  vertical-align: middle;
}
.scIcon {
  background-position: 0 -113px;
}

.c-attr-jg {
  border-bottom: 1px solid #30536f;
  margin-top: 30px;
  padding-bottom: 20px;
  vertical-align: middle;
}
.c-btn-3 {
  border-color: #fc0;
  color: #fc0;
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
.col-7 {
  width: 75%;
}

.mr10 {
  margin-right: 10px;
}
.mt10 {
  margin-top: 10px;
}
.lh-menu {
  margin: 0 10px;
  padding: 0;
}
.lh-menu ul li.lh-menu-stair {
  padding-bottom: 5px;
  margin-bottom: 10px;
}

.lh-menu ul li.lh-menu-stair a.current-1 {
  background: #f4f4f4;
  border-bottom: 1px solid #e6e6e6;
}
.lh-menu ul li.lh-menu-stair a {
  background: #f8f8f8;
  color: #333;
  display: block;
  font: 16px/36px "Microsoft YaHei";
  height: 36px;
  padding: 0 10px;
  overflow: hidden;
}
.lh-menu-second a span.fr {
  color: #bbb;
  font-size: 12px;
}
.free-icon {
  border: 1px solid #72cd91;
  border-radius: 20px;
  color: #72cd91;
  display: inline-block;
  height: 14px;
  line-height: 14px;
  font-size: 12px;
  padding: 0 8px;
}
.fr,
.Right {
  float: right;
}
.mr5 {
  margin-right: 5px;
}
.lh-menu-i-2 {
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 50%;
  width: 12px;
  height: 12px;
}

.icon16 {
  display: inline-block;
  height: 16px;
  width: 16px;
  vertical-align: middle;
}
.ml30 {
  margin-left: 30px;
}
.mr10 {
  margin-right: 10px;
}
.lh-menu-i-1 {
  background-color: #fff;
  border: 1px solid #bbb;
  border-radius: 50%;
  text-align: center;
  line-height: 12px;
}
.icon18 {
  display: inline-block;
  height: 18px;
  width: 18px;
  vertical-align: middle;
}
.fsize18 {
  font-size: 18px;
}
.lh-menu ul li.lh-menu-stair ol li.lh-menu-second a {
  background: #fff;
  color: #555;
  display: block;
  font: 14px/32px "Microsoft YaHei";
  height: 32px;
  padding: 0 10px;
  margin-top: 5px;
  overflow: hidden;
}
.el-textarea {
  position: relative;
  display: inline-block;
  width: 680px;
  vertical-align: bottom;
  font-size: var(--el-font-size-base);
}
.answerbtn .el-button {
  height: 50px;
}
.question-list {
  width: 850px;
}
.answerbtn {
  margin-right: 315px;
  float: right;
}
</style>