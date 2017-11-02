package com.zhang.security.controller;

import com.zhang.security.bean.User;
import com.zhang.security.service.UserService;
import com.zhang.security.utils.CookieUtils;
import com.zhang.security.utils.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResultType login(@RequestParam("password") String password, @RequestParam("username") String username, HttpServletRequest request, HttpServletResponse response){
//        User user=new User();
//        user.setId(id);
//        user.setUsername(username);
//        user.setPassword(password);
//        非单点登录使用session来保存用户信息
//        httpSession.setAttribute(Constants.USER,user);
        ResultType resultType=userService.login(username,password);
        //登录成功之后将token写入到cookie中
        if ("200".equals(resultType.getStatus())) {
            String token = resultType.getData();
            CookieUtils.setCookie(request, response, TOKEN_KEY, token);
        }
        return resultType;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResultType register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping("/user/{token}")
    @ResponseBody
    public ResultType selectUserByToken(@PathVariable("token") String token){
        return userService.selectUserByToken(token);
    }
}
