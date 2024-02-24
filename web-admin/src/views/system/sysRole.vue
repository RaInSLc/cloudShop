<template>
  <!-- 搜索、添加、编辑角色的界面 -->
  <div class="search-div">
    <!-- 搜索表单 -->
    <el-form label-width="70px" size="small">
      <el-form-item label="角色名称">
        <!-- 角色名称输入框 -->
        <el-input
          v-model="queryDto.roleName"
          style="width: 100%"
          placeholder="角色名称"
        />
      </el-form-item>
      <el-row style="display:flex">
        <!-- 触发搜索角色按钮 -->
        <el-button type="primary" size="small" @click="searchSysRole">
          搜索
        </el-button>
        <!-- 重置搜索条件按钮 -->
        <!-- <el-button size="small" @click="resetData">重置</el-button> -->
      </el-row>
    </el-form>

    <!-- 添加角色按钮 -->
    <div class="tools-div">
      <el-button type="success" size="small" @click="addShow">添加</el-button>
    </div>

    <!-- 添加或修改角色表单对话框 -->
    <el-dialog v-model="dialogVisible" title="添加或修改角色" width="30%">
      <el-form label-width="120px">
        <el-form-item label="角色名称">
          <!-- 角色名称输入框 -->
          <el-input v-model="saveSysRole.roleName" />
        </el-form-item>
        <el-form-item label="角色Code">
          <!-- 角色Code输入框 -->
          <el-input v-model="saveSysRole.roleCode" />
        </el-form-item>
        <el-form-item>
          <!-- 提交表单按钮 -->
          <el-button type="primary" @click="submit">提交</el-button>
          <!-- 取消按钮 -->
          <el-button @click="dialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 角色列表数据展示 -->
    <el-table :data="list" style="width: 100%">
      <!-- 角色名称列 -->
      <el-table-column prop="roleName" label="角色名称" width="180" />
      <!-- 角色Code列 -->
      <el-table-column prop="roleCode" label="角色code" width="180" />
      <!-- 创建时间列 -->
      <el-table-column prop="createTime" label="创建时间" />
      <!-- 操作列 -->
      <el-table-column label="操作" align="center" width="280" #default="scope">
        <!-- 修改按钮 -->
        <el-button type="primary" size="small" @click="editShow(scope.row)">
          修改
        </el-button>
        <!-- 删除按钮 -->
        <el-button type="danger" size="small" @click="delRole">删除</el-button>
      </el-table-column>
    </el-table>

    <!-- 分页条 -->
    <el-pagination
      v-model:current-page="pageParams.page"
      v-model:page-size="pageParams.limit"
      :page-sizes="[10, 20, 50, 100]"
      @size-change="fetchData"
      @current-change="fetchData"
      layout="total, sizes, prev, pager, next"
      :total="total"
      style="margin-top: 20px;"
    />
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { GetSysRoleListByPage, SaveSysRole } from '@/api/sysRole'
import { ElMessage } from 'element-plus'

// 角色添加和修改表单的数据模型
const roleForm = {
  id: '',
  roleName: '',
  roleCode: '',
}
// 保存用户输入的角色信息
const saveSysRole = ref(roleForm)
// 控制添加或修改角色表单的显示隐藏
const dialogVisible = ref(false)

// 弹出修改角色表单并回显已有数据
const editShow = row => {
  saveSysRole.value = { ...row }
  dialogVisible.value = true
}

// 触发添加角色表单显示
const addShow = () => {
  saveSysRole.value = {}
  dialogVisible.value = true
}

// 提交角色表单
const submit = async () => {
  const { code } = await SaveSysRole(saveSysRole.value)
  if (code === 200) {
    dialogVisible.value = false
    await fetchData()
    ElMessage.success('操作成功')
  }
}

// 角色列表数据和分页相关参数
let list = ref([])
let total = ref(0)
const pageParamsForm = {
  page: 1,
  limit: 10,
}
const pageParams = ref(pageParamsForm)
const queryDto = ref({ roleName: '' })

// 页面加载时获取角色列表数据
onMounted(() => {
  fetchData()
})

// 获取角色列表数据
const fetchData = async () => {
  const { data } = await GetSysRoleListByPage(
    pageParams.value.page,
    pageParams.value.limit,
    queryDto.value
  )
  list.value = data.list
  total.value = data.total
}

// 触发搜索角色
const searchSysRole = () => {
  fetchData()
}
</script>

<style scoped>
/* 样式 */
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
