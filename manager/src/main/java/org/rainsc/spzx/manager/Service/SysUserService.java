package org.rainsc.spzx.manager.Service;

import org.rainsc.spzx.model.dto.system.LoginDto;
import org.rainsc.spzx.model.entity.system.SysUser;
import org.rainsc.spzx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);
}
