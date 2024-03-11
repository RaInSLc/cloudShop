package org.rainsc.spzx.manager.Service;

import org.rainsc.spzx.model.entity.system.SysMenu;
import org.rainsc.spzx.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    void removeById(Long id);

    List<SysMenuVo> findMenuByUserId();
}
