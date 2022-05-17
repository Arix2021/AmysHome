package com.alix.amypets.config;

import com.alix.amypets.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

/**
 * 拦截器配置类
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {

    // 注册自定义拦截器
    @Autowired
    private LoginInterceptor loginInterceptor = new LoginInterceptor();

    // 配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ArrayList<String> patterns = new ArrayList<>();
        // 定义放行资源
        // 静态资源
        patterns.add("/bootstrap/**");
        patterns.add("/lib/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/video/**");
        patterns.add("/js/**");
        patterns.add("/gif/**");
        patterns.add("/web/error/**");
        patterns.add("/error/**");

        // 默认允许访问的页面
        patterns.add("/web/admin/login.html");
        patterns.add("/web/user/account.html");
        patterns.add("/web/user/product.html");
        patterns.add("/index.html");

        // 默认允许访问的请求
        patterns.add("/");
        patterns.add("/favicon.ico");
        patterns.add("/error");
        patterns.add("/captcha");
        patterns.add("/users/**");
        patterns.add("/districts/**");
        patterns.add("/products/**");
        // 拦截请求
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }
}
