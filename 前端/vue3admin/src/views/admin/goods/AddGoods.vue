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
    <el-form-item label="商品标题" prop="title">
      <el-input v-model.trim="ruleForm.title" placeholder="请输入标题"/>
    </el-form-item>
    <el-form-item label="商品主图" prop="img">
      <el-upload
          v-model="ruleForm.img"
          class="upload-demo"
          :action="CONFIG.SERVER_URL+'/admin/file/img/upload_goods_img'"
          name="file"
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          :on-exceed="handleExceed"
          list-type="picture"
          :limit="1"
      >
        <el-button type="primary">选择图片</el-button>
        <template #tip>
          <div class="el-upload__tip">
            请上传jpg png 格式图片 最多上传一张
          </div>
        </template>
      </el-upload>
    </el-form-item>
    <el-form-item label="价格" prop="price">
      <el-input-number v-model="ruleForm.price" :precision="2" :step="1" :min="0"/>
    </el-form-item>
    <el-form-item label="库存" prop="stock">
      <el-input-number v-model="ruleForm.stock" :precision="0" :step="1" :min="0"/>
    </el-form-item>
    <el-form-item label="是否上架" prop="status" style="display: flex;align-items: center">
      <div class="mb-2 flex items-center text-sm">
        <el-radio-group v-model="ruleForm.status" class="ml-4">
          <el-radio label="1" size="large">上架</el-radio>
          <el-radio label="0" size="large">下架</el-radio>
        </el-radio-group>
      </div>
    </el-form-item>
    <el-form-item label="分类" prop="cid" v-if="data.goodsCategory.length>0">
      <el-tree-select v-model="ruleForm.cid" :data="data.goodsCategory" :default-expand-all="true"
                      :check-strictly="true"/>
    </el-form-item>
    <el-form-item label="详情" prop="content">
      <!--富文本编辑器-->
      <WangEditor ref="wangEditor"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">提交</el-button>
    </el-form-item>
  </el-form>
</template>
<script lang="ts" setup>
import {ref,reactive,onMounted} from "vue"
import {CONFIG} from "../../../config/config";
import type {UploadProps, UploadUserFile} from 'element-plus'
import {ElError, ElSuccess, ElWarning} from "../../../utils/msg";
import router from "../../../router";
import type {FormInstance, FormRules} from 'element-plus'
import WangEditor from "../../../components/WangEditor.vue"
import request from "../../../utils/request"
const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const wangEditor = ref(null)
const ruleForm = reactive({
  title: '',
  img: "",
  price: "99.99",
  stock: 100,
  cid: "0",
  // 内容 HTML
  content: "",
  status: "1"
})
const data = reactive({
  goodsCategory: []
})
const getData = async () => {
  const res: any = await request.post("/admin/goods/category/list")
  const goodsCategory: any = buildTree(res)
  data.goodsCategory = goodsCategory
  if (data.goodsCategory.length > 0) {
    //@ts-ignore
    ruleForm.cid = data.goodsCategory[0].value
  }
}

onMounted(() => {
  getData()
})
const handleSuccess: UploadProps['onSuccess'] = (response: any, uploadFile, uploadFiles) => {
  if (response.code == 200) {
    ElSuccess("上传成功")
    ruleForm.img = response.data.fileName
  } else {
    ElError(response.msg)
  }
}
const handleExceed=()=>{
  ElWarning("暂只支持一张主图请先删除原主图再上传")
}
const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
  request.post("/admin/file/img/delete", {fileName: ruleForm.img}).then((res: any) => {
    ruleForm.img = ""
  })
}
const rules = reactive<FormRules>({
  title: [
    {required: true, message: '请输入标题', trigger: 'blur'},
    {min: 2, max: 500, message: '', trigger: 'blur'},
  ],
  price: [
    {required: true, message: '请输入价格', trigger: 'blur'},
  ],
  stock: [
    {required: true, message: '请输入库存', trigger: 'blur'},
  ],
  img: [
    {required: true, message: '请上传商品主图', trigger: 'blur'},
  ],
  status: [
    {required: true, message: '', trigger: 'blur'},
  ],
  content: [
    {required: true, message: '商品详情内容不能为空', trigger: 'blur'},
  ],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  //@ts-ignore
  ruleForm.content = wangEditor.value.valueHtml
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post("/admin/goods/add", ruleForm).then((res: any) => {
        if (res.code == 200) {
          ElSuccess("添加成功")
          router.push("/admin/goods/list")
        } else {
          ElError("添加失败请重试")
        }
      })
    } else {
      console.log('error submit!', fields)
    }
  })
}
const buildTree = (list: Array<object>) => {
  let children: Array<object> = []
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
