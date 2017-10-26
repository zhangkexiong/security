package com.zhang.security.controller;

import com.zhang.security.bean.User;
import com.zhang.security.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public User login(Integer id, String password,String username, HttpSession httpSession){
        User user=new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        httpSession.setAttribute(Constants.USER,user);
        return user;
    }
}
