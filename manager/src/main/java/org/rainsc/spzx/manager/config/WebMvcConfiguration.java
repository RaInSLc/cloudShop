package org.rainsc.spzx.manager.config;

import lombok.extern.slf4j.Slf4j;
import org.rainsc.spzx.manager.Interceptor.LoginAuthInterceptor;
import org.rainsc.spzx.manager.properties.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 跨域请求处理
@Slf4j
@Component
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;
    @Autowired
    private UserProperties userProperties;

    // 跨域请求放行
    //跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 添加路径规则
                .allowCredentials(true)               // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")           // 允许请求来源的域规则
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    // 拦截器注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 放行swagger
        String[] excludePatterns = new String[]{"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                "/api", "/api-docs", "/api-docs/**", "/doc.html/**"};


        registry.addInterceptor(loginAuthInterceptor)
//                .excludePathPatterns("/admin/system/index/login"
//                        , "/admin/system/index/generateValidateCode")
//                .excludePathPatterns(excludePatterns)
                .excludePathPatterns(userProperties.getNoAuthUrls())
//                .excludePathPatterns("/**")
//不拦截路由
                .excludePathPatterns("/**/login")
                .excludePathPatterns("/doc.html/**")
                .excludePathPatterns("/doc.html#/**")
                .excludePathPatterns("/swagger-ui.html")

                //让后台文档不可用可以直接注释掉这两个v2 v3
                .excludePathPatterns("/v2/api-docs/**")    // swagger api json
                .excludePathPatterns("/v3/api-docs/**")
                .excludePathPatterns("/swagger-resources/configuration/ui")// 用来获取支持的动作
                .excludePathPatterns("/swagger-resources")       // 用来获取api-docs的URI
                .excludePathPatterns("/swagger-resources/configuration/security")    // 安全选项
                .excludePathPatterns("/swagger-resources/**")
                //补充路径，近期在搭建swagger接口文档时，通过浏览器控制台发现该/webjars路径下的文件被拦截，故加上此过滤条件即可。(2020-10-23)
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/error")
                .addPathPatterns("/**");

//        log.info("{}", userProperties.getNoAuthUrls());
    }
}