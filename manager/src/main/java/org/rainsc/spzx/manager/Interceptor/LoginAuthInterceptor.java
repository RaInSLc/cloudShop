package org.rainsc.spzx.manager.Interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rainsc.spzx.model.entity.commonStr.CommonUse;
import org.rainsc.spzx.model.entity.system.SysUser;
import org.rainsc.spzx.model.vo.common.Result;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;
import org.rainsc.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    static String options = "OPTIONS";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取请求方式
        // 如果是options  预检请求 , 直接放行
        String method = request.getMethod();
        if (options.equals(method)) {
            return true;
        }
        // 如果是真请求  从请求头中获取token
        String token = request.getHeader("token");
        // 如果token为空 返回错误提示
        if (StrUtil.isEmpty(token)) {
            responseNoLoginInfo(response);
//            System.out.println("没拿到token");
            return false;
        }
        // 如果token不为空   拿着token在redis中获取相关信息
        String userInfoString = redisTemplate.opsForValue().get(CommonUse.REDIS_LOGIN_PREFIX + token);


        // 如果redis查不到信息 返回错误提示
        if (StrUtil.isEmpty(userInfoString)) {
            responseNoLoginInfo(response);
            return false;
        }
        // redis查到用户信息 将信息放入线程池 thread local中
        // TODO 没懂这里为什么需要这么转换
        SysUser sysUser = JSON.parseObject(userInfoString, SysUser.class);
        AuthContextUtil.set(sysUser);
        // 把redis中的用户信息更新过期时间
        redisTemplate.expire(CommonUse.REDIS_LOGIN_PREFIX + token, 30, TimeUnit.MINUTES);
        // 放行

        return true;
    }


    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        // 删除thread local中的数据
        AuthContextUtil.remove();
    }
}
