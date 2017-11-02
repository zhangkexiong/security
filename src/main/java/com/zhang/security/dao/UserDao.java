package com.zhang.security.dao;

import com.zhang.security.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User selectByUsername(String username);

    void insertUser(User user);
}
