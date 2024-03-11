import request from '@/utils/request'

const base_api = '/admin/system/sysRole'
// 角色列表
export const GetSysRoleListByPage = (pageNum, pageSize, queryDto) => {
  return request({
    // 路径
    url: `${base_api}/findByPage/${pageNum}/${pageSize}`,
    method: 'post',
    // 接口@requestBody前端中的写法 data: 名称  以Json方式进行传输
    // 接口没有 注解    前端使用params: 名称   以普通方式传输
    data: queryDto,
  })
}
// 角色添加
export const SaveSysRole = sysRole => {
  return request({
    // 路径
    url: `${base_api}/saveSysRole`,
    method: 'post',
    data: sysRole,
  })
}
// 角色修改
export const UpdateSysRole = sysRole => {
  return request({
    // 路径
    url: `${base_api}/updateSysRole`,
    method: 'put',
    data: sysRole,
  })
}

// 角色删除
export const DeleteById = roleId => {
  return request({
    // 路径
    url: `${base_api}/deleteById/${roleId}`,
    method: 'delete',
  })
}

// 查询所有的角色数据
export const GetAllRoleList = userId => {
  return request({
    url: `${base_api}/findAllRoles/${userId}`,
    // url: `${base_api}/findAllRoles`,
    method: 'get'
  })
}

// 查询指定角色所对应的菜单id
export const GetSysRoleMenuIds = (roleId) => {
  return request({
    url: "/admin/system/sysRoleMenu/findSysRoleMenuByRoleId/"+ roleId,
    method: 'get'
  })
}

// 根据角色分配菜单请求方法
export const DoAssignMenuIdToSysRole = (assignMenuDto) => {
  return request({
    url: "/admin/system/sysRoleMenu/doAssign",
    method: 'post',
    data: assignMenuDto
  })
}