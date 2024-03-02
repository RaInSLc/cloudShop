import request from '@/utils/request'

const base_api = '/admin/system/sysUser'
// 用户列表
export const GetSysUserListByPage = (pageNum, pageSize, queryDto) => {
    return request({
        // 路径
        url: `${base_api}/findByPage/${pageNum}/${pageSize}`,
        method: 'get',
        // 接口@requestBody前端中的写法 data: 名称  以Json方式进行传输
        // 接口没有 注解    前端使用params: 名称   以普通方式传输
        data: queryDto,
    })
}
// 用户添加
export const SaveSysUser = sysUser => {
    return request({
        // 路径
        url: `${base_api}/saveSysUser`,
        method: 'post',
        data: sysUser,
    })
}

// 用户修改
export const UpdateSysRole = sysUser => {
    return request({
        // 路径
        url: `${base_api}/updateSysRole`,
        method: 'put',
        data: sysUser,
    })
}

// 用户删除
export const DelSysUser = userId => {
    return request({
        // 路径
        url: `${base_api}/delSysUser/${userId}`,
        method: 'delete',
    })
}

