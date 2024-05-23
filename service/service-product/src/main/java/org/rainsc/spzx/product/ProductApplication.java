package org.rainsc.spzx.product;

import org.rainsc.spzx.manager.serverUtils.RedisUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        // 检查Redis是否正在运行  为杜绝前端不知道redis是否启动而进行疯狂重定向
        boolean redisRunning = RedisUtil.isRedisRunning();

        if (!redisRunning) {
            // 如果Redis未启动，则启动Redis
            System.out.println("Redis , 启动!");
            RedisUtil.startRedis();
        }else System.out.println("已经开过啦");
        SpringApplication.run(ProductApplication.class, args);
    }

}