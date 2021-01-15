package com.sh.service.impl;

import com.sh.dao.UserDao;
import com.sh.entity.BaseUser;
import com.sh.entity.BaseUserRole;
import com.sh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        return null;
    }

    @Override
    public List<BaseUserRole> queryOrganPermissionsByUserId(String name) {
        return null;
    }
}
