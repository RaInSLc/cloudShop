package org.rainsc.spzx.manager.Service;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.model.dto.system.SysRoleDto;
import org.rainsc.spzx.model.entity.system.SysRole;

public interface SysRoleService {
    void deleteById(Long roleId);

    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    void saveSysRole(SysRole sysRole);
}
