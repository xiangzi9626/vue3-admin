<template>
  <el-dialog v-model="props.addFormVisible" :close-on-press-escape="false" :show-close="false" title="添加角色" :align-center="true" @close="close">
    <el-form :model="form">
       <el-form-item label="角色名" label-width="140px" :required="true">
        <el-input v-model.trim="form.roleName" autocomplete="off" placeholder="请输入角色名"/>
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
const props=defineProps({
  addFormVisible:{type:Boolean,default:false}
})
const form=reactive({
  roleName:""
})
const emits=defineEmits(['confirm',"cancel"])
const cancel=()=>{
  emits("cancel")
}
const confirm=()=>{
  emits("confirm",form)
}
const close=()=>{
  form.roleName=""
}
</script>
<style scoped>

</style>
