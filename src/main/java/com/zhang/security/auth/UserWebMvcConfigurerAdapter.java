package com.zhang.security.auth;

import com.zhang.security.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 实现自定义的拦截器
 * 1.定义自定义拦截器实现HandlerInterceptor接口
 * 2.在创建一个类继承WebMvcConfigurerAdapter并且重写里面的addInterceptors方法
 * 3.在addInterceptor中添加
 * 配置spring boot 的拦截器
 */
@Configuration
public class UserWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
