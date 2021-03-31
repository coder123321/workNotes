package com.sh.service.impl;

import com.sh.dao.UserDao;
import com.sh.entity.AjaxResponse;
import com.sh.entity.BaseUser;
import com.sh.entity.BaseUserRole;
import com.sh.exception.CustomException;
import com.sh.service.UserService;
import com.sh.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.sh.exception.CustomExceptionType.LOGIN_SUCCESS;
import static com.sh.exception.CustomExceptionType.SYSTEM_ERROR;

/**
 * Created by Administrator on 2020/12/28.
 */
@Service
public class UserServiceImpl  implements UserService{
    @Autowired
    private UserDao dao;
    @Override
    public BaseUser findUserById(String id) {
        BaseUser user = dao.findUserById(id);
        return user;
    }

    @Override
    public BaseUser querybyname(String name) {
        BaseUser user = dao.findUserById(name);
        return user;
    }

    @Override
    public List<BaseUserRole> queryOrganPermissionsByUserId(String name) {
        return null;
    }

    @Override
    public AjaxResponse login(String name, String pwd) {
        BaseUser user = dao.findUserById(name);
        if(null == user){
            throw new CustomException(SYSTEM_ERROR,"该用户名或者密码错误,请检查后再登录!");
        }
        String token = JwtUtil.sign(user.getId(), user.getPassword());

        return AjaxResponse.success(token);
    }

    @Override
    public AjaxResponse logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return AjaxResponse.success();
    }
}
