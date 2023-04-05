import {createApp} from 'vue'
import App from './App.vue'
import router from './router/index'
import pinia from "./store/index";
import "./style.css"
import 'font-awesome/css/font-awesome.min.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//import './mock/article'
const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.use(pinia)
app.mount('#app')