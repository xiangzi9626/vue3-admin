<template>
  <div class="btn-box">
    <el-button type="primary" :icon="Plus" @click="data.addFormVisible=true">添加</el-button>
    <el-button type="primary" :icon="Files" @click="exportXlsx">导出</el-button>
  </div>
  <div>
    <el-table
        :data="data.list"
        style="width: 100%; margin-bottom: 20px"
        row-key="id"
        :border="true"
        :stripe="true"
        default-expand-all
        empty-text="没有数据"
        :highlight-current-row="true"
    >
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="username" label="用户名" sortable/>
      <el-table-column prop="phone" label="手机号" sortable/>
      <el-table-column prop="role_name" label="角色" sortable/>
      <el-table-column prop="createTime" label="创建时间" sortable/>
      <el-table-column label="操作" sortable>
        <template #default="scope">
          <el-tooltip
              class="box-item"
              effect="dark"
              content="编辑"
              placement="top-start"
          >
            <el-icon
                @click="showEditDialog(scope.row)">
              <Edit/>
            </el-icon>
          </el-tooltip>
          &nbsp;
          <el-tooltip
              class="box-item"
              effect="dark"
              content="改密"
              placement="top-start"
          >
            <el-icon @click="data.dialogFormVisible=true;form.uid=scope.row.id">
              <Lock/>
            </el-icon>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-if="data.total>0"
        background
        layout="prev, pager, next,sizes,total"
        :total="data.total" :page-sizes="[10, 20, 30, 40]"
        v-model:current-page="data.currentPage"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"/>
  </div>
  <!--修改密码弹出层-->
  <el-dialog v-model="data.dialogFormVisible" title="修改用户密码" :align-center="true">
    <el-form :model="form">
      <el-form-item label="新密码" label-width="140px">
        <el-input v-model="form.newPassword" autocomplete="off" placeholder="请输入新密码"/>
      </el-form-item>
      <el-form-item label="确认新密码" label-width="140px">
        <el-input v-model="form.confirmPassword" autocomplete="off" placeholder="请输入确认密码"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.dialogFormVisible=false">取消</el-button>
        <el-button type="primary" @click="modifyPassword">确定</el-button>
      </span>
    </template>
  </el-dialog>
  <EditAdmin :user="userInfo" :editFormVisible="data.editFormVisible" @cancel="data.editFormVisible=false"
             @confirm="editUserInfo"/>
  <AddAdmin :addFormVisible="data.addFormVisible" @cancel="data.addFormVisible=false" @confirm="addUser"/>
</template>

<script setup lang="ts">
import {Edit, Lock, Plus,Files} from "@element-plus/icons-vue"
import {ElError, ElSuccess} from "../../../utils/msg";
import EditAdmin from "./components/EditAdmin.vue"
import AddAdmin from './components/AddAdmin.vue'
import {ElSelect} from "element-plus";
import * as XLSX from "xlsx"
import request from "../../../utils/request";
import {onMounted,reactive} from "vue";
const data = reactive({
  editFormVisible: false,
  addFormVisible: false,
  dialogFormVisible: false,
  list: [],
  total: 0,
  currentPage: 1,
  pageSize: 10
})
const exportXlsx = () => {
  let head:any = {
    id: 'ID',
    username: '用户名',
    phone: '手机号',
    role_name:"角色",
    create_time:"创建时间"
  }

  const list = data.list.map(item => {
    const obj:any = {}
    for (const k in item) {
      if (head[k]) {
        obj[head[k]] = item[k]
      }
    }
    return obj
  })
  // 创建工作表
  //@ts-ignore
  const xlsxData = XLSX.utils.json_to_sheet(list)
  // 创建工作簿
  const wb = XLSX.utils.book_new()
  // 将工作表放入工作簿中
  XLSX.utils.book_append_sheet(wb,xlsxData, 'data')
  // 生成文件并下载
  //@ts-ignore
  XLSX.writeFile(wb, new Date().getTime()+".xlsx")
  ElSuccess("导出表格成功")
}
const form = reactive({
  uid: 0,
  newPassword: "",
  confirmPassword: ""
})
const userInfo = reactive({
  id: 0,
  username: "",
  phone: "",
  r_id: ""
})
const addUser = async (userInfo: any) => {
  if (userInfo.username == "") {
    return ElError("请输入用户名")
  }
  if (userInfo.phone == "") {
    return ElError("请输入手机号")
  }
  if (userInfo.password.length < 6 || userInfo.password.length > 30) {
    return ElError("请输入6-30位密码")
  }
  const res: any = await request.post("/admin/admin/add", userInfo)
  if (res.code == 200) {
    data.addFormVisible=false
    getData(data.currentPage, data.pageSize);
    return ElSuccess("添加成功")
  } else {
    return ElError(res.msg)
  }
}
const showEditDialog = (row: any) => {
  userInfo.id = row.id
  userInfo.phone = row.phone
  userInfo.r_id = row.r_id
  userInfo.username = row.username
  data.editFormVisible = true;
}

const getData = async (currentPage: number, pageSize: number) => {
  const res: any = await request.post("/admin/admin/list", {currentPage: currentPage, pageSize: pageSize})
  data.list = res.data
  data.total = res.total
}
onMounted(() => {
  getData(data.currentPage, data.pageSize)
})

const editUserInfo = async (userInfo: any) => {
  const res: any = await request.post("/admin/admin/edit", userInfo)
  if (res.code == 200) {
    ElSuccess("提交成功");
    data.editFormVisible = false;
    getData(data.currentPage, data.pageSize)
  } else {
    ElError(res.msg)
  }
}
const modifyPassword = () => {
  request.post("/admin/admin/password", form).then((res: any) => {
    if (res.code == 200) {
      ElSuccess("用户密码修改成功")
      data.dialogFormVisible = false
    } else {
      ElError(res.msg)
    }
  })
}
const handleCurrentChange = (val: number) => {
  data.currentPage = val
  getData(data.currentPage, data.pageSize)
}
const handleSizeChange = (val: number) => {
  data.currentPage = 1
  data.pageSize = val
  getData(data.currentPage, data.pageSize)
}
</script>

<style scoped>
.el-icon {
  cursor: pointer;
}
</style>
