<template>
  <div>
    <el-table
        :data="data.list"
        style="width: 100%; margin-bottom: 20px"
        row-key="id"
        :border="true"
        :stripe="true"
        default-expand-all
        empty-text="没有数据"
    >
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="username" label="用户名" sortable/>
      <el-table-column prop="phone" label="手机号" sortable/>
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
                @click="data.editFormVisible=true;userInfo.id=scope.row.id;userInfo.username=scope.row.username;userInfo.phone=scope.row.phone">
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
  <!--编辑用户弹出层-->
  <el-dialog v-model="data.editFormVisible" title="编辑用户资料" :align-center="true">
    <el-form :model="form">
      <el-form-item label="用户名" label-width="140px">
        <span>{{ userInfo.username }}</span>
      </el-form-item>
      <el-form-item label="手机号" label-width="140px">
        <el-input v-model="userInfo.phone" autocomplete="off" placeholder="请输入手机号"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.editFormVisible=false">取消</el-button>
        <el-button type="primary" @click="editUserInfo">确定</el-button>
      </span>
    </template>
  </el-dialog>
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
</template>

<script setup lang="ts">
import {reactive} from "vue"
import {Edit, Lock} from "@element-plus/icons-vue"
import {ElError, ElSuccess} from "../../../utils/msg";
import request from "../../../utils/request";
const data = reactive({
  editFormVisible: false,
  dialogFormVisible: false,
  list: [],
  total: 0,
  currentPage: 1,
  pageSize: 10
})
const form = reactive({
  uid: 0,
  newPassword: "",
  confirmPassword: ""
})
const userInfo = reactive({
  id: 0,
  rId:"",
  username: "",
  phone: ""
})
const getData = (currentPage: number, pageSize: number) => {
  request.post("/admin/user/list", {currentPage: currentPage, pageSize: pageSize}).then((res: any) => {
    data.list = res.data
    data.total = res.total
  })
}
getData(data.currentPage, data.pageSize)
const editUserInfo = () => {
  request.post("/admin/user/edit", userInfo).then((res: any) => {
    if (res.code == 200) {
      ElSuccess("用户资料修改成功")
      data.editFormVisible = false
      getData(data.currentPage, data.pageSize)
    } else {
      ElError(res.msg)
    }
  })
}
const modifyPassword = () => {
  request.post("/admin/user/password", form).then((res: any) => {
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
