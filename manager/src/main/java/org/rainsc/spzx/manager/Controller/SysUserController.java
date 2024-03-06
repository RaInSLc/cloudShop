package org.rainsc.spzx.manager.Controller;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.manager.Service.SysUserService;
import org.rainsc.spzx.model.dto.system.AssignRoleDto;
import org.rainsc.spzx.model.dto.system.SysUserDto;
import org.rainsc.spzx.model.entity.system.SysUser;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysUser")
//@CrossOrigin
public class SysUserController {
    //
    @Autowired
    private SysUserService sysUserService;
    // 条件分页查询

    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable("pageNum") Integer pageNum,
                             @PathVariable("pageSize") Integer pageSize,
                             SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(pageNum, pageSize, sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);

    }

    // 添加
    @PostMapping(value = "/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 修改
    @PutMapping(value = "/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 删除
    @DeleteMapping(value = "/delSysUser/{userId}")
    public Result delSysUser(@PathVariable(value = "userId") Long userId) {
        sysUserService.delSysUser(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 用户分配角色
    // 保存分配的数据
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignRoleDto assignRoleDto) {
        sysUserService.doAssign(assignRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}