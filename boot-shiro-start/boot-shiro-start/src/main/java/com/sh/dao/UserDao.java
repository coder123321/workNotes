package com.sh.dao;

import com.sh.entity.BaseUser;
import com.sh.entity.BaseUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2020/12/28.
 */
@Repository
public interface  UserDao {
    //使用@Param
    public BaseUser findUserById(@Param("id") String id);

    public BaseUser querybyname(@Param("name") String name);

    public List<BaseUserRole> queryOrganPermissionsByUserId(@Param("userId") String userId);

    public BaseUser querybyUserinfo(@Param("name") String name,@Param("pwd") String pwd);
}
