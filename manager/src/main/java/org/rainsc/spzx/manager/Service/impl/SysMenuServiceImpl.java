package org.rainsc.spzx.manager.Service.impl;

import org.rainsc.spzx.exception.R_Exception;
import org.rainsc.spzx.manager.Mapper.SysMenuMapper;
import org.rainsc.spzx.manager.Service.SysMenuService;
import org.rainsc.spzx.manager.utils.MenuHelper;
import org.rainsc.spzx.model.entity.system.SysMenu;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        // 查询所有菜单  返回list
        List<SysMenu> sysMenuList = sysMenuMapper.findAllNodes();
        // 如果没有节点  直接发农会
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return null;
        }

        // 调用工具类 将返回的list封装成前端elep需要的格式
        List<SysMenu> tree = MenuHelper.buildTree(sysMenuList);


        return tree;
    }

    // 添加
    @Override
    public void save(SysMenu sysMenu) {
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
        } else if(countParent == 0) {
            sysMenuMapper.removeById(id);
        }else{
            throw new R_Exception(ResultCodeEnum.NODE_ERROR);
        }
    }
}
