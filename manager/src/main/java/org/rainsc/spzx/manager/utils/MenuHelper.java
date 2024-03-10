package org.rainsc.spzx.manager.utils;

import org.rainsc.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    // 递归完成封装
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        // 菜单封装返回给前端指定的格式
        // sysMenuList所有菜单内容
        // 创建list封装前端需要的数据
        ArrayList<SysMenu> trees = new ArrayList<>();
        // 使用增强for循环遍历
        for (SysMenu sysMenu : sysMenuList) {
            // 入口
            // 找到 parent_id = 0
            if (sysMenu.getParentId() == 0) {
                // 根据第一层找下面全部内容
                // sysMenu是当前菜单
                // sysMenuList是所有菜单的集合
                trees.add(findChildren(sysMenu, sysMenuList));
            }
        }
        return null;
    }

    // 递归查找下层菜单
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        // sysMenu 中的ListChildren封装下一层数据
        sysMenu.setChildren(new ArrayList<>());
        // 递归
        // sysMenu中的id和sysMenuList中parentId相同 则代表是下一层
        // b层的父节点id是a层的id
        // it是遍历的出来的每个菜单
        for (SysMenu it : sysMenuList) {
            // 开始找关系
            // 父子id是否相同
            // 相同则代表是父子关系
            if (sysMenu.getId() == it.getParentId()) {
                // 递归用
                sysMenu.getChildren().add(findChildren(it, sysMenuList));
            }
        }
        return sysMenu;
    }
}
