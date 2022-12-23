<template>
  <!--编辑用户弹出层-->
  <el-dialog @open="open" v-model="props.editFormVisible" :close-on-press-escape="false" :show-close="false" title="编辑用户资料"
             :align-center="true">
    <el-form>
      <el-form-item label="用户名" label-width="140px">
        <span>{{ userInfo.username }}</span>
      </el-form-item>
      <el-form-item label="手机号" label-width="140px">
        <el-input v-model.trim="userInfo.phone" autocomplete="off" placeholder="请输入手机号"/>
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
import request from "../../../../utils/request"
//@ts-ignore
const props=defineProps({
  user: {},
  editFormVisible: false
})
const data = reactive({
  role: []
})
let userInfo = ref("")
const open = async () => {
  const res: any = await request.post("/admin/rolelist")
  let arr: any = []
  res.forEach((item: any) => {
    arr.push({
      value: item.roleId + "",
      label: item.roleName
    })
  })
  data.role = arr
   userInfo.value=props.user
}
const emits = defineEmits(['confirm', "cancel"])
const cancel = () => {
  emits("cancel")
}
const confirm = () => {
  emits("confirm", userInfo.value)
}
</script>

<style scoped>

</style>
