package org.rainsc.spzx.manager.Service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.rainsc.spzx.common.log.annotation.Log;
import org.rainsc.spzx.exception.R_Exception;
import org.rainsc.spzx.manager.Mapper.SysRoleUserMapper;
import org.rainsc.spzx.manager.Mapper.SysUserMapper;
import org.rainsc.spzx.manager.Service.SysUserService;
import org.rainsc.spzx.model.dto.system.AssignRoleDto;
import org.rainsc.spzx.model.dto.system.LoginDto;
import org.rainsc.spzx.model.dto.system.SysUserDto;
import org.rainsc.spzx.model.entity.commonStr.CommonUse;
import org.rainsc.spzx.model.entity.system.SysUser;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.rainsc.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 实现用户管理服务的接口
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    /**
     * 用户登录
     *
     * @param loginDto 登录信息
     * @return 登录结果
     */
    @Override
    public LoginVo login(LoginDto loginDto) {
        // 获取redis中验证码code和前端code进行对比
        // 拿到前端输入的code和key
        String inCaptcha = loginDto.getCaptcha();
        String inCodeKey = loginDto.getCodeKey();

        String redisCode = redisTemplate.opsForValue().get("user:validate:" + inCodeKey);
        // 为空或者不相等
        if (StrUtil.isEmpty(inCaptcha) || !StrUtil.equalsIgnoreCase(redisCode, inCaptcha)) {
            throw new R_Exception(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }
        // 相同后删除验证码
        redisTemplate.delete("user:validate:" + inCodeKey);
        String userName = loginDto.getUserName();

        // 和数据库中的账号进行对比
        SysUser sysUser = sysUserMapper.selectUserInfoByUsername(userName);
        // 不存在返回错误信息
        if (sysUser == null) {
//            throw new RuntimeException("用户不存在,请检查用户名");
            throw new R_Exception(ResultCodeEnum.LOGIN_ACCOUNT_ERROR);
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
//            throw new RuntimeException("密码错误登录失败");
            throw new R_Exception(ResultCodeEnum.LOGIN_ERROR);
        }
        // 登录成功则生成token信息保存
        String token = UUID.randomUUID().toString().replace("-", "");

        // 然后把用户信息存在redis中
        // key: token  value: info
        String j_user = JSON.toJSONString(sysUser);
        redisTemplate.opsForValue()
                .set(CommonUse.REDIS_LOGIN_PREFIX + token, j_user, 7, TimeUnit.DAYS);
        // 返回loginVo
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);

        return loginVo;
    }

    /**
     * 获取用户信息
     *
     * @param token 用户令牌
     * @return 用户信息
     */
    @Override
    public SysUser getUserInfo(String token) {
        // 在redis中获取用户信息
        String userInfoJson = redisTemplate.opsForValue().get("user:login:" + token);
        SysUser sysUser = JSON.parseObject(userInfoJson, SysUser.class);
        return sysUser;
    }

    /**
     * 用户退出登录
     *
     * @param token 用户令牌
     */
    @Override
    public void logout(String token) {
        redisTemplate.delete(CommonUse.REDIS_LOGIN_PREFIX + token);
    }

    /**
     * 分页查询用户列表
     *
     * @param pageNum    当前页码
     * @param pageSize   每页大小
     * @param sysUserDto 用户查询条件
     * @return 分页结果
     */
    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 添加用户
     *
     * @param sysUser 用户信息
     */
    @Override
    public void saveSysUser(SysUser sysUser) {
        // 判断用户名是否存在 存在就抛出已存在
        String userName = sysUser.getUserName();
        SysUser dbUser = sysUserMapper.selectUserInfoByUsername(userName);
        //String dbUserName = dbUser.getUserName();
        if (dbUser != null) {
            throw new R_Exception(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        // 对密码进行加密
        String md5_password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        // 放回password
        sysUser.setPassword(md5_password);
        sysUser.setStatus(1);
        sysUserMapper.saveSysUser(sysUser);
    }

    /**
     * 更新用户信息
     *
     * @param sysUser 用户信息
     */
    @Override
    public void updateSysUser(SysUser sysUser) {

        // 从前端参数中获取用户输入的用户名
        String userName = sysUser.getUserName();

        // 从数据库中根据前端传来的id查询当前用户的用户名
        String webUserName = sysUserMapper.selectUserNameById(sysUser.getId());

        // 判断是否修改了用户名
        if (!userName.equals(webUserName)) {
            // 如果修改了用户名，则查询数据库确保新用户名不存在
            SysUser dbUser = sysUserMapper.selectUserInfoByUsername(userName);
            if (dbUser != null) {
                // 如果新用户名已存在于数据库中，则抛出异常
                throw new R_Exception(ResultCodeEnum.USER_NAME_IS_EXISTS);
            }
        }

        // 更新用户信息
        sysUserMapper.updateSysUser(sysUser);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    @Override
    public void delSysUser(Long userId) {
        sysUserMapper.delSysUser(userId);
    }

    /**
     * 分配角色给用户
     *
     * @param assignRoleDto 分配角色信息
     */
    @Log(title = "用户操作:分配角色", businessType = 0)
    @Transactional
    @Override
    public void doAssign(AssignRoleDto assignRoleDto) {
        // 先根据userid 删除用户已有的角色数据
        Long userId = assignRoleDto.getUserId();

        // todo 模拟异常情况
        int a = 1 / 0;
        System.out.println(a);

        sysRoleUserMapper.delOldId(userId);
        // 分配新的角色
        List<Long> roleIdList = assignRoleDto.getRoleIdList();
        for (Long roleId : roleIdList) {
            sysRoleUserMapper.doAssign(userId, roleId);
        }
    }
}
