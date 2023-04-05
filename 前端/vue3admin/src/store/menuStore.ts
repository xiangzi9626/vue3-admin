import { defineStore } from 'pinia'
export const menuStore = defineStore('menu', {
    state:()=>{
        return{
            test:"12345",
            menus:[]
        }
    },
    getters:{},
    actions:{
        setMenus(data:any){
           this.menus=data
        }
    }
})
