/*
package com.zhang.security.auth;

import com.zhang.security.filter.SessionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebAuthConfiguration extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        */
/**
         * 表示在加载filter之前加载SessionFilter
         *//*

        httpSecurity.addFilterBefore(getSessionFilter(), SecurityContextPersistenceFilter.class)
                //关闭掉csrf认证
                .csrf().disable();
    }
    @Bean
    public SessionFilter getSessionFilter(){
        return new SessionFilter();
    }

}
*/
