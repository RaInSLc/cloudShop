<template>
  <div class="tools-div">
    <el-button type="success" size="small" >导出</el-button>
    <el-button type="primary" size="small" >导入</el-button>
  </div>

  <!---懒加载的树形表格-->
  <el-table
      :data="list"
      style="width: 100%"
      row-key="id"
      border
      lazy
      :load="fetchData"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
  >
    <el-table-column prop="name" label="分类名称" />
    <el-table-column prop="imageUrl" label="图标" #default="scope">
      <img :src="scope.row.imageUrl" width="50" />
    </el-table-column>
    <el-table-column prop="orderNum" label="排序" />
    <el-table-column prop="status" label="状态" #default="scope">
      {{ scope.row.status == 1 ? '正常' : '停用' }}
    </el-table-column>
    <el-table-column prop="createTime" label="创建时间" />
  </el-table>

</template>

<script setup>
import { ref } from 'vue';

// 定义list属性模型
const list = ref([
  {"id":1 , "name":"数码" , "orderNum":"1" , "status":1 , "createTime":"2023-05-22" , "hasChildren": true},
  {"id":2 , "name":"手机" , "orderNum":"1", "status":1, "createTime":"2023-05-22"},
])

// 加载数据的方法
const fetchData = (row, treeNode, resolve) => {

  // 向后端发送请求获取数据
  const data = [
    {"id":3 , "name":"智能设备" , "orderNum":"1" , "status":1 , "createTime":"2023-05-22" },
    {"id":4 , "name":"电子教育" , "orderNum":"2" , "status":1 , "createTime":"2023-05-22" },
  ]

  // 返回数据
  resolve(data)

}

</script>

<style scoped>
.search-div {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}
.tools-div {
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}
</style>