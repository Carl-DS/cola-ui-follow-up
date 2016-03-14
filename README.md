# Cola-UI Spring Rest Example



## 技术选型

1、前端

* UI框架：Cola-UI + jQuery 2.1.3。

2、后端

* 核心框架：Spring Framework 4.2.0.RELEASE
* 视图框架：Spring MVC 4.2.0.RELEASE
* 服务框架：Spring REST 4.2.0.RELEASE
* 持久层框架：Hibernate 4.3.6.Final
* 工具类：Apache Commons、Jackson 2.5.3

3、数据库

* MySQL


## 如何启动

1、修改 src/main/resources/configure.properties

```
jdbc.username = username
jdbc.password = password
jdbc.url = jdbc:mysql://localhost:3306/dbname?useUnicode=true&characterEncoding=UTF-8
jdbc.driver = com.mysql.jdbc.Driver
```

2、启动服务

3、初始化数据

* `此文件为初始化数据，表结构将服务启动时自动创建`
* 初始化文件在/src/main/resources/init-data.sql

4、浏览器访问

http://localhost:port/context
	