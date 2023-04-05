<template>
<el-container>
  <el-row>
    <el-col class="el-col-24">
      <h2>后 台 登 录</h2>
    </el-col>
    <el-col class="el-col-24">
    <el-form>
      <el-form-item>
        <el-input
            v-model.trim="form.username"
            prop="username"
            placeholder="请输入账号"
            :prefix-icon="User"
            >
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-input
            v-model="form.password"
            prop="password"
            placeholder="请输入密码"
            :prefix-icon="Lock"
            type="password"
        >
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button
            type="primary"
            :disabled="data.disabled"
            @click="login()">{{data.buttonText}}</el-button>
      </el-form-item>
    </el-form>
    </el-col>
  </el-row>
</el-container>
</template>

<script setup lang="ts">
import {reactive} from "vue"
import {User,Lock} from '@element-plus/icons-vue'
import router from "../../router";
import {ElError, ElSuccess, ElWarning} from "../../utils/msg";
import request from "../../utils/request"
const form=reactive({
  username:"",
  password:""
})
const data=reactive({
  disabled:false,
  buttonText:"登 录"
})
const login = ()=>{
  if (form.username=="" || form.password==""){
    ElWarning("请输入账号和密码")
    return false;
  }
  request.post("/admin/login",form).then((res:any)=>{
    if (res.code !== 200){
     ElError(res.msg)
     return false;
   }
    data.disabled=true
    data.buttonText="登 录 中..."
    ElSuccess("登录成功")
    sessionStorage.setItem("token",res.data)
    sessionStorage.setItem("username",form.username)
   router.push("/admin/home")
  })
}
</script>
<style scoped lang="scss">
.el-container{
  height: 100vh;
   display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("../../assets/images/bg.png");
  background-size: cover;
  h2{
    color: white;
    text-align: center;
  }
  .el-button{
    width: 100%;
    border-radius:25px;
  }
  .el-form-item{
    margin-bottom:30px;
  }
  .el-input{
    opacity:0.8;
  }
}
</style>
