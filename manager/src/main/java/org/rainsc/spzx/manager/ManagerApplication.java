package org.rainsc.spzx.manager;


import org.rainsc.spzx.common.log.annotation.EnableLogAspect;
import org.rainsc.spzx.manager.properties.MinioProperties;
import org.rainsc.spzx.manager.properties.UserProperties;
import org.rainsc.spzx.manager.serverUtils.RedisUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
// 扫描自定义注解
@EnableLogAspect
@SpringBootApplication
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
@ComponentScan(basePackages = {"org.rainsc.spzx"})
@EnableScheduling
public class ManagerApplication {
    public static void main(String[] args) {
        // 检查Redis是否正在运行  为杜绝前端不知道redis是否启动而进行疯狂重定向
        boolean redisRunning = RedisUtil.isRedisRunning();

        if (!redisRunning) {
            // 如果Redis未启动，则启动Redis
            System.out.println("Redis , 启动!");
            RedisUtil.startRedis();
        }else System.out.println("已经开过啦");

        // 启动Spring Boot应用程序
        SpringApplication.run(ManagerApplication.class);
        System.out.println("http://127.0.0.1:8501/doc.html");
    }
}
