<template>
  <template v-for="(menu,i) in menus" :key="i">
          <el-sub-menu :index="menu.id+''" v-if="menu.children && menu.children.length>0 && menu.status===1">
            <template #title>
              <el-icon>
                <i :class="menu.icon"></i>
              </el-icon>
              <span>{{menu.title}}</span>
            </template>
            <Menu :menus="menu.children"/>
          </el-sub-menu>
     <el-menu-item :index="menu.url" @click="menuClick(menu)" v-else-if="menu.children && menu.children.length===0 && menu.status===1">
            <el-icon>
              <i :class="menu.icon"></i>
            </el-icon>
       <span>{{menu.title}}</span>
          </el-menu-item>
     </template>
</template>
<script setup lang="ts">
import router from "../../../../router";
import {menuStore} from "../../../../store/menuStore";
import request from "../../../../utils/request";
import {reactive} from "vue";
defineProps(["menus"])
const data=reactive({
  menus:[]
})
const menuClick=(menu:any)=>{
  router.push(menu.url)
}
</script>

<style scoped>

</style>
