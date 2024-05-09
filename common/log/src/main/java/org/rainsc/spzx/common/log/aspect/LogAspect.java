package org.rainsc.spzx.common.log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.rainsc.spzx.common.log.annotation.Log;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LogAspect {
    // 环绕通知切面类定义
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) {
        String title = sysLog.title();
        log.info("LogAspect...doAroundAdvice方法执行了" + title);
        System.out.println("LogAspect...doAroundAdvice方法执行了" + title);
        // 业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();

            System.out.println("业务方法之后执行");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return proceed;
    }

}