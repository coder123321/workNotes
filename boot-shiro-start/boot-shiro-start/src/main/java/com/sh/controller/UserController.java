package com.sh.controller;

import com.sh.entity.AjaxResponse;
import com.sh.entity.BaseUser;
import com.sh.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2020/12/28.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;
    @RequiresRoles(value = {"admin"})
    @RequiresPermissions(value = {"admin"})
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse getUserInfo(HttpServletRequest request){
        String id = "system";
        BaseUser user = service.findUserById(id);
        return AjaxResponse.success(user,"登录成功");
    }
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse logout(HttpServletRequest request){
       service.logout();
        return AjaxResponse.success();
    }
}
