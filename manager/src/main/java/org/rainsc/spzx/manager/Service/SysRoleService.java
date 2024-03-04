package org.rainsc.spzx.manager.Service;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.model.dto.system.SysRoleDto;
import org.rainsc.spzx.model.entity.system.SysRole;

import java.util.Map;

public interface SysRoleService {

    // 根据角色ID删除角色
    void deleteById(Long roleId);

    // 根据条件查询角色并分页返回结果
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    // 保存角色信息
    void saveSysRole(SysRole sysRole);

    // 更新角色信息
    void updateSysRole(SysRole sysRole);

    Map<String, Object> findAllRoles();
}
