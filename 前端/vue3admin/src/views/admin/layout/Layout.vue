<template>
  <div class="common-layout">
    <el-container>
      <el-aside :width="data.isFold?'60px':'200px'">
        <div class="logo-box">
          <span><img src="../../../assets/logo.png"/></span>
          <span>&nbsp;vue3-admin</span>
        </div>
        <!--左侧菜单-->
        <el-scrollbar>
        <el-menu
            class="el-menu-vertical-demo"
            background-color="#545c64"
            text-color="#fff"
            :default-active="data.defaultActive"
            :collapse="data.isFold">
          <Menu :menus="data.menus"/>
        </el-menu>
        </el-scrollbar>
      </el-aside>
      <!--右侧-->
      <el-container>
        <!--右侧头部-->
        <el-header>
          <div class="header-box">
            <div class="header-box1">
              <el-icon @click="fold">
                <component class="icons" :is="data.isFold?Expand:Fold"/>
              </el-icon>
              <!---面包屑------>
              <Breadcrumb :breadcrumb="breadcrumb"/>
            </div>
            <!---头像---->
            <div class="dropdown">
              <el-dropdown>
        <span class="el-dropdown-link">
          <img src="../../../assets/images/user-default.png"/>
          &nbsp;{{ data.username }}<el-icon class="el-icon--right"><arrow-down/></el-icon>
        </span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item style="color:#42b983" :icon="Lock" @click="data.dialogFormVisible=true">修改密码
                    </el-dropdown-item>
                    <el-dropdown-item style="color: #42b983" :icon="SwitchButton" @click="logout">退出</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
          <div class="tag-box">
            <!--Tags-->
            <Tags></Tags>
          </div>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
    <Dialog :msg="data.dialogMsg" :centerDialogVisible="data.centerDialogVisible" @confirm="exit"
            @cancel="data.centerDialogVisible=false"/>
    <FormDialog :dialogFormVisible="data.dialogFormVisible" @cancel="data.dialogFormVisible=false"
                @modifyPassword="modifyPassword"/>
  </div>
</template>

<script setup lang="ts">
import {onBeforeRouteUpdate, useRoute} from "vue-router";
import {reactive, computed, onMounted} from "vue"
import {ElError, ElSuccess, ElWarning} from "../../../utils/msg";
import {menuStore} from "../../../store/menuStore";
import router from "../../../router";
import {Expand,Fold,Lock,SwitchButton,ArrowDown} from "@element-plus/icons-vue"
import Dialog from '../../../components/Dialog.vue'
import FormDialog from "./components/FormDialog.vue"
import Breadcrumb from "./components/Breadcrumb.vue"
import Menu from './components/Menu.vue'
import Tags from './components/Tags.vue'
import request from "../../../utils/request";
import pinia from "../../../store";
const data = reactive({
  username: sessionStorage.getItem("username"),
  isFold: false,
  centerDialogVisible: false,
  dialogMsg: "",
  dialogFormVisible: false,
  //@ts-ignore
  menus:menuStore().menus,
  defaultActive: router.currentRoute.value.fullPath
})
onBeforeRouteUpdate(to => {
  data.defaultActive = to.path
})
const breadcrumb = computed(() => {
  const arr: any = useRoute().matched.filter(v => v.path != "/admin" && v.path != "/admin/home")
  arr.unshift({
    meta: {title: "首页", url: "/admin/home"}
  })
  return arr
})
const fold = () => {
  data.isFold = !data.isFold
}
const logout = () => {
  data.centerDialogVisible = true
  data.dialogMsg = "确定退出吗?"
}
const exit = () => {
  request.post("/admin/logout").then((res: any) => {
    if (res.code == 200) {
      sessionStorage.setItem("token", "")
      window.sessionStorage.clear()
      menuStore().setMenus([])
     window.location.href="/admin/login"
    }
  })
}
const modifyPassword = (e: any) => {
  if (e.oldPassword == "" || /^\s+$/.test(e.oldPassword)) {
    ElWarning("请输入旧密码");
    return false;
  }
  if (e.newPassword == "" || /^\s+$/.test(e.newPassword)) {
    ElWarning("请输入新密码");
    return false;
  }
  if (e.confirmPassword == "" || /^\s+$/.test(e.confirmPassword)) {
    ElWarning("请输入确认密码");
    return false;
  }
  if (e.newPassword != e.confirmPassword) {
    ElWarning("新密码与确认密码不一致");
    return false;
  }
  request.post("/admin/password", e).then((res: any) => {
    if (res.code == 200) {
      ElSuccess("密码修改成功重新登录");
      sessionStorage.setItem("token", "");
      router.push({path: "/admin/login"});
    } else {
      ElError(res.msg)
    }
  })
}
</script>

<style scoped lang="scss">
.el-container {
  height: 100vh;
  margin: 0;
  padding: 0;
}

.el-aside {
  height: 100vh;
  overflow: hidden;
  transition: width 0.4s;
  background: #545c64;

  .logo-box {
    width: 200px;
    white-space: nowrap;
    cursor: pointer;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: white;
    font-weight: 600;

    img {
      max-width: 30px;
      max-height: 30px;
    }
  }
}

.el-menu {
  background: #545c64;
  color: white;
  padding-bottom:150px;
}

.el-header {
  height: auto;

  .header-box {
    height: 60px;
    color: #545c64;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .tag-box {
    width: 100%;
    padding: 2px 0;
  }

  .header-box1 {
    padding: 2px;
    cursor: pointer;
    display: flex;
  }

  img {
    max-width: 30px;
    max-height: 30px;
  }
}

.el-dropdown-link {
  color: #545c64;
  display: flex;
  align-items: center;
}

.el-dropdown-menu {
  background: gainsboro;
  height: 110px;
}
</style>
