#### 项目介绍

- Heart基于SpringBoot 2，致力于做更简洁的后台管理系统，一款快速开发的脚手架。springmvc + MyBatis-Plus。

- 项目特点：
  - **持久层：mybatis持久化，使用MyBatis-Plus优化，减少sql开发量；Transtraction注解事务。**
  - **使用SpringBoot自动装配，MyBatis-Plus配置文件提为默认配置放在了common包的default-config.properties中，
  子项目的xml只需固定放在com/ksh/heart/modular/\*/mapping/\*.xml，实体类固定放在com.ksh.heart.*.entity中。
  即可使用MyBatis-Plus。若不使用默认配置，可在子工程配置文件直接写入自己的配置即可覆盖。**
  - **提出公共的模块，service、dao、entity接口和后台管理系统可共用（将相应的模块放在heart-web中），当然也可不共用，只需将相应的模块放在子工程中**
  - **接口模块已添加拦截和post请求签名，可直接使用**
  - **后端使用guns的map+wrapper返回方式返回字段的字典值**
  - **前后端分离**
  - **集成了异步插入日志**
  - **实现了用户角色菜单权限动态配置，精确到按钮级别**
  - **日志分类等**
  - **工具类：excel导入导出，汉字转拼音，字符串工具类，数字工具类，发送邮件，redis工具类，MD5加密，HTTP工具类，防注入工具类,i18n 国际化多语言工具等等。**


#### 项目结构
````
heart
├─heart-common     公共模块
│ 
│ 
├─heart-api       API服务 （post请求签名、token)
│             
│ 
├─heart-web      公用实体类、dao、service
│   
│ 
├─doc  数据库sql文件
│ 
│ 
````
<br>

#### 技术选型
- 核心框架：Spring Boot 2.1.3
- 安全框架：Apache Shiro 1.4
- 视图框架：Spring MVC 5.0
- 持久层框架：MyBatis-Plus 3.0-RC1
- 数据库连接池：hikari
- 前后端分离
- 缓存：Redis

#### 软件需求
- JDK1.8
- MySQL8.0+
- Maven3.0+
- Redis
- lombok插件
