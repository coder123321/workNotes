package com.sh.config;


import com.sh.config.cache.CustomCacheManager;
import com.sh.filter.AesFilter;
import com.sh.util.RedisDTO;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Autowired
    private RedisDTO dto;

    //密码验证器
    @Bean("credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new MyMatcher();
    }

    //权限验证器
    @Bean("myRealm")
    public MyRealm myRealm(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
            MyRealm myRealm = new MyRealm();
        //给权限验证器配置上自定义的密码验证器
        myRealm.setCredentialsMatcher(credentialsMatcher);
        return myRealm;
    }
    //权限验证器
    @Bean("myRealm1")
    public MyRealm1 myRealm1(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
        MyRealm1 myRealm1 = new MyRealm1();
        //给权限验证器配置上自定义的密码验证器
        myRealm1.setCredentialsMatcher(credentialsMatcher);
        return myRealm1;
    }
    //权限验证器
    @Bean("myRealm2")
    public MyRealm2 myRealm2(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
        MyRealm2 myRealm2 = new MyRealm2();
        //给权限验证器配置上自定义的密码验证器
        myRealm2.setCredentialsMatcher(credentialsMatcher);
        return myRealm2;
    }

//    @Bean
//    public CacheManager cacheManager(){
//        return new MemoryConstrainedCacheManager();
//    }
    /**
     * 生成一个ShiroRedisCacheManager
     **/
    private CustomCacheManager cacheManager(RedisTemplate template) {
        return new CustomCacheManager(template);
    }

    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
//        这个参数是cookie的名称，对应前端的checkbox的name=rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        cookie生效时间为10秒
        simpleCookie.setMaxAge(10);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return  cookieRememberMeManager;
    }

    /**
     * 自定义sessionManager
     * @return
     */
//    @Bean
//    public SessionManager sessionManager(){
//        return new CustomSessionManager();
//    }
    //自定义sessionManager
//    @Bean
//    public SessionManager sessionManager() {
//        CustomSessionManager mySessionManager = new CustomSessionManager();
//        mySessionManager.setSessionDAO(redisSessionDAO());
//        return mySessionManager;
//    }
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        ModularRealmAuthenticator modularRealmAuthenticator=new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    //桥梁，主要是Realm的管理认证配置
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("myRealm") MyRealm myRealm,@Qualifier("myRealm1") MyRealm1 myRealm1,@Qualifier("myRealm2") MyRealm2 myRealm2,RedisTemplate<String, Object> redisTemplate) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //注入自定义myRealm
//        defaultWebSecurityManager.setRealm(myRealm);
//        defaultWebSecurityManager.setRealm(myRealm1);
//        defaultWebSecurityManager.setRealm(myRealm2);
        ArrayList<Realm> lsit = new ArrayList<Realm>();
        lsit.add(myRealm);
        lsit.add(myRealm1);
        lsit.add(myRealm2);
//        Collection<Realm> realms = new Collection<Realm>();
        defaultWebSecurityManager.setRealms(lsit);
        //注入自定义cacheManager
//        defaultWebSecurityManager.setCacheManager(cacheManager());
        //注入记住我管理器
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        //注入自定义sessionManager
//        defaultWebSecurityManager.setSessionManager(sessionManager());

        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);

        //注入自定义cacheManager
        defaultWebSecurityManager.setCacheManager(cacheManager(redisTemplate));
        //自定义缓存实现，使用redis
//        defaultWebSecurityManager.setSessionManager(SessionManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public AesFilter aesFilter() {
        return new AesFilter();
    }
    //进行全局配置，Filter工厂，设置对应的过滤条件和跳转条件
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        //shiro对象
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/unauth");
//        bean.setSuccessUrl("/index");
        Map<String,Filter> filters = bean.getFilters();
        filters.put("authc", new AesFilter());
        bean.setFilters(filters);
//        filters.put("corsAuthenticationFilter", new CORSAuthenticationFilter());


//        Map<String, Filter> filterMap=new LinkedHashMap<String,Filter>();
//       filterMap.put("MyRememberFilter",MyRememberFilter());
//        bean.setFilters(filters);
       /* //自定义拦截器
        Map<String, Filter> filterMap=new LinkedHashMap<String,Filter>();
        //限制同一账号同时在线的个数
//        filterMap.put("kickout",kickoutSessionControlFilter());
//        bean.setFilters(filterMap);*/
        //MAP
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        /*
        认证顺序是从上往下执行。
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 :这是一个坑呢，一不小心代码就不好使了;
        // ① authc:所有url都必须认证通过才可以访问; ② anon:所有url都都可以匿名访问
         */
        linkedHashMap.put("/logout", "logout");//在这儿配置登出地址，不需要专门去写控制器。
        linkedHashMap.put("/static/**", "anon");
        //开启注册页面不需要权限
        linkedHashMap.put("/register", "anon");
        linkedHashMap.put("/saveregister", "anon");
        linkedHashMap.put("/forgetpwd", "anon");//不需要验证
        linkedHashMap.put("/notVerify/**", "anon");//不需要验证
        linkedHashMap.put("/user/**", "authc");//需要验证
        //验证phone唯一
//        linkedHashMap.put("/solephone", "anon");
        //获取验证码
//        linkedHashMap.put("/getcode", "anon");
        //验证码判断
//        linkedHashMap.put("/comparecode", "anon");
//        linkedHashMap.put("/websocket", "anon");//必须开启。
//        linkedHashMap.put("/css/**", "anon");//不需要验证
//        linkedHashMap.put("/js/**", "anon");//不需要验证
        //配置错误页面
//        linkedHashMap.put("error", "anon");//不需要验证
//        linkedHashMap.put("/img/**", "anon");//不需要验证
//        linkedHashMap.put("/layui/**", "anon");//不需要验证
//        linkedHashMap.put("/video/**", "anon");//不需要验证
//        linkedHashMap.put("/bower_components/**", "anon");//不需要验证
//        linkedHashMap.put("/plugins/**", "anon");//不需要验证
//        linkedHashMap.put("/dist/**", "anon");//不需要验证

//        linkedHashMap.put("/**", "user");//需要进行权限验证

        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
//        bean.setLoginUrl("/unauth");

        bean.setFilterChainDefinitionMap(linkedHashMap);
        return bean;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    //加入·注解的使用，不加入这个注解不生效
    //启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
    //     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }
    /**
     * 配置shiro redisManager
     *
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost("127.0.0.1");
//        redisManager.setPort(6379);
//        redisManager.setExpire(60000);// 配置缓存过期时间
//        redisManager.setTimeout(20000);
//        redisManager.setPassword("123456");
//        return redisManager;
//    }
    /**
     * cacheManager 缓存 redis实现
     *
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
//    @Bean
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        redisCacheManager.setKeyPrefix("cache_");
//        return redisCacheManager;
//    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     *
     * 使用的是shiro-redis开源插件
     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
//        return redisSessionDAO;
//    }

    /**
     * Session ID 生成器
     * <br/>
     * create by: leigq
     * <br/>
     * create time: 2019/7/3 16:08
     *
     * @return JavaUuidSessionIdGenerator
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }


    //    进行权限认证的，没有会使得前台的shiro标签无法使用
    //shiro结合thymeleaf实现细粒度权限控制
//    @Bean
//    public ShiroDialect shiroDialect() {
//        return new ShiroDialect();
//    }


}



