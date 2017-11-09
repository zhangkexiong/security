package com.zhang.security.auth;

import com.zhang.security.bean.User;
import com.zhang.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: Small Bear
 * @Description:用于返回用户的基本信息和权限信息
 * @Data:Create in 21:56 2017/11/6
 * @Modified By:Small Bear
 */
@Component
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserService userService;
    /**
     * 根据用户名来获取用户的信息,并将其加载到userDetails中
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("no user");
        }
        UserDetails userDetails = new UserDetails() {
            /**
             * 此方法用来装载用户的权限信息
             * @return
             */
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ADMIN");
                SimpleGrantedAuthority simpleGrantedAuthority2 = new SimpleGrantedAuthority("ROLE_USER");
                SimpleGrantedAuthority simpleGrantedAuthority3 = new SimpleGrantedAuthority("ROLE_ADMIN");
                authorities.add(simpleGrantedAuthority);
                authorities.add(simpleGrantedAuthority2);
                authorities.add(simpleGrantedAuthority3);
                return null;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
        return userDetails;
    }
}
