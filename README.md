# Cola-UI Follow Up

[![Endpoint coverage](http://progressed.io/bar/50?title=progress)](#progress)
[![Build Status](https://travis-ci.org/Carl-DS/cola-ui-follow-up.svg?branch=followcola)](https://travis-ci.org/Carl-DS/cola-ui-follow-up)

## 技术选型

> 前端

* UI框架：Cola-UI + jQuery 2.1.3。

> 后端

* 核心框架：Spring Framework 4.2.0.RELEASE
* 视图框架：Spring MVC 4.2.0.RELEASE
* 服务框架：Spring REST 4.2.0.RELEASE
* 持久层框架：Hibernate 4.3.6.Final
* 工具类：Apache Commons、Jackson 2.5.3

> 数据库

* MySQL


## 如何启动

> 修改 src/main/resources/configure.properties

```
jdbc.username = username
jdbc.password = password
jdbc.url = jdbc:mysql://localhost:3306/dbname?useUnicode=true&characterEncoding=UTF-8
jdbc.driver = com.mysql.jdbc.Driver
```

> 启动服务
* 配置服务方式: jetty/tomcat, 设置context path 为`/cola-ui-follow-up`
* maven方式: `mvn jetty:run` ; `mvn tomcat7:run`

> 初始化数据

* 此文件为初始化数据，表结构将服务启动时自动创建
* 初始化文件在/src/main/resources/init-data.sql

> 浏览器访问

http://localhost:8080/cola-ui-follow-up/