import {createApp} from 'vue'
import App from './App.vue'
import router from './router/index'
import pinia from "./store/index";
import 'font-awesome/css/font-awesome.min.css'
//import ElementPlus from 'element-plus'
//import 'element-plus/dist/index.css'
//import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//import './mock/article'
const app = createApp(App)
/*for (const [key,component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}*/
app.use(router)
app.use(pinia)
app.mount('#app')
