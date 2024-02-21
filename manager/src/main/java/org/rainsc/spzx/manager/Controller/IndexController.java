package org.rainsc.spzx.manager.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.rainsc.spzx.manager.Service.SysUserService;
import org.rainsc.spzx.manager.Service.ValidateCodeService;
import org.rainsc.spzx.model.dto.system.LoginDto;
import org.rainsc.spzx.model.entity.system.SysUser;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.rainsc.spzx.model.vo.system.LoginVo;
import org.rainsc.spzx.model.vo.system.ValidateCodeVo;
import org.rainsc.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    // 生成验证码
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();

        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    // 获取用户信息
//    @GetMapping(value = "/getUserInfo")
//    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
//        // 把放在请求头中的token拿到
//        // 根据token查询redis获取用户信息
//        SysUser sysUser = sysUserService.getUserInfo(token) ;
//        return Result.build(sysUser , ResultCodeEnum.SUCCESS) ;
//    }

    // 使用线程池+拦截器获取用户信息
    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo() {
        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS);
    }


    // 退出登录
    @GetMapping(value = "/logout")
    public Result<SysUser> logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }

}
