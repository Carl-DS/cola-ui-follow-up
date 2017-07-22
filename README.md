# Cola-UI Follow Up

[![Endpoint coverage](http://progressed.io/bar/50?title=progress)](#progress)
[![Build Status](https://travis-ci.org/Carl-DS/cola-ui-follow-up.svg?branch=followcola)](https://travis-ci.org/Carl-DS/cola-ui-follow-up)

## 快速开始

> 修改 src/main/resources/configure.properties

```properties
jdbc.url = jdbc:mysql://localhost:3306/colauifollowup?useUnicode=true&characterEncoding=UTF-8
jdbc.username = username
jdbc.password = password
```
用户名密码修改为你本机的mysql配置,并新建数据库`colauifollowup`

> 启动服务

* 配置服务方式: jetty/tomcat, 设置context path 为`/cola-ui-follow-up`
* maven方式: `mvn jetty:run` ; `mvn tomcat7:run`

> 初始化数据: /src/main/resources/init-data.sql

* 此文件为初始化数据，表结构将服务启动时自动创建
* 创建后表中没有数据, 需导入init-data.sql 文件

> 浏览器访问

http://localhost:8080/cola-ui-follow-up

> 登录

用户名: admin
密码: admin

> RESTful API 接口文档

http://localhost:8080/cola-ui-follow-up/service/swagger-ui.html

![](https://github.com/Carl-DS/cola-ui-follow-up/blob/followcola/src/main/webapp/resources/images/swagger.png)


