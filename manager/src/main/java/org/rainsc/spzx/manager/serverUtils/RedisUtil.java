package org.rainsc.spzx.manager.serverUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// 本地测试使用redis  方式redis服务器未开启导致前端无限重定向
public class RedisUtil {
    // 检查Redis是否正在运行
    public static boolean isRedisRunning() {
        try {
            Process process = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("redis-server.exe")) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 启动Redis
    public static void startRedis() {
        try {
            Runtime.getRuntime().exec("E:\\Program Files\\Redis\\redis-server.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
