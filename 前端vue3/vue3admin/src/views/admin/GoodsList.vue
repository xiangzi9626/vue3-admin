<template>
  <div class="btn-box">
    <el-button type="primary" :icon="Plus" @click="jump">添加</el-button>
    <el-button type="primary" :icon="Delete" @click="showDialog">批量删除</el-button>
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
        @select="handleSelect"
        @select-all="handleSelectAll"
    >
      <el-table-column type="selection" width="55"/>
      <el-table-column label="图片/标题" sortable>
        <template #default="scope">
          <div class="goods-box">
            <span><img style="max-width:100px;max-height:100px"
                       :src="CONFIG.SERVER_URL+'/upload/img/goods/'+scope.row.img"/></span>
            &nbsp;
            <span>{{ scope.row.title }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="price" label="价格" sortable/>
      <el-table-column prop="stock" label="库存" sortable/>
      <el-table-column label="上架" sortable>
        <template #default="scope">
          <span v-if="scope.row.status==1">上架</span>
          <span v-else>下架</span>
        </template>
      </el-table-column>
      <el-table-column label="分类" sortable>
        <template #default="scope">
          <span v-if="scope.row.cid==0">未分类</span>
          <span v-else>{{scope.row.name}}</span>
        </template>
      </el-table-column>
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
                @click="jumpEdit(scope.row)">
              <Edit/>
            </el-icon>
          </el-tooltip>
          &nbsp;
          <el-popconfirm :title="'确定删除商品 '+scope.row.title+' 吗?'" @confirm="handleDelete(scope.row)"
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
    <el-pagination
        v-if="data.total>0"
        background
        layout="prev, pager, next,sizes,total"
        :total="data.total" :page-sizes="[10, 20, 30, 40]"
        v-model:current-page="data.currentPage"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"/>
  </div>
  <Dialog msg="确定删除吗?" :centerDialogVisible="data.centerDialogVisible" @confirm="batchDelete"
          @cancel="data.centerDialogVisible=false"/>
</template>

<script setup lang="ts">
import request from "../../utils/request";
import {Edit, Delete, Plus} from "@element-plus/icons-vue"
import {ElError, ElSuccess} from "../../utils/msg";
import router from "../../router";
import {CONFIG} from "../../config/config";
import Dialog from "../../views/components/Dialog.vue"

const data = reactive({
  centerDialogVisible: false,
  list: [],
  total: 0,
  currentPage: 1,
  pageSize: 10
})
const showDialog = () => {
  if (data.list.length == 0) return ElError("没有数据")
  //@ts-ignore
  let checkedArr = data.list.filter(item => item.checked)
  if (checkedArr.length == 0) return ElError("请勾选要删除的项")
  data.centerDialogVisible = true
}
const batchDelete = async () => {
  data.centerDialogVisible = false
  let idArr: Array<number> = []
  data.list.forEach(item => {
    //@ts-ignore
    if (item.checked) {
      //@ts-ignore
      idArr.push(item.id)
    }
  })
  const res: any = await request.post("/admin/batch_delete_goods", idArr)
  if (res.code == 200) {
    ElSuccess("删除成功")
    data.currentPage = 1
    getData(data.currentPage, data.pageSize)
  }
}
const jump = () => {
  router.push("/admin/goods/addgoods")
}
const jumpEdit = (row: any) => {
  router.push("/admin/goods/edit_goods?id=" + row.id)
}
const getData = (currentPage: number, pageSize: number) => {
  request.post("/admin/goodslist", {currentPage: currentPage, pageSize: pageSize}).then((res: any) => {
    console.log(JSON.stringify(res))
    data.list = res.data
    data.list.forEach((item: any) => {
      item.checked = false
    })
    data.total = res.total
    console.log(JSON.stringify(res.data))
  })
}
getData(data.currentPage, data.pageSize)
const handleDelete = (row: any) => {
  request.post("/admin/delete_goods", {id: row.id}).then((res: any) => {
    if (res.code == 200) {
      ElSuccess("删除成功")
      getData(data.currentPage, data.pageSize)
    } else {
      ElError(res.msg)
    }
  })
}
const handleSelect = (selection: any, row: any) => {
  row.checked = !row.checked
}
const handleSelectAll = (selection: any) => {
  let b = true
  if (selection.length == 0) {
    b = false
  }
  data.list.forEach((obj: any) => {
    obj.checked = b
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
.btn-box {
  margin-bottom: 10px;
}

.el-icon {
  cursor: pointer;
}

.goods-box {
  display: flex;
  align-items: center;
}
</style>
