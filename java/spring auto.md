# Security OAuth2 实现单点登录

### 配置文件

#### 1，

```xml
server:
  port: 8082
  servlet:
    context-path: /memberSystem
security:
  basic:
    enabled: false
  oauth2:
    client:
      client-id: UserManagement   #
      client-secret: user123
      access-token-uri: http://localhost:8080/oauth/token
      user-authorization-uri: http://localhost:8080/oauth/authorize
    resource:
      jwt:
        key-uri: http://localhost:8080/oauth/token_key
```

我们禁用了默认的 Basic Authentication

accessTokenUri 是获取访问令牌的 URI
userAuthorizationUri 是用户将被重定向到的授权 URI
用户端点 userInfoUri URI 用于获取当前用户的详细信息

