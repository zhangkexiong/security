package com.zhang.security.controller;

import com.alibaba.fastjson.JSON;
import com.zhang.security.bean.User;
import com.zhang.security.service.UserService;
import com.zhang.security.utils.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/api/{username}")
    @ResponseBody
    public ResultType findByUsername(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        ResultType resultType=new ResultType();
        String date = JSON.toJSONString(user);
        resultType.setMessage(date);
        resultType.setStatus("200");
        resultType.setSuccess(true);
        return resultType;
    }
}
