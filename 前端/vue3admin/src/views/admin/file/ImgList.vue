<template>
  <div class="btn-box">
    <el-button type="primary" :icon="Checked" @click="selectAll()">全选</el-button>
    <el-button type="primary" :icon="Remove" @click="selectNotAll()">全不选</el-button>
    <el-button type="primary" :icon="Delete" @click="showDialog">删除</el-button>
  </div>
  <div class="demo-image">
    <div v-for="(item,index) in data.files" class="block">
      <div>
        <div style="width:80%;text-align: left">
          <el-checkbox v-model="data.files[index].checked" size="small" :border="true"/>
        </div>
        <div>
          <el-image style="max-width: 80%;max-height:500px" :src="CONFIG.SERVER_URL+'/upload/img/goods/'+item.name"/>
        </div>
      </div>
    </div>
  </div>
  <el-container>
    <el-pagination
        v-if="data.total>0"
        background
        layout="prev, pager, next,total"
        :total="data.total" :page-size="data.pageSize"
        v-model:current-page="data.currentPage"
        @current-change="handleCurrentChange"
    />
  </el-container>
  <Dialog msg="确定删除选中的图片吗?" :centerDialogVisible="data.centerDialogVisible" @confirm="del"
          @cancel="data.centerDialogVisible=false"/>
</template>

<script lang="ts" setup>
import {reactive,onMounted} from "vue"
import {CONFIG} from "../../../config/config";
import {ElError, ElSuccess} from "../../../utils/msg";
import {Delete, Checked, Remove} from "@element-plus/icons-vue"
import Dialog from "../../../components/Dialog.vue"
import request from "../../../utils/request";
const data:any = reactive({
  centerDialogVisible: false,
  files: [],
  total: 0,
  currentPage: 1,
  pageSize: 40
})
const selectAll = () => {
  for (let i = 0; i < data.files.length; i++) {
    //@ts-ignore
    data.files[i].checked = true
  }
}
const selectNotAll = () => {
  for (let i = 0; i < data.files.length; i++) {
    //@ts-ignore
    data.files[i].checked = false
  }
}
const getImg = async () => {
  const res: any = await request.post("/admin/file/img/list?currentPage=" + data.currentPage + "&pageSize=" + data.pageSize)
  data.total = res.total
  let arr: any = []
  res.img.forEach((name: any) => {
    arr.push({name: name, checked: false})
  })
  data.files = arr
}
const handleCurrentChange = (val: number) => {
  data.currentPage = val
  getImg()
}
onMounted(() => {
  getImg()
})
const showDialog = () => {
  let count = 0
  data.files.filter((item: any) => {
    if (item.checked) {
      count++
    }
  })
  if (count == 0) {
    ElError("请勾选要删除的图片")
  } else {
    data.centerDialogVisible = true
  }
}
const del = async () => {
  let arr: any = []
  data.files.filter((item: any) => {
    if (item.checked) {
      arr.push(item.name)
    }
  })
  const res: any = await request.post("/admin/file/img/batch_delete", arr)
  if (res.code == 200) {
    ElSuccess("删除成功")
    data.centerDialogVisible = false
    getImg()
    data.currentPage = 1
  } else {
    ElError("删除失败请重试")
  }
}
</script>

<style scoped>
.demo-image .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  display: inline-block;
  width: 20%;
  box-sizing: border-box;
  vertical-align: top;
}

.demo-image .block:last-child {
  border-right: none;
}

.demo-image .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}

.el-checkbox {
  border-color: deepskyblue;
}
</style>
