package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.dto.system.SysUserDto;
import org.rainsc.spzx.model.entity.system.SysUser;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUsername(String userName);

    List<SysUser> findByPage(SysUserDto sysUserDto);



    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void delSysUser(Long userId);

    String selectUserNameById(Long id);
}
