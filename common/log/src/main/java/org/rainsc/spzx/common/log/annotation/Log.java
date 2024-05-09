package org.rainsc.spzx.common.log.annotation;

import org.rainsc.spzx.common.log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


// 定义注解的作用目标为方法
@Target({ElementType.METHOD})
// 定义注解的生命周期为运行时
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    public String title();                                // 模块名称

    public OperatorType operatorType() default OperatorType.MANAGE;    // 操作人类别

    public int businessType();     // 业务类型（0其它 1新增 2修改 3删除）

    public boolean isSaveRequestData() default true;   // 是否保存请求的参数

    public boolean isSaveResponseData() default true;  // 是否保存响应的参数
}
