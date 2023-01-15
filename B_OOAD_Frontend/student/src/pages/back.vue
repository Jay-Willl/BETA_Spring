<template>
    
</template>

<script>
export default {
    data(){
        this.$router.replace(
        {
        path:`/course/${this.$route.params.courseid}/${this.$route.params.pid}`
        }
        
      )
      return{

      }
    },
    methods:{}
}
</script>

<style scoped>

</style>