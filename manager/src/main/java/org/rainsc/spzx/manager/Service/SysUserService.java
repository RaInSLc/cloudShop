package org.rainsc.spzx.manager.Service;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.model.dto.system.LoginDto;
import org.rainsc.spzx.model.dto.system.SysUserDto;
import org.rainsc.spzx.model.entity.system.SysUser;
import org.rainsc.spzx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

//    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysRole(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void delSysUser(Long userId);
}
