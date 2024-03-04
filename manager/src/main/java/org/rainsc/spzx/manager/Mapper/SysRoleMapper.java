package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.dto.system.SysRoleDto;
import org.rainsc.spzx.model.entity.system.SysRole;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    // 添加角色
    void saveSysRole(SysRole sysRole);

    // 角色修改
    void update(SysRole sysRole);

    void remove(Long roleId);

    List<SysRole> findAllRoles();
}
