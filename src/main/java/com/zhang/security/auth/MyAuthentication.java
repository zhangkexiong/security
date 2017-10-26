package com.zhang.security.auth;

import com.zhang.security.bean.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 用来存储身份认证信息的
 */
public class MyAuthentication implements Authentication {

    public MyAuthentication(){}

    private User user;

    /**
     * 获取到用户的权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 获取到文凭(证书)
     * 判断是否进行过认证
     * 1. 认证过后则获取到文凭
     * 2. 否则会则返回为null
     * @return
     */
    @Override
    public Object getCredentials() {
        if (!isAuthenticated()){
            return null;
        }
        return user.getId();
    }

    /**
     * 将用户对象注入进来
     * @param user
     */
    public void inject(final User user){
        this.user=user;
    }

    /**
     * 相当于返回的用户信息
     * @return
     */
    @Override
    public Object getDetails() {
        return user;
    }

    /**
     * 获取到用户凭证
     * @return
     */
    @Override
    public Object getPrincipal() {
        if (!isAuthenticated()){
            return null;
        }
        return user.getId();
    }

    /**
     * 判断是否认证过
     * @return
     */
    @Override
    public boolean isAuthenticated() {
        return user != null;
    }
    @Override
    public void setAuthenticated(boolean isAuthenticate) throws IllegalArgumentException {

    }

    /**
     * 获取到用户名称
     * @return
     */
    @Override
    public String getName() {
        if (!isAuthenticated()) {
            return null;
        }
        return user.getUsername();
    }
}
