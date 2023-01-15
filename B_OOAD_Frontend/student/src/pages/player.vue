<template>
  <div>
    <div class="video-study">
      <header class="el-header">
        <div class="banner">
          <div class="back">
            <el-link
              class="bak"
              @click="$router.back()"
              type="primary"
              :underline="false"
              icon="ArrowLeftBold"
            >
              <p class="word">返回课程</p>
            </el-link>
          </div>
          <div class="source-con">
            <p class="source-name"></p>
          </div>

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
                    <router-link
                      v-if="islogin"
                      to="/home"
                      active-class="current"
                    >
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

          <div class="menudivright-r-div">
            <div class="Patternbtn-div"></div>
            <div class="morebtn-div showMore"></div>
            <div class="fl InformationCenter-div"></div>
          </div>
        </div>
      </header>
      <div class="box-content" style="height: 661px">
        <div class="box-left">
          <div class="left-top clearfix">
            <div class="videotop_box fl">
              <span class="videotop_lesson fl">
                <b>第{{ myc_index }}章</b>
              </span>
              <span id="lessonOrder" class="videotop_lesson_tit fl">
                {{ myc_index }}-{{ myl_index }} {{ myl_title }}
              </span>
            </div>

            <div class="videotop_all fr">
              <ul class="newList clearfix">
                <li>
                  <el-link :underline="false" icon="Collection" class="c777"
                    >课程介绍</el-link
                  >
                </li>
                <!-- <li>
                  <el-link :underline="false" icon="Tickets" class="c777"
                    ></el-link
                  >
                </li> -->
              </ul>
            </div>
          </div>
          <!-- 定义播放器dom -->
          <div id="J_prismPlayer" class="prism-player" />
        </div>
        <div class="box-right">
          <!-- <ul class="newListTest">
            <li class="homeworkExam" @click="timeover">
              <el-icon style="font-size: 30px"><Reading /></el-icon>
              <p class="c777">暂停</p>
            </li>
            <li class="homeworkExam" @click="timestart">
              <el-icon style="font-size: 30px"><TrendCharts /></el-icon>
              <p class="c777">开始</p>
            </li>
            <li class="homeworkExam" @click="centerDialogVisible = true">
        
              <el-icon style="font-size: 30px"><Reading /></el-icon>
              <p class="c777">完成</p>
          
            </li>
          </ul> -->
          <div class="topicTitle clearfix">
            <ul>
              <li class="tabTitle select">
                <span>目录</span>
              </li>
            </ul>
          </div>
          <el-scrollbar height="520px">
            <el-menu
              :default-active="myc_index + '-' + myl_index"
              class="el-menu-vertical"
              @open="handleOpen"
              @close="handleClose"
            >
              <div v-for="item in chapter_new_list" :key="item.chapter_id">
                <el-sub-menu :index="item.chapter_index + ''">
                  <template #title>
                    <span>{{
                      item.chapter_index + " " + item.chapter_title
                    }}</span>
                    <div style="margin-left: 100px">
                      {{ item.chapter_over_score + "/" + item.chapter_socre }}
                    </div>
                  </template>

                  <el-menu-item
                    v-for="video in item.lessonList"
                    :key="video.lesson_id"
                    :index="item.chapter_index + '-' + video.lesson_index"
                    @click="
                      jumpTo(
                        course_id,
                        video.lesson_id,
                        item.chapter_index,
                        video.lesson_index
                      )
                    "
                  >
                  <div style="margin-right: 10px">
                      {{ "分数:" + video.lesson_score +"|"}}
                    </div>
                    {{
                      item.chapter_index +
                      "-" +
                      video.lesson_index +
                      "  " +
                      video.lesson_title
                    }}
                    <el-icon v-if="video.lesson_over"><Check /></el-icon>
                    
                  </el-menu-item>
                </el-sub-menu>
              </div>
            </el-menu>
          </el-scrollbar>
        </div>
      </div>
    </div>
    <el-dialog
      v-model="centerDialogVisible"
      title="课程完成"
      width="30%"
      align-center
    >
      <span>你已经完成课程，点击继续。</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="confirmlesson()">
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="checkDialog" title="课程检查" width="30%" align-center>
      <span>请点击继续观看</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="confirmcheck()">
            继续观看
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script>
import videoApi from "/src/api/video";
import loginApi from "/src/api/login";
import cookie from "js-cookie";

