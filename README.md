# security
spring security权限管理
spring boot 与security的完美融合
#单点登录的流程
1. 用户首次访问资源,通过sessionId到服务器端换取用户信息
2. 将用户的信息保存到redis中
3. 输入用户名密码到后台校验,认证通过则将用户信息序列化保存到redis中,并且与sessionId绑定
4. 认证成功,跳转到资源页面
5. 重复步骤1
6. 认证成功,获取到用户信息,返回资源页面
