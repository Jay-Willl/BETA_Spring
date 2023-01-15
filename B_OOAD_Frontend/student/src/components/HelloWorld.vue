<script >
import { ref } from 'vue'

defineProps({
  msg: String,
})

const count = ref(0)

</script>

<!-- <template>
  <div>
    <div class="block">
      <el-carousel interval="4000" type="card" trigger="click" height=400px >
        <el-carousel-item >
          
            <img src="../assets/photo/banner/1.jpg" alt="mainpage" width="900px">
          
          
        </el-carousel-item>
        <el-carousel-item >
         
            <img src="../assets/photo/banner/2.jpg" alt="mainpage" width="900px">      
        </el-carousel-item>
        <el-carousel-item >
  
            <img src="../assets/photo/banner/3.jpg" alt="mainpage" width="900px">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template> -->
<template>
  <el-carousel :interval="4000" type="card" height="200px">
    <el-carousel-item v-for="item in 6" :key="item">
      <h3 text="2xl" justify="center">{{ item }}</h3>
    </el-carousel-item>
  </el-carousel>
</template>


<style scoped>
.el-carousel {
  margin-top: 15px;
  width: 100%;
}
.read-the-docs {
  color: #888;
}
.el-carousel__item{
	width: 80%;
	left: -15%;
}

</style>