export default {
  data() {
    return {
      islogin: true,
      chapterVideoList: {},
      chapter_new_list: {},
      timer: "",
      timecnt: 0,
      totaltime: NaN,
      player: null,
      play_status: false,
      play_end: false,
      lesson_socre: 0,

      user_id: "",
      lesson_id: "",
      centerDialogVisible: false,
      cover_url: "",
      source_url: {},
      player_url: "",
      player_quality: "",
      myc_index: "",
      myl_index: "",
      myl_title: "",
      over_list: {},
      checkDialog: false,
      check: false,
      randomNum: 0,
      course_id: "",
      count_score: 0,
    };
  },
  beforeRouteEnter(to, from, next) {
    const { rtt, downlink, effectiveType, saveData } = navigator.connection;
    console.log(`有效网络连接类型: ${effectiveType}`);
    console.log(`估算的下行速度/带宽: ${downlink}Mb/s`);
    console.log(`估算的往返时间: ${rtt}ms`);
    console.log(`打开/请求数据保护模式: ${saveData}`);
    let quality = "";
    if (effectiveType == "4g") {
      console.log("快速网络");
      quality = "HD";
      console.log("超清");
    } else if (effectiveType == "3g") {
      console.log("普速网络");
      quality = "LD";
      console.log("流畅");
    } else if (effectiveType == "2g") {
      console.log("慢速网络");
      quality = "FD";
    }
    videoApi
      .getVideo(to.params.pid)
      .then((response) => {
        if (response) {
          next((vm) => {
            // vm.carInfo = resp.result;
            console.log("视频api调用");

            vm.cover_url = response.data.videoBase.coverURL;
            console.log("视频封面", vm.cover_url);
            vm.totaltime = response.data.videoBase.duration;
            let temp = response.data.videoBase.duration - 1;
            let htemp = Number(temp) / 3;
            vm.randomNum =
              Math.floor(Math.random() * (Number(temp) - htemp + 1)) + htemp;
            let infolist = [];
            infolist = response.data.playInfoList;
            console.log("infolist", infolist.length);
            for (let i = 0; i < infolist.length; i++) {
              let level = infolist[i].definition;
              let srcurl = infolist[i].playURL;
              vm.source_url[level] = srcurl;
            }

            vm.player_url = JSON.stringify(vm.source_url);

            vm.player_quality = quality;

            console.log("quality", quality);
            document.cookie = "selectedStreamLevel=" + quality;
            var _cookie = document.cookie.replace(
              /(selectedStreamLevel=)\w{2}/,
              "$1" + quality
            );
            document.cookie = _cookie;
            // console.log("change cookie",document.cookie)
          });
        } else {
          next(false);
        }
      })
      .catch(() => {
        next(false);
      });
  },
  created() {
    document.addEventListener("visibilitychange", this.visibili);
  },
  mounted() {
    this.getLessonProgress();
    this.player = new Aliplayer(
      {
        id: "J_prismPlayer",

        isLive: false,
        source: this.player_url,
        // source:
        //   '{"FD": "https://outin-3b194cda86c011ed86b800163e1c94a4.oss-cn-shanghai.aliyuncs.com/0b226b308c2f71ed800e0764a3fc0102/2a5375161db94f2fb3b7d85ff694ffb1-be299b2dc577c1a97924b5dc9e0e8252-fd.mp4?Expires=1672899133&OSSAccessKeyId=LTAIwkKSLcUfI2u4&Signature=tlW3y8Hrm37AfwcGdwXU33wcOKk%3D",\
        // "LD": "https://outin-3b194cda86c011ed86b800163e1c94a4.oss-cn-shanghai.aliyuncs.com/0b226b308c2f71ed800e0764a3fc0102/2a5375161db94f2fb3b7d85ff694ffb1-d193a5753c33a9b71064e123cbaf5636-ld.mp4?Expires=1672899133&OSSAccessKeyId=LTAIwkKSLcUfI2u4&Signature=sy2uiRSuN%2BnK%2BBEOW0Tn5iZB73U%3D",\
        // "SD": "https://outin-3b194cda86c011ed86b800163e1c94a4.oss-cn-shanghai.aliyuncs.com/0b226b308c2f71ed800e0764a3fc0102/2a5375161db94f2fb3b7d85ff694ffb1-db92853a324cee99a2f4b32bf1fb6abb-sd.mp4?Expires=1672899133&OSSAccessKeyId=LTAIwkKSLcUfI2u4&Signature=KYhM4NmP86NssidHvxIweHs%2F4Zo%3D",\
        // "HD": "https://outin-3b194cda86c011ed86b800163e1c94a4.oss-cn-shanghai.aliyuncs.com/0b226b308c2f71ed800e0764a3fc0102/2a5375161db94f2fb3b7d85ff694ffb1-6fc7793c1b9f8b0c7648ef4014aaf823-hd.mp4?Expires=1672899133&OSSAccessKeyId=LTAIwkKSLcUfI2u4&Signature=Es2licNuEf5mnDBG4VtuAGbTf8k%3D"}',

        width: "100%",
        height: "100%",
        // 以下可选设置
        // cover: this.cover_url, // 封面
        qualitySort: "asc", // 清晰度排序

        mediaType: "video", // 返回音频还是视频
        autoplay: false, // 自动播放
        isLive: false, // 直播
        rePlay: false, // 循环播放
        preload: true,
        controlBarVisibility: "hover", // 控制条的显示方式：鼠标悬停
        useH5Prism: true, // 播放器类型：html5
      },
      function (player) {
        console.log("播放器创建成功");
        // player.on("sourceloaded", function (params) {
        //   var paramData = params.paramData;
        //   var desc = paramData.desc;
        //   var definition = paramData.definition;
        //   // 获取清晰度组件并调用清晰度组件的 setCurrentQuality 设置清晰度
        //   player
        //     .getComponent("QualityComponent")
        //     .setCurrentQuality(desc, definition);
        // });
      }
    );
    this.setData();
  },
  beforeDestroy() {
    document.removeEventListener("visibilitychange", this.visibili);
  },
  watch: {
    play_status(newVal) {
      console.log("视频播放状态", newVal);
      console.log("状态", this.play_status);
      console.log("时长", this.totaltime);
      console.log("判断", isNaN(this.totaltime));
      if (newVal == true && !isNaN(this.totaltime)) {
        console.log("watch中的计时开始");
        this.timestart();
      } else {
        this.timeover();
      }
    },
    play_end(newval) {
      console.log("视频结束", newval);
      if (this.timecnt >= this.totaltime - 1) {
        this.centerDialogVisible = true;
      }
    },
    $route(to, from) {
      this.$router.go(0);
    },
  },
  methods: {
    logout() {
    
      cookie.remove("user_token");
      cookie.remove("user_id");
      cookie.remove("user_name");
      cookie.remove("user_gold");
      localStorage.removeItem("Authorization");
      console.log("退出");
      this.$router.push("/");
      
    },

    setData() {
      this.player.on("play", this.handlePlay);
      this.player.on("ended", this.handleEnd);
      this.player.on("pause", this.handlePause);
      if (cookie.get("user_token") != null) {
        this.islogin = true;
        console.log("login");
      } else {
        this.islogin = false;
        console.log("no login");
      }

      console.log("cookie:", document.cookie);
      this.chapterVideoList = JSON.parse(this.$route.query.chapterlist);
      this.chapter_new_list = this.chapterVideoList;
      console.log("章节列表", this.chapterVideoList);
      this.myc_index = this.$route.query.mychapter_index;
      this.myl_index = this.$route.query.mylesson_index;
      this.course_id = this.$route.params.courseid;
    },
    handlePlay(e) {
      console.log("视频播放");
      this.totaltime = this.player.getDuration();
      console.log("视频时长", this.totaltime);
      if (!isNaN(this.totaltime)) {
        this.play_status = true;
        this.play_end = false;
      } else {
        this.play_status = false;
      }
    },
    handlePause(e) {
      console.log("视频暂停");
      this.play_status = false;
    },
    handleEnd(e) {
      console.log("视频结束");
      this.play_end = true;
    },
    handleOpen(key, keypath) {
      console.log(key, keypath);
    },
    handleClose(key, keypath) {
      console.log(key, keypath);
    },

    visibili() {
      console.log("窗口可视性：" + document.webkitVisibilityState);
      if (document.webkitVisibilityState == "hidden") {
        console.log("暂停");
        this.timeover();
        this.player.pause();
      } else {
        console.log("可视");
        if (this.play_status == true) {
          console.log("继续");
          this.timestart();
        }
      }
    },
    // onConnectionChange() {
    //   const { rtt, downlink, effectiveType, saveData } = navigator.connection;
    //   console.log(`有效网络连接类型: ${effectiveType}`);
    //   console.log(`估算的下行速度/带宽: ${downlink}Mb/s`);
    //   console.log(`估算的往返时间: ${rtt}ms`);
    //   console.log(`打开/请求数据保护模式: ${saveData}`);
    //   if (effectiveType == "4g") {
    //     console.log("快速网络");
    //     this.setStreamLevel('HD');
    //   } else if (effectiveType == "3g") {
    //     console.log("普速网络");
    //     this.setStreamLevel('SD');
    //   } else if (effectiveType == "2g") {
    //     console.log("慢速网络");
    //     this.setStreamLevel('FD');
    //   }
    // },
    timestart() {
      console.log("开始计时");
      this.timer = setInterval(this.valChange, 1000);
    },
    valChange() {
      this.timecnt++;
      console.log(this.timecnt);
      console.log(this.randomNum);
      if (!this.check && this.timecnt > this.randomNum) {
        console.log("检查");
        this.check = true;
        this.checkDialog = true;
        this.timeover();
        this.player.pause();
      }
    },
    confirmcheck() {
      this.checkDialog = false;
      this.timeover();
      this.player.play();
    },
    timeover() {
      console.log("计时暂停");
      clearInterval(this.timer);
    },
    jumpTo(courseid, lessonid, chapterindex, lessonindex) {
      this.$router.replace({
        path: `/course/${courseid}/${lessonid}`,
        query: {
          date: new Date().getTime(),
          chapterlist: JSON.stringify(this.chapterVideoList),
          mychapter_index: chapterindex,
          mylesson_index: lessonindex,
        },
      });
    },
    confirmlesson() {
      this.centerDialogVisible = false;
      console.log("提交api");
      videoApi
        .addLessonCom(this.$route.params.pid, cookie.get("user_id"))
        .then((response) => {
          console.log("成功提交");
          console.log(response.data);
        });
      this.jumpTo(
        this.course_id,
        this.$route.params.pid,
        this.$route.query.mychapter_index,
        this.$route.query.mylesson_index
      );
    },
    // getVideo() {
    //   videoApi.getVideo(this.$route.params.pid).then((response) => {
    //     console.log("视频api回答");
    //     console.log(response.data);
    //     this.cover_url = response.data.videoBase.coverURL;
    //     console.log("视频封面", this.cover_url);
    //     this.totaltime = response.data.videoBase.duration;
    //     let infolist = [];
    //     infolist = response.data.playInfoList;
    //     console.log("infolist", infolist.length);
    //     for (let i = 0; i < infolist.length; i++) {
    //       let level = infolist[i].definition;
    //       console.log("typeof level", typeof level);
    //       let srcurl = infolist[i].playURL;
    //       console.log("srcurl", srcurl);
    //       this.source_url[level] = srcurl;
    //     }
    //     this.player_url = JSON.stringify(this.source_url);

    //   });
    // },
    getLessonProgress() {
      videoApi
        .getLessonProgress(this.$route.params.courseid, cookie.get("user_id"))
        .then((response) => {
          console.log("获得课程成绩1");
          this.over_list = response.data;
          console.log(this.over_list);
          this.chapter_new_list = this.chapterVideoList;
          console.log("获得成绩时的章节列表", this.chapter_new_list);
          for (let j = 0; j < this.chapter_new_list.length; j++) {
            let chapte = this.chapter_new_list[j];

            if (chapte.chapter_socre == undefined) {
              chapte["chapter_socre"] = 0;
              chapte["chapter_over_score"] = 0;
            }
            if (chapte.chapter_socre) {
              chapte.chapter_socre = 0;
              chapte.chapter_over_score = 0;
            }
            // console.log("chapter_socre", chapte.chapter_socre);
            // console.log("chapter_over_score", chapte.chapter_over_score);
          }

          console.log("开始循环数据");

          for (let j = 0; j < this.chapter_new_list.length; j++) {
            let chapte = this.chapter_new_list[j];
            
            for (let k = 0; k < chapte.lessonList.length; k++) {
          
                let lesson = chapte.lessonList[k];
                 if (
                  this.myl_index == lesson.lesson_index &&
                  this.myc_index == chapte.chapter_index
                ) {
                  this.myl_title = lesson.lesson_title;
              
                }
    
                 if (lesson.lesson_over == undefined) {
                  lesson["lesson_over"] = false;
                  // console.log("lesson创建over", lesson.lesson_over);
                }
                chapte.chapter_socre += lesson.lesson_score;
              for (let i = 0; i < this.over_list.length; i++) {
              
                if (
                  chapte.chapter_index == this.over_list[i].chapter_index &&
                  lesson.lesson_index == this.over_list[i].lesson_index
                ) {
                  chapte.chapter_over_score += lesson.lesson_score;
                  lesson.lesson_over = true;
                  // console.log("success", lesson.lesson_index);
                 
                }
              }
            }
          }

          console.log(this.chapter_new_list);
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
  float: right;
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
  height: 60px;
  color: #3d4059;
  background: #3d84ff;
  box-shadow: 0 2px 8px 0 rgb(0 0 0 / 10%);
  z-index: 99;
  position: fixed;
  width: 100%;
  padding: 0 62px;
  left: 0;
}
.el-header .banner {
  margin: 0 auto;
  min-width: 1200px;
  height: 60px;
  position: relative;
}
.content {
  height: calc(100vh - 90px);
}
.c-999 {
  color: #999;
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
.el-col-12 {
  max-width: 20%;
  flex: 0 0 50%;
}
.el-row {
  display: flex;
  flex-wrap: wrap;
  position: relative;
  box-sizing: border-box;
  justify-content: flex-end;
}
.video-study {
  height: 100%;
  overflow: hidden;
}
.video-study .box-content {
  margin-top: 60px;
  position: relative;
  min-width: 1200px;
}
.video-study .box-content .box-left {
  width: calc(100% - 340px);
  height: 100%;
  background: #000;
  position: relative;
}
.video-study .box-content .box-left .left-top {
  background: #f6f6f6;
  box-shadow: inset -1px 0 0 0 #ededed;
  right: 340px;
  height: 40px;
  line-height: 40px;
  position: absolute;
  left: 0;
  top: 0;
  padding: 0 35px 0 60px;
  z-index: 2;
  width: 100%;
  box-sizing: border-box;
}
.clearfix {
  clear: both;
  zoom: 1;
}
.fl {
  float: left;
}
.bak {
  /* margin-top: 10px; */
  height: 40px;
  width: 90px;
  font-size: 20px;
  color: white;
}
.word {
  font-size: 14px;
}
.el-header .banner .source-con {
  position: absolute;
  left: 50%;
  text-align: center;
  transform: translateX(-50%);
  -webkit-transform: translateX(-50%);
  -moz-transform: translateX(-50%);
  -ms-transform: translateX(-50%);
  -o-transform: translateX(-50%);
  top: 0;
  margin-top: 10px;
}
.el-header .banner .source-con .source-name {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 300px;
  color: #fff;
  font-size: 16px;
}
.el-header .banner .header_userInfo {
  float: right;
  margin: 10px 0 0 30px;
  height: 26px;
  border-radius: 2px;
  line-height: 26px;
  width: 36px;
  position: relative;
  cursor: pointer;
  z-index: 51;
  padding-bottom: 24px;
}
.el-header .banner .back {
  color: #fff;
  text-decoration: none;
  font-size: 14px;
  line-height: 60px;
  opacity: 0.75;
  display: inline;
  cursor: pointer;
}
.el-header .banner .menudivright-r-div {
  float: right;
}
.el-header .banner .menudivright-r-div .Patternbtn-div {
  float: left;
  width: 80px;
  text-align: center;
  padding-top: 8px;
  cursor: pointer;
  height: 60px;
  box-sizing: border-box;
}
.el-header .banner .menudivright-r-div .morebtn-div {
  width: 80px;
  height: 60px;
  text-align: center;
  position: relative;
  cursor: pointer;
  float: left;
  padding-top: 8px;
  box-sizing: border-box;
}
.el-header .banner .menudivright-r-div .InformationCenter-div {
  margin-left: 76px;
  margin-right: 30px;
  position: relative;
}
.fl {
  float: left;
}
.video-study .box-content .box-left .left-top .videotop_lesson,
.video-study .box-content .box-left .left-top .videotop_lesson_tit {
  height: 40px;
  line-height: 40px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  display: inline-block;
  font-size: 12px;
  color: #2a2a2a;
}
.video-study .box-content .box-left .left-top .videotop_lesson_tit {
  max-width: 400px;
  margin-left: 5px;
}
.fr {
  float: right;
  display: inline;
}
.video-study .box-content .box-left .left-top .videotop_all ul li {
  float: left;
  margin: 6px 5px 0 0;
  cursor: pointer;
  list-style: none;
  height: 30px;
  line-height: 30px;
  font-size: 12px;
  text-align: right;
  padding-left: 14px;
  padding-right: 10px;
}
.c777 {
  color: #777;
}
.video-study .box-content .box-right {
  position: absolute;
  height: 100%;
  top: 0;
  bottom: 0;
  right: 0;
  width: 340px;
}
.video-study .box-content .box-right .newListTest {
  padding: 0 15px;
  overflow: hidden;
  clear: both;
}
.video-study .box-content .box-right .newListTest li {
  margin-top: 5px;
  height: 80px;
  list-style: none;
  float: left;
  width: 100px;
  text-align: center;
  line-height: 30px;
  font-size: 12px;
  color: #bfbfbf;
  cursor: pointer;
}
.video-study .box-content .box-right .newListTest li .att {
  font-size: 12px;
  color: rgb(48, 97, 202);
  display: inline-block;
  line-height: 19px;
  margin-top: 10px;
}
.video-study .box-content .box-right .topicTitle {
  width: 340px;
  height: 40px;
  background: #f6f6f6;
}
.video-study .box-content .box-right .topicTitle ul {
  margin-left: 20px;
}
.video-study .box-content .box-right .topicTitle ul .tabTitle {
  font-size: 14px;
  color: #2a2a2a;
  line-height: 40px;
}
li,
ol,
ul {
  list-style: none;
}
</style>