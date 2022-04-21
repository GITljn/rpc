package com.ljn.httpclientserver.controller;

import com.ljn.httpclient.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "{\"msg\":\"处理结果\"}";
    }

    @RequestMapping("/params")
    @ResponseBody
    public String params(String username, String password) {
        return "{username:"+username+", password:"+password+"}";
    }

    @RequestMapping("/json")
    @ResponseBody
    @CrossOrigin
    public String json(@RequestBody List<User> users) {
        System.out.println(users.toString());
        return users.toString();
    }
}
