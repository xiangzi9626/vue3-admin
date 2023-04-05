import {ElMessage} from "element-plus"
const ElWarning=(msg:string)=>{
    ElMessage({
        message:msg,
        type:"warning",
    })
}
const ElSuccess=(msg:string)=>{
    ElMessage({
        message:msg,
        type:"success",
    })
}
const ElError=(msg:string)=>{
    ElMessage({
        message:msg,
        type:"error",
    })
}
export {ElError,ElWarning,ElSuccess}
