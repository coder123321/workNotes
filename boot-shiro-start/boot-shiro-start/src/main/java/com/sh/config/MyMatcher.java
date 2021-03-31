package com.sh.config;

import com.sh.jwt.JwtToken;
import com.sh.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;

//验证密码 查找到了1该用户 自定义密码验证器
public class MyMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
//        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        JwtToken tokenStr = (JwtToken) token;
        String tokenrel = (String) tokenStr.getCredentials();
//        String pwdSTR = JwtUtil.getUserPwd(tokenrel);

//        String pwd = encrypt(String.valueOf(pwdSTR));
        String mysqlpwd = (String) info.getCredentials();
        return this.equals(tokenrel, mysqlpwd);
    }


    //将传进来的密码进行加密的方法
    private String encrypt(String data){
        String sha384Hex = DigestUtils.md5DigestAsHex(data.getBytes());//md5加密
//        String sha384Hex=new Sha384Hash(data).toBase64();
        return sha384Hex;
    }

}
