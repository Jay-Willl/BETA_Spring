<template>
  <!-- <el-container>
    <el-header>
      <section class="container">
        <a href="#" title="saki" id="logo">
          <img src="./assets/img/logo.jpg" alt="saki" />
        </a>
        <div class="linkbox">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a class="top">首页</a>
            </router-link>
            <router-link to="/course" tag="li" active-class="current">
              <a class="top">课程</a>
            </router-link>
          </ul>
        </div>

        <div class="search-box">
          <el-input
            placeholder="请输入内容"
            v-model="input"
            class="searchClass"
          >
            <template #append>
              <el-button
                icon="search"
                style="border-color: #00000029; background-color: white"
              />
            </template>
          </el-input>
        </div>
        <el-button class="login" type="primary" round>注册/登录</el-button>
      </section>
    </el-header>
   
  </el-container> -->
   <router-view ></router-view>
</template>
<script>
export default {};
</script>

<style scoped>

</style>
