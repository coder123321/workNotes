package com.sh.service;

import com.sh.entity.BaseUser;
import com.sh.entity.BaseUserRole;

import java.util.List;

/**
 * Created by Administrator on 2020/12/28.
 */
public interface UserService {
     BaseUser findUserById(String id);
     BaseUser querybyname(String name);
     List<BaseUserRole> queryOrganPermissionsByUserId(String name);

}
