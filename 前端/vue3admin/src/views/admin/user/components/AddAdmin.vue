<template>
  <el-dialog @open="open" v-model="props.addFormVisible" :close-on-press-escape="false" :show-close="false" title="新增用户"
             :align-center="true">
    <el-form>
      <el-form-item label="用户名" label-width="140px">
        <el-input v-model.trim="userInfo.username" autocomplete="off" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item label="手机号" label-width="140px">
        <el-input v-model.trim="userInfo.phone" autocomplete="off" placeholder="请输入手机号"/>
      </el-form-item>
      <el-form-item label="密码" label-width="140px">
        <el-input v-model="userInfo.password" autocomplete="off" placeholder="请输入密码"/>
      </el-form-item>
      <el-form-item label="角色" prop="rId" label-width="140px">
        <el-select v-model="userInfo.r_id" class="m-2" placeholder="请选择" size="large">
          <el-option
              v-for="item in data.role"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="confirm">确定</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script setup lang="ts">
import {reactive} from "vue"
import request from "../../../../utils/request"
const props=defineProps({
  addFormVisible: {type:Boolean,default:false}
})
const data:any = reactive({
  role: []
})
let userInfo = reactive({
  username:"",
  phone:"",
  password:"",
  r_id:""
})
const open = async () => {
  const res: any = await request.post("/admin/role/list")
  let arr: any = []
  res.forEach((item: any) => {
    arr.push({
      value: item.roleId + "",
      label: item.roleName
    })
  })
  data.role = arr
  //@ts-ignore
   userInfo.username=""
  //@ts-ignore
  userInfo.phone=""
  //@ts-ignore
  userInfo.password=""
  //@ts-ignore
  userInfo.r_id=""
}
const emits = defineEmits(['confirm', "cancel"])
const cancel = () => {
  emits("cancel")
}
const confirm = () => {
  emits("confirm", userInfo)
}
</script>

<style scoped>

</style>
