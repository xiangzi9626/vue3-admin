<template>
  <div class="btn-box">
    <el-button type="primary" :icon="Plus" @click="data.addFormVisible=true">添加</el-button>
  </div>
  <div>
    <el-table
        :data="data.tableData"
        style="width: 100%; margin-bottom: 20px"
        row-key="roleId"
        empty-text="没有数据"
        :border="true"
        :stripe="true"
        default-expand-all
    >
      <el-table-column prop="roleId" label="ID" sortable/>
      <el-table-column prop="roleName" label="角色名" sortable/>
      <el-table-column label="操作" sortable>
        <template #default="scope">
          <el-tooltip
              class="box-item"
              effect="dark"
              content="权限分配"
              placement="top-start">
            <el-icon
                @click="data.editPowerVisible=true;data.roleId=scope.row.roleId;data.roleName=scope.row.roleName"
                class="btn">
              <Filter/>
            </el-icon>
          </el-tooltip>
          &nbsp;
          <el-tooltip
              class="box-item"
              effect="dark"
              content="编辑"
              placement="top-start">
            <el-icon
                @click="data.editFormVisible=true;data.roleId=scope.row.roleId;data.roleName=scope.row.roleName"
                class="btn">
              <Edit/>
            </el-icon>
          </el-tooltip>
          &nbsp;
          <el-popconfirm :title="'确定删除角色 '+scope.row.roleName+' 吗?'" @confirm="handleDelete(scope.row)"
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
  <AddRole :addFormVisible="data.addFormVisible" @confirm="addRole" @cancel="data.addFormVisible=false"/>
  <EditRole :roleName="data.roleName" :editFormVisible="data.editFormVisible" @confirm="editRole"
            @cancel="data.editFormVisible=false"/>
  <EditPower :roleName="data.roleName" :roleId="data.roleId" :editPowerVisible="data.editPowerVisible"
             @confirm="editPower"
             @cancel="data.editPowerVisible=false"/>
</template>
<script setup lang="ts">
import {Edit, Delete, Plus, Filter} from "@element-plus/icons-vue"
import {onBeforeMount,reactive} from 'vue'
import {ElError, ElSuccess} from "../../../utils/msg";
import AddRole from "./components/AddRole.vue"
import EditRole from "./components/editRole.vue"
import EditPower from "./components/EditPower.vue"
import router from "../../../router";
import request from '../../../utils/request'
const data = reactive({
  tableData: [],
  editFormVisible: false,
  addFormVisible: false,
  roleId: "",
  roleName: "",
  editPowerVisible: false
})
const editPower =async (param: any) => {
  //alert(param.powerMenus)
const res:any=await request.post("/admin/role/edit_power?role_id="+param.roleId+"&powerMenus="+param.powerMenus)
  if (res.code==200) data.editPowerVisible=false;return ElSuccess("提交成功");
  return ElError("提交失败请重试")
}
const editRole = async (form: any) => {
  if (form.roleName == "") {
    return ElError("角色名不能为空")
  }
  const res: any = await request.post("/admin/role/edit", {roleId: data.roleId, roleName: form.roleName})
  if (res.code == 200) data.editFormVisible = false;
  getData();
  return ElSuccess("提交成功")
  return ElError("提交失败请重试")
}
const addRole = async (form: any) => {
  if (form.roleName == "") {
    return ElError("角色名不能为空")
  }
  const res: any = await request.post("/admin/role/add", form)
  if (res.code == 200) {
    ElSuccess("添加成功")
    data.addFormVisible = false
    getData()
  } else {
    ElError("提交失败请重试")
  }
}
const getData = async () => {
  const res: any = await request.post("/admin/role/list")
  data.tableData = res
}
onBeforeMount(() => {
  getData()
})
const handleDelete = (item: any) => {
  request.post("/admin/role/delete?id=" + item.roleId).then((res: any) => {
    if (res.code == 200) {
      ElSuccess(res.msg)
      getData()
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
