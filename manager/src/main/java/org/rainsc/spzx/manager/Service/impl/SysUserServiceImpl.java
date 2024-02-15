package org.rainsc.spzx.manager.Service.impl;


import com.alibaba.fastjson.JSON;
import org.rainsc.spzx.manager.Mapper.SysUserMapper;
import org.rainsc.spzx.manager.Service.SysUserService;
import org.rainsc.spzx.model.dto.system.LoginDto;
import org.rainsc.spzx.model.entity.system.SysUser;
import org.rainsc.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
        // 获取提交的用户名
        String userName = loginDto.getUserName();

        // 和数据库中的账号进行对比
        SysUser sysUser = sysUserMapper.selectUserInfoByUsername(userName);
        // 不存在返回错误信息
        if (sysUser == null) {
            throw new RuntimeException("用户不存在,请检查用户名");
        }
        // 存在则开始比对密码
        // 用户名在数据库中对应的密码
        String db_password = sysUser.getPassword();
        // 用户输入的密码
        String in_password = loginDto.getPassword();
        // 加密一下
        in_password = DigestUtils.md5DigestAsHex(in_password.getBytes());
        // 密码一致登录成功 否则返回登录失败
        if (!in_password.equals(db_password)) {
            throw new RuntimeException("密码错误登录失败");
        }
        // 登录成功则生成token信息保存
        String token = UUID.randomUUID().toString().replace("-", "");

        // 然后把用户信息存在redis中
        // key: token  value: info
        String j_user = JSON.toJSONString(sysUser);
        redisTemplate.opsForValue()
                .set("user:login" + token, j_user, 7, TimeUnit.DAYS);
        // 返回loginVo
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);

        return loginVo;
    }
}
