package com.zhang.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @Author: Small Bear
 * @Description: 使用数据库中的资源表来实现权限
 * @Data:Create in 21:32 2017/11/6
 * @Modified By:Small Bear
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                /**
                 * 表示访问/index不需要登录权限
                 */
                .antMatchers("/index").permitAll()
                /**
                 * 访问其他都需要登录(authenticated表示是否已经登录)
                 */
                .anyRequest().authenticated()
                /**
                 * 表示访问/hello需要ADMIN的权限
                 */
                .antMatchers("/admin").hasRole("ADMIN")
                /**
                 * 表示登录页面是/login指向的视图
                 */
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index")
                .failureForwardUrl("/index")
                .permitAll()
                /**
                 * 表示登录成功之后将用户的信息存放在Handler中
                 */
                //.successHandler(loginSuccess())
                /**
                 * 退出的视图是/home所对应的视图,无须权限
                 */
                .and()
                .logout()
                .logoutSuccessUrl("/logout")
                .permitAll()
                .invalidateHttpSession(true);
        http.csrf().disable();
    }
   /* @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
        *//**
         * 指定密码加密所使用的加密器为passwordEncode()
         * 需要将密码加密后写入数据库中
         *//*
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);

    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }*/
    @Bean
    public AuthenticationSuccessHandler loginSuccess() {
        return new LoginSuccessHandler();
    }
}
