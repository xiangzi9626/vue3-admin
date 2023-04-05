<template>
<el-row>
  <el-col :span="8">
    <div><h2>用户数量</h2></div>
    <div>120</div>
  </el-col>
  <el-col :span="8">
    <div><h2>商品总数</h2></div>
    <div>120</div>
  </el-col>
  <el-col :span="8">
    <div><h2>订单总数</h2></div>
    <div>200</div>
  </el-col>
</el-row>
  <el-row>
    <div id="myEcharts" style="width: 100%;height:400px"></div>
  </el-row>
</template>

<script setup lang="ts">
import * as echarts from 'echarts'
import {reactive,onMounted} from "vue"
const data=reactive({
  option: {},
})
const initEcharts=()=>{
  //@ts-ignore
  let myEchart = echarts.init(document.getElementById("myEcharts"));
      // 绘制图表
  myEchart.setOption({
        title: { text: "总订单量" },
        tooltip: {},
        xAxis: {
          data: ["12-3", "12-4", "12-5", "12-6", "12-7", "12-8", "12-9", "12-10","12-11", "12-12", "12-13"],
        },
        yAxis: {},
        series: [
          {
            name: "订单量",
            type: "bar",
            barMaxWidth:100,
            itemStyle:{
              color:"blue"
            },
            data: [50, 200, 360, 100, 100, 210,50,500,300,150,80],
          },
        ],
      })
}
onMounted(()=>{
  initEcharts()
})
</script>

<style scoped>
.el-row{
  margin: auto;
  text-align: center;
}
.el-row h2{
  color: #888888;
}
</style>
