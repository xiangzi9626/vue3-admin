<template>
  <el-scrollbar height="30px">
    <div class="scrollbar-flex-content">
      <el-tag effect="dark" :type="isCurrent('/admin/home')?'':'info'" @click="jump('/admin/home')">首页</el-tag>

      <el-tag
          v-for="(tag,index) in data.tags"
          :key="tag.path"
          closable
          @close="closeTag(index)"
          @click="jump(tag.path)"
          effect="dark"
          :type="isCurrent(tag.path)?'':'info'"
      >
        {{ tag.title }}
      </el-tag>
    </div>
  </el-scrollbar>
</template>
<script setup lang="ts">
import {onBeforeRouteUpdate} from "vue-router";
import router from "../../../../router";
import {tagStore} from "../../../../store/tags";
import {reactive,onBeforeMount} from "vue"
let data:any = reactive({
  tags: tagStore().tags
})
const jump = (path: string) => {
  router.push(path)
}
const isCurrent = (path: string) => {
  if (router.currentRoute.value.fullPath == path) {
    return true
  } else {
    return false
  }
}
const closeTag = (index: number) => {
  tagStore().tags.splice(index, 1)
  let path: string = ""
  if (index == 0) {
    path = "/admin/home"
  } else {
    //@ts-ignore
    path = data.tags[index - 1].path
  }
  router.push(path)
}
onBeforeMount(() => {
  let currentRoute = router.currentRoute.value
  if (currentRoute.fullPath !== "/admin/home") {
    let obj = {path: currentRoute.fullPath, title: currentRoute.meta.title}
    tagStore().pushTags(obj)
    data.tags = tagStore().tags
  }
})
onBeforeRouteUpdate(to => {
  if (to.fullPath !== "/admin/home") {
    let obj = {path: to.fullPath, title: to.meta.title}
    tagStore().pushTags(obj)
    data.tags = tagStore().tags
  }
})
</script>
<style scoped>
.scrollbar-flex-content {
  width: 100%;
  height: 30px;
  display: flex;
}

.el-tag {
  margin-right: 5px;
  padding: 0 5px;
  cursor: pointer;
}
</style>
