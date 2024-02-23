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

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 删除角色
    @Override
    public void deleteById(Long roleId) {

    }

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
}
