package com.zimug.courses.security.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by 070819 on 2021/1/10.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler  successhandler;
    @Autowired
    private MyAuthenticationFailureHandler  failurehandler;
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()//开启httpbasic认证
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated();//所有请求都需要登录认证才能访问
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin().
                loginPage("/login.html").//登录页
                loginProcessingUrl("/login")//登录请求
                .usernameParameter("uname")//登录用户名
                .passwordParameter("pword")//登录密码
                .successHandler(successhandler)
                .failureHandler(failurehandler)
//                .defaultSuccessUrl("/")//成功后的请求
//                .failureUrl("/login.html")//失败后的请求
                .and()
                .authorizeRequests()//匹配规则
                .antMatchers("/login.html","/login").permitAll()//不需要任何限制都可以访问
                .antMatchers("/","/biz1","/biz2").hasAnyAuthority("ROLE_user","ROLE_admin")//需要登录成功，就可以访问
//                .antMatchers("/syslog","/sysuser").hasAnyRole("admin")//角色是admin才能访问
                .antMatchers("/syslog").hasAuthority("sys:log")
                .antMatchers("/sysuser").hasAuthority("sys:user")
                .anyRequest()
                .authenticated()//所有请求都需要登录认证才能访问
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)//session会话管理，如果需要就创建一个
        .sessionFixation().migrateSession()//每次会话都会改变sessionID
        .invalidSessionUrl("/invalidSession.html");//超时后的路径
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("123456")).roles("user")
                .and().withUser("admin").password(passwordEncoder().encode("123456")).authorities("sys:log")
//                .and().withUser("admin").password(passwordEncoder().encode("123456")).roles("admin")
                .and().passwordEncoder(passwordEncoder());

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","fonts/**","/js/**","/img/**","/static/**");
    }
}
