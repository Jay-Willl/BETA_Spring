
<template>
<body>
<el-icon class="bak" @click="$router.back()"><Back /></el-icon>
  <div class="container">
    <div class="form-box">
      <!-- 注册 -->
      <div class="register-box hidden">
        <h1>register</h1>
        <input type="text" v-model="registerForm.user_name" placeholder="用户名" />
        <input type="email" v-model="registerForm.user_email" placeholder="邮箱" />
        <input type="password" v-model="registerForm.user_passwd" placeholder="密码" />
        <input type="password" placeholder="确认密码" />
        <button @click="regist()" >注册</button>
      </div>
      <!-- 登录 -->
      <div class="login-box">
        <h1>login</h1>
        <input type="text" v-model="loginForm.username" placeholder="用户名" />
        <input type="password" v-model="loginForm.password" placeholder="密码" />
        <button @click="login" >登录</button>
      </div>
    </div>
    <div class="con-box left">
      <h2>欢迎来到<span>saki</span></h2>

      <!-- <img src="../images/cat/1.png" alt="" /> -->
      <p>已有账号</p>
      <button id="gologin" @click="slogin()">去登录</button>
    </div>
    <div class="con-box right">
      <h2>欢迎来到<span>saki</span></h2>

      <!-- <img src="../images/cat/2.png" alt="" /> -->
      <p>没有账号？</p>
      <button id="register" @click="sre()">去注册</button>
    </div>
  </div>
</body>
</template>


<script>
import { ElMessage } from 'element-plus'
import { mapMutations } from 'vuex';
import cookie from 'js-cookie';
import loginApi from '/src/api/login';
export default {
  data () {
    return {
      loginForm: {
        username: '',
        password: '',
        // userrole: 'student',
      },
      registerForm:{
        user_name: '',
        user_passwd: '',
        user_email: '',
        user_role: 'student',
      },
      userToken: ''
    };
  },


  methods: {
    ...mapMutations(['changeLogin']),
    login () {
      if (this.loginForm.username === '' || this.loginForm.password === '') {
        ElMessage.error('账号或密码不能为空');
      } else {
        // this.axios({
        //   method: 'post',
        //   url: '/user/login',
        //   data: _this.loginForm
        // }).then(res => {
        //   console.log(res.data);
        //   _this.userToken = 'Bearer ' + res.data.data.body.token;
        //   // 将用户token保存到vuex中
        //   _this.changeLogin({ Authorization: _this.userToken });
        //   _this.$router.push('/home');
        //   alert('登陆成功');
        // }).catch(error => {
        //   alert('账号或密码错误');
        //   console.log(error);
        // });

        loginApi.submitLoginUser(this.loginForm)
          .then(
            response =>{
              console.log(response.data)
              cookie.set('user_token',response.data.token);
              cookie.set('user_id',response.data.user_id);
              cookie.set('user_gold',response.data.user_gold);
              cookie.set('user_name',response.data.user_name);
              console.log('cookie: '+cookie.get('user_token'));
              this.changeLogin({ Authorization: this.userToken });
              this.$router.back();
            }
          ).catch(
            errro =>{
              alert('账号或密码错误');
              console.log(errro);
            }
          )
        
        // let storage = window.localStorage;
        // let a = storage.getItem("Authorization");
        // this.userToken = "hello,world!"
        // this.changeLogin({ Authorization: this.userToken });
        // console.log(a+"   pppp");
        // this.$router.back();
      
      }
    },
    regist (){
      if (this.registerForm.username === '' || this.registerForm.password === ''){
        ElMessage.error('账号或密码不能为空');
      }
      else if (this.registerForm.mail === ''){
        ElMessage.error('邮箱不能为空');
      }
      else{
        loginApi.registerMember(this.registerForm)
          .then(response => {
            if(response.data == 'ok')
          {  this.$message({
                type: 'success',
                message: "注册成功"
              });}else{
                this.$message({
                type: 'success',
                message: "注册失败"
              });
              }
            this.slogin();
          })
      }
    },
    slogin() {
      let form_box = document.getElementsByClassName("form-box")[0];
      let register_box = document.getElementsByClassName("register-box")[0];
      let login_box = document.getElementsByClassName("login-box")[0]; 
      form_box.style.transform = "translateX(0%)";
      register_box.classList.add("hidden");
      login_box.classList.remove("hidden");
    },
    sre() {
      let form_box = document.getElementsByClassName("form-box")[0];
      let register_box = document.getElementsByClassName("register-box")[0];
      let login_box = document.getElementsByClassName("login-box")[0];
      form_box.style.transform = "translateX(80%)";
      login_box.classList.add("hidden");
      register_box.classList.remove("hidden");
    },
   
  },
};
</script>


