package com.zhang.security.interceptor;

import com.zhang.security.auth.MyAuthentication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * 用户的许可证(用户登陆成功之后访问)
 */
@Configuration
public class UserPermissionEvaluator implements PermissionEvaluator{
    /**
     * 根据权限的信息来,查询用户是否有权限
     * @param authentication
     * @param targetMainOperation
     * @param permission
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetMainOperation, Object permission) {
        authentication.getDetails();
        MyAuthentication auth= (MyAuthentication) authentication;
        auth.getName();
        Integer id= (Integer) targetMainOperation;
        if (id==1){
            return true;
        }
        System.out.println(auth.getName());
        /**
         * 当返回值是false的时候服务器会报403错误,表示没有权限
         */
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
