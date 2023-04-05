<template>
  <div style="border: 1px solid #ccc">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
        :mode="mode"
    />
    <Editor
        style="height: 500px; overflow-y: hidden;"
        v-model="valueHtml"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="handleCreated"
    />
  </div>
</template>

<script lang="ts" setup>
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'
import {CONFIG} from "../config/config";
import {ref,onBeforeUnmount,shallowRef} from "vue"
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()

// 内容 HTML
const valueHtml = ref('')
const mode = 'simple' //default 或 'simple'
const toolbarConfig = {}
const editorConfig = {
  placeholder: '请输入内容...',
  MENU_CONF: {}
}
//@ts-ignore
editorConfig.MENU_CONF['uploadImage'] = {
  server: CONFIG.SERVER_URL + "/admin/file/img/editor_upload_img",
  fieldName: "file"
}
// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

const handleCreated = (editor:any) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}
const setHtml=(html:any)=>{
  valueHtml.value=html
}
defineExpose({
   valueHtml,
  setHtml
})
</script>
<style scoped>

</style>
