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
          <el-input v-model="saveSysRole.roleName"/>
        </el-form-item>
        <el-form-item label="角色Code">
          <!-- 角色Code输入框 -->
          <el-input v-model="saveSysRole.roleCode"/>
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
      <el-table-column prop="roleName" label="角色名称" width="180"/>
      <!-- 角色Code列 -->
      <el-table-column prop="roleCode" label="角色code" width="180"/>
      <!-- 创建时间列 -->
      <el-table-column prop="createTime" label="创建时间"/>
      <!-- 操作列 -->
      <el-table-column label="操作" align="center" width="280" #default="scope">
        <!-- 修改按钮 -->
        <el-button type="primary" size="small" @click="editShow(scope.row)">
          修改
        </el-button>
        <!-- 删除按钮 -->
        <el-button type="danger" size="small" @click="delRoleById(scope.row)">
          删除
        </el-button>
        <el-button type="warning" size="small" @click="showAssignMenu(scope.row)">
          分配菜单
        </el-button>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogMenuVisible" title="分配菜单" width="40%">
      <el-form label-width="80px">
        <el-tree
            :data="sysMenuTreeList"
            ref="tree"
            show-checkbox
            default-expand-all
            :check-on-click-node="true"
            node-key="id"
            :props="defaultProps"
        />
        <el-form-item>
          <el-button type="primary" @click="doAssign">提交</el-button>
          <el-button @click="dialogMenuVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

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
import {onMounted, ref} from 'vue'
import {DeleteById, GetSysRoleListByPage, GetSysRoleMenuIds, SaveSysRole, UpdateSysRole,DoAssignMenuIdToSysRole} from '@/api/sysRole'
import {ElMessage, ElMessageBox} from 'element-plus'

// 分配菜单
const doAssign = async () => {
  const checkedNodes = tree.value.getCheckedNodes() ; // 获取选中的节点
  const checkedNodesIds = checkedNodes.map(node => {  // 获取选中的节点的id
    return {
      id: node.id,
      isHalf: 0
    }
  })

  // 获取半选中的节点数据，当一个节点的子节点被部分选中时，该节点会呈现出半选中的状态
  const halfCheckedNodes = tree.value.getHalfCheckedNodes() ;
  const halfCheckedNodesIds = halfCheckedNodes.map(node => {   // 获取半选中节点的id
    return {
      id: node.id,
      isHalf: 1
    }
  })

  // 将选中的节点id和半选中的节点的id进行合并
  const menuIds = [...checkedNodesIds , ...halfCheckedNodesIds]
  console.log(menuIds);

  // 构建请求数据
  const assignMenuDto = {
    roleId: roleId,
    menuIdList: menuIds
  }

  // 发送请求
  await DoAssignMenuIdToSysRole(assignMenuDto) ;
  ElMessage.success('操作成功')
  dialogMenuVisible.value = false

}
// 构造分配菜单回显界面
const defaultProps = {
  children: 'children',
  label: 'title',
}
const dialogMenuVisible = ref(false)
const sysMenuTreeList = ref([])

// 树对象变量
const tree = ref()

// 默认选中的菜单数据集合
let roleId = ref()
const showAssignMenu = async row => {
  dialogMenuVisible.value = true
  roleId = row.id
  const {data} = await GetSysRoleMenuIds(row.id)   // 请求后端地址获取所有的菜单数据，以及当前角色所对应的菜单数据
  sysMenuTreeList.value = data.sysMenuList
  tree.value.setCheckedKeys(data.roleMenuIds)   // 进行数据回显
}

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
  // es6中的对象拓展运算符实现对象的复制
  saveSysRole.value = {...row}
  dialogVisible.value = true
}

// 触发添加角色表单显示
const addShow = () => {
  saveSysRole.value = {}
  dialogVisible.value = true
}

// 添加和修改角色
// 判断sysRole包含id 则进行修改操作 不包含则进行添加操作
// 不包含id执行修改

const submit = async () => {

  if (!saveSysRole.value.id) {
    const {code} = await SaveSysRole(saveSysRole.value)
    if (code === 200) {
      dialogVisible.value = false
      ElMessage.success('操作成功')
      await fetchData()
    }

  } else {
// 执行修改
    const {code} = await UpdateSysRole(saveSysRole.value)
    if (code === 200) {
      dialogVisible.value = false
      ElMessage.success("操作成功")
      await fetchData()
    }
  }
}

// 删除
const delRoleById = row => {
  ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', '!警告!', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    const {code} = await DeleteById(row.id)
    if (code === 200) {
      ElMessage.success('删除成功')
      pageParams.value.page = 1
      await fetchData()
    }
  })
}


// 角色列表数据和分页相关参数
let list = ref([])
let total = ref(0)
const pageParamsForm = {
  page: 1,
  limit: 10,
}
const pageParams = ref(pageParamsForm)
const queryDto = ref({roleName: ''})

// 页面加载时获取角色列表数据
onMounted(() => {
  fetchData()
})


// 获取角色列表数据
const fetchData = async () => {
  const {data} = await GetSysRoleListByPage(
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
