package com.zhang.security.auth;

import com.zhang.security.bean.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 用来存储身份认证信息的
 */
public class MyAuthentication implements Authentication{

    private User user;

    /**
     * 获取到用户的权限
     * @return
     */
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
    public void inject(User user){
        this.user=user;
    }

    /**
     * 相当于返回的用户信息
     * @return
     */
    public Object getDetails() {
        return user;
    }

    /**
     * 获取到用户凭证
     * @return
     */
    public Object getPrincipal() {
        return null;
    }

    /**
     * 判断是否认证过
     * @return
     */
    public boolean isAuthenticated() {
        return user!=null;
    }

    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    /**
     * 获取到用户名称
     * @return
     */
    public String getName() {
        if (!isAuthenticated()) {
            return null;
        }
        return user.getUsername();
    }
}
