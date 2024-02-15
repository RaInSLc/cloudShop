package org.rainsc.spzx.manager.Service;

import org.rainsc.spzx.model.dto.system.LoginDto;
import org.rainsc.spzx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);
}
