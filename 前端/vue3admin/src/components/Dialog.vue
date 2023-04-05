<template>
 <el-dialog
      v-model="props.centerDialogVisible"
      :show-close="false"
      :close-on-press-escape="false"
      title="提示"
      width="30%"
      :align-center="true"
     >
    <span>{{msg}}</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="confirm"
        >确定</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
const props=defineProps({
  msg:{type:String,default:""},
  centerDialogVisible:{type:Boolean,default:false}
})
const emits=defineEmits(['confirm',"cancel"])
const cancel=()=>{
  emits("cancel")
}
const confirm=()=>{
  emits("confirm")
}
</script>

<style scoped>

</style>
