import {createRouter, createWebHashHistory, createWebHistory, RouteRecordRaw} from 'vue-router'
import request from "../utils/request";
import {menuStore} from "../store/menuStore";
import pinia from "../store";

const routes: Array<RouteRecordRaw> = [
    {
        path: "/:catchAll(.*)",
        //@ts-ignore
        hidden: true,
        component: () => import("../views/404/404.vue")
    },
    {
        path: '/',
        meta: {title: "首页"},
        component: () => import("../views/HomeView.vue")
    },
    {
        path: '/admin/login',
        name: 'admin-login',
        meta: {title: "后台登录"},
        component: () => import('../views/admin/Login.vue')
    },
    // @ts-ignore
    {
        path: "/admin",
        name: "admin",
        redirect: "/admin/home",
        meta: {title: "后台首页", requireauth: true},
        component: () => import("../views/admin/layout/Layout.vue"),
    }
]
const router = createRouter({
    history: createWebHistory(),
    routes
})

function buildRoute(list: Array<object>) {
    const modules=import.meta.glob("../views/**/**.vue")
    let children: Array<object> = []
    let deep = (children: Array<object>, list: Array<object>) => {
        list.forEach((item: any) => {
            if (item.children && item.children.length > 0) {
                let obj = {
                    path: item.path,
                    meta: {title: item.title, icon: item.icon, url: "", requireauth: true},
                    children: []
                }
                children.push(obj)
                // @ts-ignore
                deep(children[children.length - 1].children, item.children)
            } else {
                // @ts-ignore
                children.push({
                    path: item.path,
                    meta: {title: item.title, icon: item.icon, url: item.url, requireauth: true},
                    component:modules[`../views/admin/${item.component}.vue`]
                    //component: () => import(`../views/admin/${item.component}.vue`)
                })
            }
        })
    }
    deep(children, list)
    return children
}

let b = true //是否第一次加载
router.beforeEach(async (to, from, next) => {
    if (to.name === "admin-login") {
        if (sessionStorage.getItem("token")) {
            router.push({path: "/admin/home"})
        }
    } else if (/^\/admin/.test(to.path) || to.meta.requireauth) {
        if (!sessionStorage.getItem("token")) {
            router.push({path: "/admin/login"})
        }
    }
    if (to.meta.title) {
        // @ts-ignore
        document.title = to.meta.title
    }
    if (b && sessionStorage.getItem("token")) {
        let res: any = await request.post("/admin/menu/list")
        let store = menuStore(pinia)
        store.setMenus(res)
        let menus = buildRoute(res)
        menus.forEach((item: any) => {
            router.addRoute("admin", item)
        })
        b = false
        next({...to, replace: true})
    } else {
        next()
    }
})
export default router
