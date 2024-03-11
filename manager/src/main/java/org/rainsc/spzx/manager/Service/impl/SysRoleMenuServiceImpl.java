package org.rainsc.spzx.manager.Service.impl;

import org.rainsc.spzx.manager.Mapper.SysRoleMenuMapper;
import org.rainsc.spzx.manager.Service.SysMenuService;
import org.rainsc.spzx.manager.Service.SysRoleMenuService;
import org.rainsc.spzx.model.dto.system.AssignMenuDto;
import org.rainsc.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuService sysMenuService;

    // 查询所有菜单 和 已经分配过菜单的
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        // 查询所有的菜单
        List<SysMenu> sysMenuList = sysMenuService.findNodes();
        // 查询角色分配过的id列表
        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);
        // 将数据存储到Map中进行返回
        Map<String, Object> result = new HashMap<>();
        result.put("sysMenuList", sysMenuList);
        result.put("roleMenuIds", roleMenuIds);
        // 返回
        return result;

    }

    // 为角色分配菜单
    @Override
    public void doAssign(AssignMenuDto assignMenuDto) {
        // 删除已分配过的菜单
        sysRoleMenuMapper.deleteByRoleId(assignMenuDto.getRoleId());
        // 保存分配的数据
        List<Map<String, Number>> menuInfo = assignMenuDto.getMenuIdList();
        System.out.println(menuInfo);
        if (menuInfo != null && menuInfo.size() > 0) {
            // 分配了菜单
            sysRoleMenuMapper.doAssign(assignMenuDto);
        }
    }
}
