package com.zhang.security.service;

import com.zhang.security.bean.User;
import com.zhang.security.utils.ResultType;

public interface UserService {
    User findByUsername(String username);

    ResultType login(String username,String password);

    ResultType register(User user);

    ResultType selectUserByToken(String token);

    ResultType removeUserByToken(String token);
}
