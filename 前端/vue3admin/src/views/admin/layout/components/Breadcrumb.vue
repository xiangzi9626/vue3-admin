<template>
  <el-breadcrumb separator="/">
    <el-breadcrumb-item v-for="(item,index) in breadcrumb" :key="index">
      <span class="span">{{item.meta.title}}</span>
    </el-breadcrumb-item>
   </el-breadcrumb>
</template>
<script setup lang="ts">
import router from "../../../../router";
defineProps(["breadcrumb"])
const nav=(item:any)=>{
  router.push(item.meta.url)
}
</script>
<style scoped>
.el-breadcrumb{
  color: white;
  margin-left: 20px;
}
.el-breadcrumb *{
  color:gray;
}
.span{
  cursor: text;
}
</style>
