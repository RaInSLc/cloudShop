package org.rainsc.spzx.manager.Service;

import java.util.Map;

public interface SysRoleMenuService {
    // 查询所有菜单 和 已经分配过菜单的
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);
}
