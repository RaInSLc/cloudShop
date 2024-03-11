package org.rainsc.spzx.manager.Service.impl;

import org.rainsc.spzx.exception.R_Exception;
import org.rainsc.spzx.manager.Mapper.SysMenuMapper;
import org.rainsc.spzx.manager.Service.SysMenuService;
import org.rainsc.spzx.manager.utils.MenuHelper;
import org.rainsc.spzx.model.entity.system.SysMenu;
import org.rainsc.spzx.model.entity.system.SysUser;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.rainsc.spzx.model.vo.system.SysMenuVo;
import org.rainsc.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        // 查询所有菜单  返回list
        List<SysMenu> sysMenuList = sysMenuMapper.findAllNodes();
        // 如果没有节点  直接返回null
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return null;
        }
        // 调用工具类 将返回的list封装成前端 element plus需要的格式
        List<SysMenu> tree = MenuHelper.buildTree(sysMenuList);
        return tree;
    }

    // 添加
    @Override
    public void save(SysMenu sysMenu) {
        Integer sortValue = sysMenu.getSortValue();
        String component = sysMenu.getComponent();
        if (sortValue == null || component == null) {
            throw new R_Exception(ResultCodeEnum.MENU_NULL);
        }
        sysMenuMapper.save(sysMenu);
    }

    // 修改
    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    // 删除
    @Override
    public void removeById(Long id) {
        // 查询是否包含子菜单
        int countParent = sysMenuMapper.selectCountParentByID(id);
        // 不包含才删除
        // 找到的子菜单的数量大于0 则抛出异常
        if (countParent > 0) {
            throw new R_Exception(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.removeById(id);

    }

    // 查询用户可以操作的菜单
    @Override
    public List<SysMenuVo> findMenuByUserId() {
        // 获取当前登录用户id
        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();

        // 根据用户id查询指定的菜单
        List<SysMenu> sysMenuList = sysMenuMapper.findMenuByUserId(userId);

        // 封装成树形结构
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        List<SysMenuVo> sysMenuVos = this.buildMenus(sysMenuTreeList);
        return sysMenuVos;
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}