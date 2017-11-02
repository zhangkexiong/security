package com.zhang.security.controller;

import com.zhang.security.bean.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping("/api/index")
    @PreAuthorize("hasPermission(1,'index')")
    public String toIndex(){
        return "index";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public User index(){
        User user=new User();
        user.setUsername("zhangsan");
        user.setPassword("123456");
        return user;
    }
}
