<template>
  <div class="btn-box">
    <el-button type="primary" :icon="Plus" @click="jump">添加</el-button>
    <el-button type="primary" :icon="Refresh" @click="refresh">刷新</el-button>
  </div>
  <div>
    <el-table
        :data="data.tableData"
        style="width: 100%; margin-bottom: 20px"
        row-key="id"
        empty-text="没有数据"
        :border="true"
        :stripe="true"
        default-expand-all
    >
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="pid" label="父ID" sortable/>
      <el-table-column prop="title" label="菜单名称" sortable/>
      <el-table-column prop="url" label="URL" sortable/>
      <el-table-column prop="sort" label="排序" sortable/>
      <el-table-column label="图标" sortable>
        <template #default="scope">
          <i :class="scope.row.icon"></i>&nbsp;{{ scope.row.icon }}
        </template>
      </el-table-column>
      <el-table-column prop="component" label="组件" sortable/>
      <el-table-column prop="createTime" label="创建时间" sortable/>
      <el-table-column label="操作" sortable>
        <template #default="scope">
          <el-tooltip
              class="box-item"
              effect="dark"
              content="编辑"
              placement="top-start">
             <el-icon
                 @click="jumpEdit(scope.row)"
                class="btn">
              <Edit/>
            </el-icon>
          </el-tooltip>
          &nbsp;
          <el-popconfirm :title="'确定删除菜单 '+scope.row.title+' 吗?'" @confirm="handleDelete(scope.row)"
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
</template>
<script setup lang="ts">
import {Edit,Delete,Plus,Refresh} from "@element-plus/icons-vue"
import {onMounted, reactive} from "vue"
import {ElError, ElSuccess} from "../../../utils/msg";
import router from "../../../router";
import request from '../../../utils/request'
const data = reactive({
  tableData:[]
})
const menuAll=async ()=>{
 let res:any =await request.post("/admin/menu/all")
  data.tableData=res
}
onMounted(()=>{
  menuAll()
})
const jump = () => {
  router.push("/admin/menu/add")
}
const jumpEdit = (row: any) => {
   router.push("/admin/menu/edit?id="+row.id)
}
const refresh = () => {
  //@ts-ignore
  window.location = window.location.href
}
const handleDelete = (item: any) => {
  request.post("/admin/menu/delete", {id: item.id}).then((res: any) => {
    if (res.code == 200) {
      ElSuccess(res.msg)
      request.post("/admin/menu/all").then((res: any) => {
        data.tableData = res
      })
    } else {
      ElError(res.msg)
    }
  })
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
