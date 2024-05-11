package org.rainsc.spzx.common.log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.rainsc.spzx.common.log.annotation.Log;
import org.rainsc.spzx.common.log.service.AsyncOperLogService;
import org.rainsc.spzx.common.log.utils.LogUtil;
import org.rainsc.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private AsyncOperLogService operLogService;

    /**
     * 环绕通知，用于捕获带有@Log注解的方法执行，并记录操作日志
     *
     * @param joinPoint 切点
     * @param sysLog    日志注解
     * @return 切点方法的返回值
     * @throws Throwable 可能抛出的异常
     */
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) throws Throwable {
        // 创建操作日志对象
        SysOperLog sysOperLog = new SysOperLog();
        // 在方法执行前记录日志信息
        LogUtil.beforeHandleLog(sysLog, joinPoint, sysOperLog);

        Object proceed = null;
        try {
            // 执行被注解的方法
            proceed = joinPoint.proceed();
            // 方法执行成功后记录日志
            LogUtil.afterHandlLog(sysLog, proceed, sysOperLog, 0, null);
        } catch (Throwable e) {
            // 方法执行出现异常时记录异常信息并抛出
            e.printStackTrace();
            log.error("Error message", e);
            LogUtil.afterHandlLog(sysLog, proceed, sysOperLog, 1, e.getMessage());
            throw e;
        }

        // 调用Service保存操作日志到数据库
        operLogService.saveSysOperLog(sysOperLog);

        return proceed;
    }
}
