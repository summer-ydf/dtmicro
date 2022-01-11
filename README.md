
                                 
### DT CMS 平台

 **Spring Boot前后端分离架构，大道至简、代码玄学、开箱即用**  <br>

![SpringBoot版本](https://img.shields.io/badge/spring--boot-2.3.2-brightgreen.svg "SpringBoot版本") 
![SpringCloud版本](https://img.shields.io/badge/spring-cloud--Hoxton.SR9-brightgreen.svg "SpringCloud版本]")
![SpringCloudAlibaba版本](https://img.shields.io/badge/spring-cloud--alibaba--2.2.6-brightgreen.svg "SpringCloudAlibaba版本")

![输入图片说明](https://img.shields.io/badge/Vue-3.5.0-brightgreen "在这里输入图片标题") ![输入图片说明](https://img.shields.io/badge/redis-6.0.6-brightgreen "在这里输入图片标题") ![输入图片说明](https://img.shields.io/badge/kaptcha-2.3.2-brightgreen "在这里输入图片标题") ![输入图片说明](https://img.shields.io/badge/MySQL-8.0.81-brightgreen "在这里输入图片标题") ![输入图片说明](https://img.shields.io/badge/mybatisplus-3.4.6-brightgreen "在这里输入图片标题") ![输入图片说明](https://img.shields.io/badge/easyexcel-2.2.0beta2-brightgreen "在这里输入图片标题")


# 平台简介

 **DT CMS**  是一款SpringBoot2.x与Vue整合前后端分离内容管理系统，项目源码完全由个人精心编写，致力于做更简洁高性能RBAC内容服务系统，追求 **快速的用户体验** 、 **二次编码** ，以及 **核心技术模块的整合** 使用。后端新技术框架的加持、前端UI的设计与美化，会持续升级，持续完善，欢迎亲友们收藏、点赞和转发。

Vue前端链接：[Client端入口](https://gitee.com/summerydf/dtcmsclient)

线上体验地址：[加入CMS基础版本体验](http://47.108.191.196)，账号密码：root/root123 ，注：请大家不要乱删除数据，影响体验效果。

## 平台优势

一款极易扩展的开源系统，可用于学习、商用、二次开发等等，系统配套了开发使用手册，部署手册，以及开发说明手册，并且整合了最新潮流的技术框架，源代码注释完整、结构简洁、结构清晰、便维护、便迭代。

## 核心技术

SpringBoot、MybatisPlus、SpringSecurity、JWT令牌使用RSA秘钥非对称加密，极大限度保证系统安全性，数据库采用MySQL、Redis，文件服务器：Minio,前端UI：Vue、ElementUI

## 开发环境准备

### 1. Minio文件服务器安装

- Windows下载[ https://dl.minio.io/server/minio/release/windows-amd64/minio.exe]( https://dl.minio.io/server/minio/release/windows-amd64/minio.exe)

安装启动，进入minio.exe安装目录下，启动cmd窗口，输入如下命令：


```
.\minio.exe server D:\data
```

D:\data 为当前服务器存储资源路径，可自行指定。

- Linux安装教程 [全网最细Docker安装Minio，填满最新版大坑（强烈推荐收藏）](https://blog.csdn.net/qq_41107231/article/details/119042855)

### 2. MySQL数据库安装

- Linux安装教程 [全网最细Linux之Centos8安装MySQL8.0以上版本，您值得收藏！](https://blog.csdn.net/qq_41107231/article/details/119064936)

### 3. Redis数据库安装

- Linux安装教程 [最全Linux安装Redis最新版](https://blog.csdn.net/qq_41107231/article/details/111656160)

