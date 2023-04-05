<template>
  <el-dialog v-model="props.dialogFormVisible" title="修改密码" :align-center="true">
    <el-form :model="form">
      <el-form-item label="旧密码" label-width="140px">
        <el-input v-model="form.oldPassword" autocomplete="off" placeholder="请输入旧密码"/>
      </el-form-item>
      <el-form-item label="新密码" label-width="140px">
        <el-input v-model="form.newPassword" autocomplete="off" placeholder="请输入新密码"/>
      </el-form-item>
      <el-form-item label="确认新密码" label-width="140px">
        <el-input v-model="form.confirmPassword" autocomplete="off" placeholder="请输入确认密码"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="handelClick">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {reactive} from "vue"
const props=defineProps({
  dialogFormVisible:{type:Boolean,default:false}
})
const form=reactive({
  oldPassword:"",
  newPassword:"",
  confirmPassword:""
})
const emits=defineEmits(["modifyPassword","cancel"])
const cancel=()=>{
  emits("cancel")
}
const handelClick=()=>{
  emits("modifyPassword",form)
}
</script>

<style scoped>

</style>
