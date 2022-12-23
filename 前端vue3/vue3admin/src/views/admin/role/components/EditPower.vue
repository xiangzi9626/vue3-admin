<template>
  <!--编辑弹出层-->
  <el-dialog @open="open" v-model="props.editPowerVisible" :close-on-press-escape="false" :show-close="false"
             title="权限分配"
             :align-center="true">
    <el-form>
      <el-form-item label="角色">
        <span>{{ this.props.roleName }}</span>
      </el-form-item>
      <el-form-item label="菜单权限">
        <el-tree
            ref="treeRef"
            :data="data.tree"
            show-checkbox
            default-expand-all
            node-key="id"
            :highlight-current="true"
            :props="defaultProps"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="confirm">提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import request from "../../../../utils/request";
import router from "../../../../router";
import {menuStore} from "../../../../store/menuStore";
import {ElTree} from 'element-plus'
import type Node from 'element-plus/es/components/tree/src/model/node'
import {onMounted} from "vue";

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
//@ts-ignore
const props = defineProps({
  //@ts-ignore
  roleId: "",
  roleName: "",
  editPowerVisible: false
})
const data = reactive({
  menus: menuStore().menus,
  tree: [],
})
const treeRef = ref<InstanceType<typeof ElTree>>()
const setChecked = (key: number) => {
  treeRef.value!.setChecked(key, true, false)
}
const getPowerMenus = () => {
  request.post("/admin/role_menus?role_id=" + props.roleId).then(res => {
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
  //@ts-ignore
  data.tree = buildMenu(data.menus);
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

</style>