<style scoped>
.bak{
    height: 100px;
    width: 100px;
     font-size: 30px;
     color: white;

}
* {
  /* 初始化 */
  margin: 0;
  padding: 0;
}
body {
  /* 100%窗口高度 */
  height: 100vh;
  /* 弹性布局 水平+垂直居中 */
  /* display: flex;
  justify-content: center;
  align-items: center; */
  /* 渐变背景 */
  background-image: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
  /* background: linear-gradient(200deg, #00B4DB, #0083B0); */
}
.container {
  background-color: #fff;
  width: 650px;
  height: 415px;
  margin-left: 400px;
  margin-top: 100px;
  border-radius: 5px;
  /* 阴影 */
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
  /* 相对定位 */
  position: relative;
}
.form-box {
  /* 绝对定位 */
  position: absolute;
  top: -10%;
  left: 5%;
  background-color: #339af0;
  width: 320px;
  height: 500px;
  border-radius: 5px;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2;
  /* 动画过渡 加速后减速 */
  transition: 0.5s ease-in-out;
}
.register-box,
.login-box {
  /* 弹性布局 垂直排列 */
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
.hidden {
  display: none;
  transition: 0.5s;
}
h1 {
  text-align: center;
  margin-bottom: 25px;
  /* 大写 */
  text-transform: uppercase;
  color: #fff;
  /* 字间距 */
  letter-spacing: 5px;
}
input {
  background-color: transparent;
  width: 70%;
  color: #fff;
  border: none;
  /* 下边框样式 */
  border-bottom: 1px solid rgba(255, 255, 255, 0.4);
  padding: 10px 0;
  text-indent: 10px;
  margin: 8px 0;
  font-size: 14px;
  letter-spacing: 2px;
}
input::placeholder {
  color: #fff;
}
input:focus {
  color: #ffffff;
  outline: none;
  border-bottom: 1px solid #24199b80;
  transition: 0.5s;
}
input:focus::placeholder {
  opacity: 0;
}
.form-box button {
  width: 70%;
  margin-top: 35px;
  background-color: #f6f6f6;
  outline: none;
  border-radius: 8px;
  padding: 13px;
  color: #4d92c3;
  letter-spacing: 2px;
  border: none;
  cursor: pointer;
}
.form-box button:hover {
  background-color: #4d92c3;
  color: #f6f6f6;
  transition: background-color 0.5s ease;
}
.con-box {
  width: 50%;
  /* 弹性布局 垂直排列 居中 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  /* 绝对定位 居中 */
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}
.con-box.left {
  left: -2%;
}
.con-box.right {
  right: -2%;
}
.con-box h2 {
  color: #8e9aaf;
  font-size: 25px;
  font-weight: bold;
  letter-spacing: 3px;
  text-align: center;
  margin-bottom: 4px;
}
.con-box p {
  font-size: 12px;
  letter-spacing: 2px;
  color: #ffffff;
  text-align: center;
}
.con-box span {
  color: #2f6fcd;
}
.con-box img {
  width: 150px;
  height: 150px;
  opacity: 0.9;
  margin: 40px 0;
}
.con-box button {
  margin-top: 3%;
  background-color: #fff;
  color: #2a3ab7;
  border: 1px solid #b7c6d8;
  padding: 6px 10px;
  border-radius: 5px;
  letter-spacing: 1px;
  outline: none;
  cursor: pointer;
}
.con-box button:hover {
  background-color: #d3b7d8;
  color: #fff;
}
</style>
