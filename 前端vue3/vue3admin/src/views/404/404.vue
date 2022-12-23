<template>
<div class="div1">
  <div><h1>404</h1></div>
  <div><h4 style="color: gray">页面不存在</h4></div>
  <router-link :to="{path:'/admin/home'}">返回</router-link>
</div>
</template>

<script setup lang="ts">

</script>
<style scoped>
.div1{
  width: 100%;
  height: 100vh;
  text-align: center;
}
</style>
