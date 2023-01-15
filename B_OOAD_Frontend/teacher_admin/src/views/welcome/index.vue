<script setup lang="ts">
import { onMounted } from "vue";

defineOptions({
  name: "Welcome"
});
onMounted(() => {
  console.log("username store", localStorage.getItem("username"));
  console.log("token store", localStorage.getItem("token"));
  console.log("role store", localStorage.getItem("roles"));
});

</script>

<template>
  <h1>Welcome to Teacher & Admin Site!</h1>
</template>
