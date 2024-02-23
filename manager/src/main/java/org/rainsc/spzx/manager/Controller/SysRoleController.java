package org.rainsc.spzx.manager.Controller;

import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.manager.Service.SysRoleService;
import org.rainsc.spzx.model.dto.system.SysRoleDto;
import org.rainsc.spzx.model.entity.system.SysRole;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable("pageNum") Integer pageNum,
                             @PathVariable("pageSize") Integer pageSize,
                             @RequestBody SysRoleDto sysRoleDto) {
        PageInfo<SysRole> pageInfo =
                sysRoleService.findByPage(sysRoleDto, pageNum, pageSize);


        return Result.build(pageInfo,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping(value = "/deleteById/{roleId}")
    public Result deleteById(@PathVariable(value = "roleId") Long roleId) {
        sysRoleService.deleteById(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
