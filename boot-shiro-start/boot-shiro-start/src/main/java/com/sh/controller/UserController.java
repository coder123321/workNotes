package com.sh.controller;

import com.sh.entity.BaseUser;
import com.sh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Administrator on 2020/12/28.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;
    @RequestMapping("/login")
    @ResponseBody
    public String test(Map<String,Object> map){
        String id = "system";
        BaseUser user = service.findUserById(id);
        return user.toString();
    }
}
