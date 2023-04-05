<template>
  <!--编辑弹出层-->
  <el-dialog @open="open" v-model="props.editPowerVisible" :close-on-press-escape="false" :show-close="false"
             title="权限分配"
             :align-center="true">
    <el-scrollbar>
    <el-form>
      <el-form-item label="角色">
        <span>{{ props.roleName }}</span>
      </el-form-item>
      <el-form-item label="菜单权限">
        <el-tree
            ref="treeRef"
            :data="data.menus"
            show-checkbox
            default-expand-all
            node-key="id"
            :highlight-current="true"
            :props="defaultProps"
        />
      </el-form-item>
    </el-form>
      </el-scrollbar>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="confirm">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import router from "../../../../router";
import {menuStore} from "../../../../store/menuStore";
import {ElTree} from 'element-plus'
import type Node from 'element-plus/es/components/tree/src/model/node'
import {ref, reactive, onMounted, onBeforeMount} from "vue";
import request from "../../../../utils/request";
interface Tree {
  id: number
  label: string
  children?: Tree[]
}

const defaultProps = {
  children: 'children',
  label: 'label',
  id: "id"
}
const props = defineProps({
   roleId: {type:String,default:""},
  roleName: {type:String,default:""},
  editPowerVisible: {type:Boolean,default:""}
})
const data:any= reactive({
  menus:[],
})
const menuAll=async ()=>{
  const res:any=await request.post("/admin/menu/all")
  data.menus = buildMenu(res)
}
const treeRef = ref<InstanceType<typeof ElTree>>()
const setChecked = (key: number) => {
  treeRef.value!.setChecked(key, true, false)
}
const getPowerMenus = () => {
  request.post("/admin/role/menus?role_id=" + props.roleId).then((res:any) => {
    resetChecked();
    const arr = res.data.powerMenus.split(",")
    for (let i = 0; i < arr.length; i++) {
      setChecked(arr[i])
    }
  })
}
const emits = defineEmits(['confirm', "cancel"])
const cancel = () => {
  emits("cancel")
}
const confirm = () => {
  emits("confirm", {roleId: props.roleId, roleName: props.roleName, powerMenus: getCheckedKeys()})
}
const buildMenu = (list: Array<object>) => {
  let children: Array<object> = []
  let deep = (children: Array<object>, list: Array<object>) => {
    list.forEach((item: any) => {
      if (item.children && item.children.length > 0) {
        let obj = {
          id: item.id,
          label: item.title,
          children: []
        }
        children.push(obj)
        // @ts-ignore
        deep(children[children.length - 1].children, item.children)
      } else {
        // @ts-ignore
        children.push({
          id: item.id,
          label: item.title,
        })
      }
    })
  }
  deep(children, list)
  return children
}
onMounted(() => {
   menuAll()
})
const resetChecked = () => {
  treeRef.value!.setCheckedKeys([], false)
}
const getCheckedKeys = () => {
  const checkedKeys: any = treeRef.value!.getCheckedKeys(false)
  return checkedKeys
}
const open = () => {
  getPowerMenus()
}
</script>
<style scoped>
.el-scrollbar{
  height:300px;
}
</style>
