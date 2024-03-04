package org.rainsc.spzx.manager.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.manager.Mapper.SysRoleMapper;
import org.rainsc.spzx.manager.Service.SysRoleService;
import org.rainsc.spzx.model.dto.system.SysRoleDto;
import org.rainsc.spzx.model.entity.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    // 查找角色列表
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);
        // 根据条件查询出数据
        List<SysRole> list = sysRoleMapper.findByPage(sysRoleDto);
        // 封装pageinfo 对象
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    // 添加角色
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    // 角色修改
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }


    // 删除角色
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.remove(roleId);
    }

    // 查询所有角色
    @Override
    public Map<String, Object> findAllRoles() {
        List<SysRole> sysRoleList = sysRoleMapper.findAllRoles();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("allRolesList", sysRoleList);
        return resultMap;

    }
}
