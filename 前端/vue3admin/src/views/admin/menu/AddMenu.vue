<template>
  <el-form
      ref="ruleFormRef"
      :model="ruleForm"
      :rules="rules"
      label-width="120px"
      class="demo-ruleForm"
      :size="formSize"
      status-icon
  >
    <el-form-item label="菜单名称" prop="title">
      <el-input v-model.trim="ruleForm.title" placeholder="请输入菜单名称"/>
    </el-form-item>
    <el-form-item label="路由path" prop="path">
      <el-input v-model.trim="ruleForm.path" placeholder="请输入路由的path子路由不要加/"/>
    </el-form-item>
    <el-form-item label="上级菜单" prop="pid">
      <el-tree-select v-model="ruleForm.pid" :data="data.menuAll" :default-expand-all="true" :check-strictly="true"/>
    </el-form-item>
    <el-form-item label="图标" prop="icon">
      <el-input v-model="ruleForm.icon" placeholder="请输入图标字符"/>
    </el-form-item>
    <el-form-item label="组件" prop="component">
      <el-input v-model.trim="ruleForm.component" placeholder="请输入组件名称"/>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input-number v-model="ruleForm.sort" :step="1" :min="0" :max="10000"/>
    </el-form-item>
    <el-form-item label="左侧显示" prop="status" style="display: flex;align-items: center">
      <div class="mb-2 flex items-center text-sm">
        <el-radio-group v-model="ruleForm.status" class="ml-4">
          <el-radio label="1" size="large">是</el-radio>
          <el-radio label="0" size="large">否</el-radio>
        </el-radio-group>
      </div>
    </el-form-item>
    <el-form-item label="URL" prop="url">
      <el-input v-model.trim="ruleForm.url" placeholder="路由完整URL 如果是父菜单无需填写"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">提交</el-button>
      <el-button @click="resetForm(ruleFormRef)">重置</el-button>
    </el-form-item>
  </el-form>
</template>
<script lang="ts" setup>
import {ref, reactive, onMounted} from "vue"
import {ElError, ElSuccess} from "../../../utils/msg";
import request from "../../../utils/request"
import router from "../../../router"
import type {FormInstance, FormRules} from 'element-plus'

const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive({
  title: '',
  path: "",
  pid: '0',
  icon: "",
  component: "",
  sort: 0,
  status: "1",
  url: ""
})
const buildRoute = (list: Array<object>) => {
  let children: Array<object> = [{value: "0", label: "顶级菜单"}]
  let deep = (children: Array<object>, list: Array<object>) => {
    list.forEach((item: any) => {
      if (item.children && item.children.length > 0) {
        let obj = {
          value: item.id,
          label: item.title,
          children: []
        }
        children.push(obj)
        // @ts-ignore
        deep(children[children.length - 1].children, item.children)
      } else {
        // @ts-ignore
        children.push({
          value: item.id,
          label: item.title
        })
      }
    })
  }
  deep(children, list)
  return children
}
const data: any = reactive({
  menuAll: []
})
const menuAll = async () => {
  let res: any = await request.post("/admin/menu/all")
  data.menuAll = buildRoute(res)
}
onMounted(() => {
  menuAll()
})
const rules = reactive<FormRules>({
  title: [
    {required: true, message: '请输入菜单名称', trigger: 'blur'},
    {min: 2, max: 500, message: '', trigger: 'blur'},
  ],
  path: [
    {required: true, message: '请输入路由的path', trigger: 'blur'},
    {min: 2, max: 500, message: '', trigger: 'blur'},
  ],
  pid: [
    {
      required: true,
      message: '请选择上级菜单',
      trigger: 'change',
    },
  ],
  icon: [
    {required: true, message: '请输入图标', trigger: 'blur'},
    {min: 2, max: 500, message: '', trigger: 'blur'},
  ],
  sort: [
    {required: true, message: '', trigger: 'blur'},
  ],
  status: [
    {required: true, message: '', trigger: 'blur'},
  ],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post("/admin/menu/add", ruleForm).then((res: any) => {
        if (res.code == 200) {
          ElSuccess("添加成功")
          router.push("/admin/menu/list")
        } else {
          ElError("添加失败请重试")
        }
      })
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
