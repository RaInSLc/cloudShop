package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleUserMapper {
    void delOldId(Long userId);

    void doAssign(Long userId, Long roleId);
}
