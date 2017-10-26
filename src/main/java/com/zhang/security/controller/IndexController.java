package com.zhang.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/api/index")
    @PreAuthorize("hasPermission(1,'index')")
    public String toIndex(){
        return "index";
    }
}
