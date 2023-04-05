<template>
  <div class="btn-box">
    <el-button type="primary" :icon="Plus"
               @click="data.addFormVisible=true;data.selectCategory=buildTree(data.goodsCategory)">添加</el-button>
  </div>
  <div>
    <el-table
        :data="data.goodsCategory"
        style="width: 100%; margin-bottom: 20px"
        row-key="category_id"
        empty-text="没有数据"
        :border="true"
        :stripe="true"
        default-expand-all
    >
      <el-table-column prop="categoryId" label="ID" sortable/>
      <el-table-column prop="name" label="分类名称" sortable/>
      <el-table-column prop="pid" label="父ID" sortable/>
      <el-table-column label="操作" sortable>
        <template #default="scope">
          <el-popconfirm :title="'确定删除分类 '+scope.row.name+' 吗?'" @confirm="handleDelete(scope.row)"
                         confirm-button-text="确定" cancel-button-text="取消">
            <template #reference>
              <el-icon class="btn">
                <el-tooltip
                    class="box-item"
                    effect="dark"
                    content="删除"
                    placement="top-start"
                >
                  <Delete/>
                </el-tooltip>
              </el-icon>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!--添加弹出层-->
  <el-dialog v-model="data.addFormVisible" title="新增分类" :align-center="true">
    <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
        class="demo-ruleForm"
        :size="formSize"
        status-icon
    >
      <el-form-item label="分类名称" prop="name">
        <el-input v-model.trim="ruleForm.name" placeholder="请输入分类名称"/>
      </el-form-item>
      <el-form-item label="上级分类" prop="pid">
        <el-tree-select v-model="ruleForm.pid" :data="data.selectCategory" :default-expand-all="true"
                        :check-strictly="true"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.addFormVisible=false;resetForm(ruleFormRef)">取消</el-button>
        <el-button type="primary" @click="submitForm(ruleFormRef)">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
import {Delete, Plus} from "@element-plus/icons-vue"
import {ref,reactive,onBeforeMount} from "vue"
import {ElError, ElSuccess} from "../../../utils/msg";
import request from '../../../utils/request'
import type {FormInstance, FormRules} from 'element-plus'
const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const data:any = reactive({
  goodsCategory: [],
  selectCategory: [],
  addFormVisible: false
})
const ruleForm = reactive({
  pid: "",
  name: ""
})
const getData = async () => {
  const res = await request.post("/admin/goods/category/list")
  //@ts-ignore
  data.goodsCategory = res
}
getData()
const rules = reactive<FormRules>({
  pid: [
    {
      required: true,
      message: '请选择上级分类',
      trigger: 'change',
    },
  ],
  name: [
    {required: true, message: '请输入分类名称', trigger: 'blur'},
    {min: 2, max: 500, message: '', trigger: 'blur'},
  ],
})
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post("/admin/goods/category/add", ruleForm).then((res: any) => {
        if (res.code == 200) {
          data.addFormVisible = false
          ruleForm.pid = "0"
          ruleForm.name = ""
          ElSuccess("添加成功")
          getData()
        } else {
          ElError("添加失败请重试")
        }
      })
    } else {
      console.log('error submit!', fields)
    }
  })
}
const handleDelete = (item: any) => {
  request.post("/admin/goods/category/delete", {id: item.categoryId}).then((res: any) => {
    if (res.code == 200) {
      ElSuccess(res.msg)
      getData()
    } else {
      ElError(res.msg)
    }
  })
}
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
const buildTree = (list: Array<object>) => {
  let children: Array<object> = [{value: "0", label: "顶级分类"}]
  let deep = (children: Array<object>, list: Array<object>) => {
    list.forEach((item: any) => {
      if (item.children && item.children.length > 0) {
        let obj = {
          value: item.categoryId + "",
          label: item.name,
          children: []
        }
        children.push(obj)
        // @ts-ignore
        deep(children[children.length - 1].children, item.children)
      } else {
        // @ts-ignore
        children.push({
          value: item.categoryId + "",
          label: item.name
        })
      }
    })
  }
  deep(children, list)
  return children
}
</script>
<style scoped>
.btn-box {
  margin-bottom: 10px;
}

.btn {
  cursor: pointer;
}
</style>
