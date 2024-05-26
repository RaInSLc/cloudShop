package org.rainsc.spzx.user.Service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.rainsc.spzx.user.Service.SmsService;
import org.rainsc.spzx.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * @param phone
     */
    @Override
    public void sendValidateCode(String phone) {
        // 测试直接取验证码 省的发送

        String code = redisTemplate.opsForValue().get(phone);

        if (StringUtils.hasText(code)) {
            return;
        }
        code = RandomStringUtils.randomNumeric(4);

        // 生成验证码 4位
//        String code = RandomStringUtils.randomNumeric(4);

        // 把验证码放进redis 5分钟有效 测试设置5天
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.DAYS);

        // 向手机号发送验证码
        sendMessage(phone, code);
    }

    // 发送短信验证码
    private void sendMessage(String phone, String code) {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:" + code);
        bodys.put("template_id", "CST_ptdie100");  //注意，CST_ptdie100该模板ID仅为调试使用，调试结果为"status": "OK" ，即表示接口调用成功，然后联系客服报备自己的专属签名模板ID，以保证短信稳定下发
        bodys.put("phone_number", phone);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**
 *重要提示：
 *如您的返回结果中，没有我们接口的返回报文，或者连header的信息都打印出来了。可能是您的代码环境未能适配该请求示例。
 *那么，以下两个命令行，您可以二选一，选择一个适合你环境的加入到请求示例中。即可打印我们接口的返回报文。
 *或者直接联系客服  VX 15622205140
 *
 *System.out.println(EntityUtils.toString(response.getEntity()));
 *
 *System.out.println(response.body().string());
 */
}
