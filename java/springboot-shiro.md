# Shiro入门-缓存

### **shiro默认是关闭认证信息缓存的，对于授权信息的缓存shiro默认开启的。\**主要研究授权信息缓存，因为授权的数据量大。

参考

https://www.doufuplus.com/blog/shiro-jwt03.html

[https://blog.csdn.net/wozniakzhang/article/details/95855296?ops_request_misc=&request_id=&biz_id=102&utm_term=shiro%E5%89%8D%E5%90%8E%E7%AB%AF%E5%88%86%E7%A6%BBtoken&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-2-95855296.pc_search_result_no_baidu_js&spm=1018.2226.3001.4187](https://blog.csdn.net/wozniakzhang/article/details/95855296?ops_request_misc=&request_id=&biz_id=102&utm_term=shiro前后端分离token&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-2-95855296.pc_search_result_no_baidu_js&spm=1018.2226.3001.4187)



https://blog.csdn.net/qq_43721032/article/details/110188342?utm_medium=distribute.pc_relevant.none-task-blog-OPENSEARCH-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-OPENSEARCH-2.control



认证不会缓存，权限会缓存



# springboot+shiro做统一的异常处理

## 1.shiro的常见异常

#### 1.1 AuthencationException:

```
    AuthenticationException 异常是Shiro在登录认证过程中，认证失败需要抛出的异常。 AuthenticationException包含以下子类：
1
```

- CredentitalsException 凭证异常

  ```
      IncorrectCredentialsException 不正确的凭证
      ExpiredCredentialsException 凭证过期
  ```
  
- AccountException 账号异常

  ```
      ConcurrentAccessException 并发访问异常（多个用户同时登录时抛出）
      UnknownAccountException 未知的账号
      ExcessiveAttemptsException 认证次数超过限制
      DisabledAccountException 禁用的账号
          LockedAccountException 账号被锁定
  ```
  
- UnsupportedTokenException 使用了不支持的Token

#### 1.2AuthorizationException:

子类:

- UnauthorizedException:抛出以指示请求的操作或对请求的资源的访问是不允许的。
- UnanthenticatedException:当尚未完成成功认证时，尝试执行授权操作时引发异常。

## 2.异常处理方法

#### 2.1在shiro配置文件中配置

#### 2.1.1自己创建GlobalExceptionResolver

```
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv;
        System.out.println(ex instanceof UnauthenticatedException);
        //进行异常判断。如果捕获异常请求跳转。
        if (ex instanceof UnauthorizedException) {
        	mv = new ModelAndView("/error/error");
            ex.printStackTrace();
            mv.addObject("msg", "你的级别还不够高,加油吧！少年。");  
            return mv;
        }
        else if(ex instanceof UnauthenticatedException){
            mv = new ModelAndView("/error/error");
            ex.printStackTrace();
            mv.addObject("msg", "没有此权限！");          
            return mv;
        }      
        else {
            mv = new ModelAndView("/error/error");
            ex.printStackTrace();
            mv.addObject("msg", "我勒个去，页面被外星人挟持了!");          
            return mv;

        }

    }
}
```

#### 2.1.2在ShiroConfiguration中配置

```
@Bean(name = "exceptionHandler")
  public HandlerExceptionResolver handlerExceptionResolver(){
      return new GlobalExceptionResolver();
}
1234
```

#### 2.2 使用@ExceptionHandler配置

创建异常处理类：这是基于springmvc的

```java
@ControllerAdvice
public class AllException{
	  @ExceptionHandler
       public String exceptiona(Exception ex) {		 
    	   if(ex instanceof UnauthenticatedException) {
    		   return "redirect:/login";    		 
    	     }
    	   else if(ex instanceof UnauthorizedException) {
    		   return "error/error";
 		     }
    	   else if(ex instanceof Exception){
    		   
    	     }
    	   return null;
       }
}
```

> 注意：此类必须要被继承才能生效。如loginController类 想要捕获类中的异常。
> 如：
> `public class AccoutController extends AllException`

# solr  kafaka   redis    多ream支持