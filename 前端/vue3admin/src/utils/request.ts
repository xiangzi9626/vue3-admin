import axios from 'axios';
import {CONFIG} from "../config/config"
// 创建HTTP实例
const $http = axios.create({
    baseURL: CONFIG.SERVER_URL,
    timeout: 5000,
    headers: {
        "Content-Type": "application/json;charset=utf-8"
    }
})

// 请求拦截
$http.interceptors.request.use(config => {
    config.headers = config.headers || {}
    if (sessionStorage.getItem('token')) {
        config.headers.token = sessionStorage.getItem('token') || ''
    }
    return config
})

// 响应拦截
$http.interceptors.response.use(res => {
    console.log(res)
    if (res.data == "login") {
        window.location.href = "/admin/login"
        sessionStorage.setItem("token", "")
        window.sessionStorage.clear()
    } else {
        return res.data
    }
}, err => {
    console.log(err);
    return Promise.reject(err);
})
export default $http


