package com.zhang.security.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhang.security.bean.User;
import com.zhang.security.dao.UserDao;
import com.zhang.security.service.UserService;
import com.zhang.security.utils.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Value("${USER_SESSION}")
    private String USER_SESSION;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public User findByUsername(String username) {
        return userDao.selectByUsername(username);
    }

    @Override
    public ResultType login(String username, String password) {
        ResultType resultType = new ResultType();
        User user = userDao.selectByUsername(username);
        if (user != null) {
            String pass = user.getPassword();
            //String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
            if (password.equals(pass)) {
                String token = USER_SESSION+UUID.randomUUID().toString();
                String userJson = JSON.toJSONString(user);
                //说明用户信息验证成功,将用户的信息保存到redis中并且设置过期时间
                redisTemplate.opsForValue().set(token,userJson,SESSION_EXPIRE,TimeUnit.SECONDS);
                //redisTemplate.opsForValue().set(token,userJson);
                //并且将token返回
                resultType.setMessage("用户登录成功并且将用户信息存放到redis数据库中");
                resultType.setStatus("200");
                resultType.setData(token);
                return resultType;
            }
        }
        resultType.setStatus("500");
        resultType.setMessage("用户名或者密码错误!!!");
        return resultType;
    }

    @Override
    public ResultType register(User user) {
        ResultType resultType = checkLogin(user.getUsername());
        if (resultType.isSuccess()) {
            String uuid = UUID.randomUUID().toString();
            user.setUuid(uuid);
            //使用spring自带的md5加密
            String newPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(newPassword);
            userDao.insertUser(user);
            resultType.setMessage("用户注册成功");
            return resultType;
            }
        resultType.setMessage("用户已经被注册");
        return resultType;
    }

    @Override
    public ResultType selectUserByToken(String token) {
        ResultType resultType = new ResultType();
        String userInfo = redisTemplate.opsForValue().get(token);
        System.out.println(userInfo);
        if (userInfo != null) {
            resultType.setData(userInfo);
            resultType.setStatus("200");
            resultType.setSuccess(true);
            resultType.setMessage("查询到用户的信息");
            return resultType;
        }
        resultType.setMessage("用户信息过期已经被销毁!!");
        return resultType;
    }

    @Override
    public ResultType removeUserByToken(String token) {
        ResultType resultType = new ResultType();
        resultType.setStatus("200");
        //1. 判断token是否为空
        if (token != null){
        redisTemplate.delete(token);
        resultType.setSuccess(true);
        resultType.setMessage("移除key成功");
        }
        return resultType;
    }

    /**
     * 校验用户名是否存在
     * @param username
     * @return
     */
    private ResultType checkLogin(String username){
        User user = userDao.selectByUsername(username);
        ResultType resultType = new ResultType();
        resultType.setStatus("200");
        if (user == null){
            resultType.setSuccess(true);
            return resultType;
        }
        return resultType;
    }
}
