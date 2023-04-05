<template>
  <el-dialog @open="open" v-model="data.editFormVisible" :close-on-press-escape="false" :show-close="false" title="编辑角色" :align-center="true">
    <el-form :model="form">
       <el-form-item label="角色名" label-width="140px" :required="true">
        <el-input v-model.trim="form.roleName" placeholder="请输入角色名"/>
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
const data=defineProps({
  editFormVisible:{type:Boolean,default:false},
  roleName:{type:String,default:""}
})
const form=reactive({
  roleName:""
})
const open=()=>{
 form.roleName=data.roleName
}
const emits=defineEmits(['confirm',"cancel"])
const cancel=()=>{
  emits("cancel")
}
const confirm=()=>{
   emits("confirm",form)
}
</script>
<style scoped>

</style>
