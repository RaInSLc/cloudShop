package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.system.SysUser;

@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUsername(String userName);
}
