package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.system.SysMenu;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findAllNodes();

    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    int selectCountParentByID(Long id);

    void removeById(Long id);

    List<SysMenu> findMenuByUserId(Long userId);
}
