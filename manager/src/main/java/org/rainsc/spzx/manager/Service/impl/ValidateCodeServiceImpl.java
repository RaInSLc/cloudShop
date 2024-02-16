package org.rainsc.spzx.manager.Service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import org.rainsc.spzx.manager.Service.ValidateCodeService;
import org.rainsc.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    // 生成图片验证码
    @Override
    public ValidateCodeVo generateValidateCode() {
        // 通过hutool工具生成图片验证码
        //int width, int height, int codeCount, int circleCount
        // 宽 长 位数 干扰数
        CircleCaptcha circleCaptcha =
                CaptchaUtil.createCircleCaptcha(150, 48, 3, 1);
        String code = circleCaptcha.getCode(); // 验证码
        String imageBase64 = circleCaptcha.getImageBase64(); // base64编码的图

        // 生成后存入redis key:uuid value:验证码值
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue()
                .set("user:validate:" + key, code, 5, TimeUnit.MINUTES);
        // 返回封装的验证码VO
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        return validateCodeVo;
    }
}
