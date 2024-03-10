package org.rainsc.spzx.manager.Controller;

import org.rainsc.spzx.manager.Service.SysRoleMenuService;
import org.rainsc.spzx.model.dto.system.AssginMenuDto;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
public class SysRoleMenuController {


    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    // 查询所有菜单 和 已经分配过菜单的
    @GetMapping(value = "/findSysRoleMenuByRoleId/{roleId}")
    public Result findSysRoleMenuByRoleId(@PathVariable("roleId") Long roleId) {
        Map<String, Object> map = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    // 保存角色分配菜单的数据
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto) {
        sysRoleMenuService.doAssign(assginMenuDto);

        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
