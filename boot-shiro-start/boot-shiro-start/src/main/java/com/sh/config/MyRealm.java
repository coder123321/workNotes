package com.sh.config;
import com.sh.entity.BaseUser;
import com.sh.entity.BaseUserRole;
import com.sh.jwt.JwtToken;
import com.sh.service.UserService;
import com.sh.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//登录及权限验证
public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserService us;
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof AesToken || token instanceof UsernamePasswordToken;
//    }
    /**
     * 必须重写此方法，不然Shiro会报错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

  //角色权限和对应权限添加
    //Authorization授权，将数据库中的角色和权限授权给输入的用户名
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        //获取登录的用户名
//        String phone = (String) principalCollection.getPrimaryPrincipal();
//        //记录用户的所有角色和权限
//        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();//权限信息
//        //到数据库里查询要授权的内容
//        BaseUser user = us.querybyname(phone);
//        if(null!= user){
//            List<BaseUserRole> userPermissions = us.queryOrganPermissionsByUserId(user.getId());
//            if(userPermissions.size()>0){
//                for(int a=0;a<userPermissions.size();a++){
//                    simpleAuthorizationInfo.addRole(userPermissions.get(a).getRoleId());
//                    simpleAuthorizationInfo.addStringPermission(userPermissions.get(a).getOrganId());
//                }
//            }
//        }
////        for(Role r:user.getRoles()){
////            //将所有的角色信息添加进来。
////            simpleAuthorizationInfo.addRole(r.getRname());
////            for(Permission p:r.getPermissions()){
////                //将此次遍历到的角色的所有权限拿到，添加·进来
////                simpleAuthorizationInfo.addStringPermission(p.getPname());
////            }
////        }
//        return simpleAuthorizationInfo;
//    }

    //用户身份验证
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        //从token获取用户名,从主体传过来的认证信息中获取
//        //加这一步的目的是在post请求时会先进入认证然后再到请求。
//        if(authenticationToken.getPrincipal()==null){
//            return null;
//        }
//        //获取用户的登录信息，用户名
//        String phone=authenticationToken.getPrincipal().toString();
//
//        //根据service调用用户名，查找用户的全部信息
//        //通过用户名到数据库获取凭证
//        BaseUser user=us.querybyname(phone);
//        if(user==null){
//            //这里返回会报出对应异常
//            return  null;
//        }else{
//            //这里验证authenticationToken和simpleAuthenticationInfo的信息
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(phone,user.getPassword(),getName());
//            return simpleAuthenticationInfo;
//        }
//
//    }
    /**
     * 获取用户登录信息
     *
     *org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY ; com.hncxhd.bywl.entity.manual.UserInfo@533752b2
     */
//    public BaseUser getUserInfo(String sessionID, HttpServletRequest request, HttpServletResponse response){
//        boolean status = false;
//        SessionKey key = new WebSessionKey(sessionID,request,response);
//        try{
//            Session se = SecurityUtils.getSecurityManager().getSession(key);
//            Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//            //org.apache.shiro.subject.SimplePrincipalCollection cannot be cast to com.hncxhd.bywl.entity.manual.UserInfo
//            SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
//            return (BaseUser)coll.getPrimaryPrincipal();
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//        }
//        return null;
//    }


    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JwtUtil.getUsername(principals.toString());
        BaseUser user = us.querybyname(username);
//        String [] roles = {};
        String [] roles = {"admin"};
//        String [] roles = {"admin","root"};
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(roles.length>0){
            for(int a=0;a<roles.length;a++){
                simpleAuthorizationInfo.addRole(roles[a]);
                simpleAuthorizationInfo.addStringPermission(roles[a]);
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = null;
        try {
            //这里工具类没有处理空指针等异常这里处理一下(这里处理科学一些)
            username = JwtUtil.getUserId(token);
        } catch (Exception e) {
            throw new AuthenticationException("heard的token拼写错误或者值为空");
        }
        if (username == null) {
//            log.error("token无效(空''或者null都不行!)");
            throw new AuthenticationException("token无效");
        }
        BaseUser userBean = us.findUserById(username);
        if (userBean == null) {
//            log.error("用户不存在!)");
            throw new AuthenticationException("用户不存在!");
        }
        if (!JwtUtil.verify(token, username, userBean.getPassword())) {
//            log.error("用户名或密码错误(token无效或者与登录者不匹配)!)");
            throw new AuthenticationException("用户名或密码错误(token无效或者与登录者不匹配)!");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }





}
