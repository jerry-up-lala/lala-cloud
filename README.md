<!-- markdownlint-disable -->
<p align="center">
  <img width="100" src="./assets/logo.svg" style="text-align: center;">
</p>
<h1 align="center">jerry-up · lala · lala-cloud</h1>
<h4 align="center">springcloud架构的服务端，基于lala-framework提供各种常见的后端功能样例</h4>
<p align="center">
    <img alt="cloud" src="./assets/cloud-1.0.0-green.svg">
    <a href="./LICENSE" target="_blank">
        <img alt="license" src="./assets/license-MIT-green.svg">
    </a>
</p>
<p align="center">
    <a href="https://github.com/jerry-up-lala/lala-framework" target="_blank">
        <img alt="framework" src="./assets/framework-1.0.0-blue.svg">
    </a>
</p>

## 1. 项目简介

汇聚后端开发中常用的技术，功能，场景，提供使用样例。

| 项目                                                   | 仓库地址                                            | 说明                     |
| ------------------------------------------------------ | ------------------------ | ------------------------ |
| framework | [![framework#gitee](./assets/gitee-snow.svg)](https://gitee.com/jerry_up_lala/lala-framework) [![framework#github](./assets/github-snow.svg)](https://github.com/jerry-up-lala/lala-framework)| 基础框架     |
| lala-cloud-web | [![lala-cloud-web#gitee](./assets/gitee-snow.svg)](https://gitee.com/jerry_up_lala/lala-cloud-web) [![lala-cloud-web#github](./assets/github-snow.svg)](https://github.com/jerry-up-lala/lala-cloud-web) | 配套前端 |

## 2. 目录说明

```sh
lala-cloud
    └──apidoc #接口文档
    └──assets #附件
	|	└──cloud #服务端代码
    |       └──api #公共模块
    │       |   └──服务名-api #
    │       |        └──src
    │       |           └──main
    │       |             └──java
    │       |                └──com.jerry.up.lala.cloud.api.服务名
    |       ├─gateway #网关
    │       | └──src
    │       |    └──main
    │       |      └──java
    │       |      |  └──com.jerry.up.lala.cloud.gateway
    │       |      |     ├─component #组件
    │       |      |     ├─config #配置
    │       |      |     ├─ctrl #控制层
    │       |      |     ├─entity #实体
    │       |      |     ├─error #错误码
    │       |      |     ├─handler #处理
    │       |      |     ├─mapper #mapper
    │       |      |     ├─properties #配置
    │       |      |     ├─vo #视图对象
    │       |      |     └──GatewayApp #服务启动类  
    │       |      └──resources
    │       |            ├─mapper #mybatis mapper xml文件
    │       |            ├─banner.txt #欢迎页
    │       |            └──bootstrap.yml #配置文件
    |       └──server #微服务
    |            └──服务名-server
    │               └──src
    │                  └──main
    │                    └──java
    │                    |  └──com.jerry.up.lala.cloud.server.服务名
    │                    |     ├─api #对内服务
    │                    |     ├─bo #业务对象
    │                    |     ├─constant #常量
    │                    |     ├─ctrl #控制层
    │                    |     ├─dto #数据对象
    │                    |     ├─entity #实体
    │                    |     ├─error #错误码
    │                    |     ├─listener #监听器
    │                    |     ├─mapper #mapper
    │                    |     ├─properties #配置
    │                    |     ├─service #service层
    │                    |     ├─vo #视图对象
    │                    |     └──GatewayApp #服务启动类  
    │                    └──resources
    │                          ├─mapper #mybatis mapper xml文件
    │                          ├─banner.txt #欢迎页
    │                          └──bootstrap.yml #配置文件
    |   
    ├──deploy #部署脚本
    │    ├─jar #jar方式部署
    │    ├─compile.sh #编译脚本
    │    └──deploy_api.sh #部署脚本
    ├──nacos #nacos配置文件
    └──sql #sql脚本
```

## 3. 服务列表 
> 未做服务领域拆分，实际项目中根据业务进行拆分

| 服务名称    | 默认端口 | 说明   | 网关路由 | 异常码   |
| ----------- | --------- | -------- | -------- | -------- |
| gateway     | 8080 | 网关    | -        | `11****` |
| system-server | 8081 | 系统服务 | /system    | `12****` |
| core-server | 8082 | 核心服务 | /core    | `13****` |

## 4. 功能列表

| 功能       | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| 工作台     | 请求日志统计，个人信息，个人通知，快捷入口                   |
| crud样例   | 提供MySQL数据库增删改查样例，包含`原生模式`，`组件模式`，`脚手架模式`等三种风格样例 |
| http样例   | 提供发送Http请求样例                                         |
| 线程池样例 | 提供线程池参数配置样例                                       |
| Redis      | 提供Redis数据库增删改查以及编辑有效期样例，支持`String`，`List`，`Set`，`ZSet`，`Hash`等五种数据类型 |
| 消息队列   | 提供RocketMQ消息队列生产消费样例                             |
| 系统管理   | 提供集团管理，菜单管理，用户管理，角色管理，通知管理等后台系统配置基本功能 |
| 开发工具   | 提供服务管理，路由管理，请求日志，字典管理，代码生成等研发常用工具               |
| 个人中心   | 提供个人信息维护，个人通知管理功能                           |

## 5. 初始化环境

### 5.1. 安装中间件

> 可通过[lala-cloud-tool](https://gitee.com/jerry_up_lala/lala-cloud-tool)基于docker或者参照[中间件安装](https://jerry-up-blog.pages.dev/tool/middleware/)初始化环境，亦可自行安装如下版本中间件。

| 名称                                        | 版本                                                         |
| ------------------------------------------- | ------------------------------------------------------------ |
| [MySQL](https://www.mysql.com/)             | [5.7.36](https://dev.mysql.com/doc/relnotes/mysql/5.7/en/news-5-7-36.html) |
| [Redis](https://redis.io)                   | [6.2.13](https://github.com/redis/redis/releases/tag/6.2.13) |
| [Nacos](https://nacos.io/zh-cn/)            | [2.2.0](https://github.com/alibaba/nacos/releases/tag/2.2.0) |
| [Seata](https://seata.io/zh-cn/)            | [1.6.1](https://github.com/seata/seata/releases/tag/v1.6.1) | 7091,8091      |                   |
| [Sentinel](https://sentinelguard.io/zh-cn/) | [1.8.6](https://sentinelguard.io/zh-cn/blog/sentinel-1-8-6-release.html) | 8080           | sentinel/sentinel |
| [RocketMQ](https://rocketmq.apache.org/zh/) | [5.0.0](https://github.com/apache/rocketmq/releases/tag/rocketmq-all-5.0.0) |

### 5.2. 初始化数据库

> 执行 `./sql/v1.0.0.sql` ，会自动创建数据库并初始化表结构以及数据。

```sh
# 替换如下命令 对应的 ip，端口，用户名，密码。
mysql -h127.0.0.1 -P3306 -uroot -p123456 < ./sql/v1.0.0.sql
```

![image-20240802170514348](./assets/image-20240802170514348.png)

### 5.3. 初始化Nacos配置

> `./nacos`文件夹下，以`Group`为文件夹，`Data ID`为文件名。

1. 在`./nacos/LALA_ClOUD_GROUP`文件夹下新增`env.***.yaml`配置文件，根据实际的环境进行参数配置。

   > 样例配置文件参考`./nacos/LALA_ClOUD_GROUP/env.192.168.35.61.yaml`。

   | 配置项 | 说明 |
   | ------ | ---- |
   |env.mail.host|邮件发信主机|
   |env.mail.port|邮件发信端口|
   |env.mail.from|邮件发信人邮箱|
   |env.mail.user|邮件发信人账号|
   |env.mail.pass|邮件发信人密码|
   |env.mail.subjectPrefix|邮件发信主题|
   |env.error.catchPrint|捕获异常打印|
   |env.error.servicePrint|业务异常打印|
   |env.error.runTimePrint|运行时异常打印|
   |env.error.mailOpen|异常是否邮件提醒|
   |env.error.mailLevel|异常邮件提醒级别|
   |env.error.mailReceivers|异常邮件收件人|
   |env.log.mq|请求日志是否发送至消息队列|
   |env.log.print|请求日志是否打印|
   |env.mysql.host|MySQL地址|
   |env.mysql.username|MySQL用户名|
   |env.mysql.password|MySQL密码|
   |env.redis.host|Redis地址|
   |env.redis.password|Redis密码|
   |env.sentinel.host|Sentinel地址|
   |env.rocketmq.nameServer|RocketMQ地址|
   |env.druid.username|Druid用户名|
   |env.druid.password|Druid密码|
   |env.token.activityTimeout|最低活跃频率，单位/秒|
   |env.token.jwtSecretKey|jwt秘钥|
   |env.token.redisHost|Sa-Token独立使用的Redis地址|
   |env.token.redisPort|Sa-Token独立使用的Redis端口|
   |env.token.redisPassword|Sa-Token独立使用的Redis密码|
   |env.token.redisDatabase|Sa-Token独立使用的Redis数据库索引|
   |env.access|接口是否校验访问权限|
   |env.upload.max|上传Excel最大条数|
   |env.upload.partition|上传Excel批量处理分区大小|

2. 将`./nacos/LALA_CLOUD_GROUP`文件夹进行压缩，导入至Nacos。

   ![image-20240802170748802](./assets/image-20240802170748802.png)

## 6. 本地开发

### 6.1. 前置准备

#### 6.1.1. JDK

项目需要JDK8

![JDK8](./assets/image-20240701093854544.png)

#### 6.1.2. Maven

> 使用Maven进行依赖管理，默认使用阿里云效提供的[免费制品仓库](https://jerry-up-blog.pages.dev/tool/env/repository.html#_2-1-maven%E4%BB%93%E5%BA%93)。

- 方式一，替换`pom.xml`中地址为自己使用的Maven地址，并推送framework基础依赖包至Maven仓库。

- 方式二，修改`settings.xml`为如下配置（只有拉取代码权限，无推送权限）。

```xml
<?xml version="1.0" encoding="UTF-8"?>

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

    <localRepository>/Applications/code-tool/maven/repository</localRepository>

    <servers>
        <server>
            <id>rdc-releases</id>
            <username>65edb330dfac75d8a9644531</username>
            <password>xh10uWIOTOHV</password>
        </server>

        <server>
            <id>rdc-snapshots</id>
            <username>65edb330dfac75d8a9644531</username>
            <password>xh10uWIOTOHV</password>
        </server>
    </servers>

    <mirrors>
        <mirror>
          <id>mirror</id>
          <mirrorOf>central,jcenter,!rdc-releases,!rdc-snapshots</mirrorOf>
          <name>mirror</name>
          <url>https://maven.aliyun.com/nexus/content/groups/public</url>
        </mirror>
    </mirrors>

    <profiles>
        <profile>
            <id>rdc</id>
            <properties>
                <altReleaseDeploymentRepository>
                    rdc-releases::default::https://packages.aliyun.com/maven/repository/2365883-release-DDVVbg/
                </altReleaseDeploymentRepository>
                <altSnapshotDeploymentRepository>
                    rdc-snapshots::default::https://packages.aliyun.com/maven/repository/2365883-snapshot-3GEFXp/
                </altSnapshotDeploymentRepository>
            </properties>
        </profile>
    </profiles>

    <activeProfiles>
        <activeProfile>rdc</activeProfile>
    </activeProfiles>


</settings>
```

### 6.2. 项目导入

```sh
git clone https://github.com/jerry-up-lala/lala-cloud.git
```

![image-20240730172313089](./assets/image-20240730172313089.png)

### 6.3. 启动

#### 6.3.1. 设置环境变量

 ```sh
 # 环境变量样例
 DATA_PATH=/Users/jerry/root/root/data/lala-cloud;NACOS_CONFIG_HOST=192.168.35.61:8848;NACOS_CONFIG_GROUP=LALA_CLOUD_GROUP;NACOS_CONFIG_ENV=env.192.168.35.61.yaml
 ```

| 变量名             | 说明         | 样例值                           | 默认值          |
| ------------------ | ------------ | -------------------------------- | --------------- |
| DATA_PATH          | 数据存放路径 | /Users/jerry/root/root/data/lala-cloud | /data           |
| NACOS_CONFIG_HOST  | 配置中心地址 | 192.168.35.61:8848               | 127.0.0.1:8848  |
| NACOS_CONFIG_GROUP | 配置中心分组 | LALA_CLOUD_GROUP | LALA_CLOUD_GROUP |
| NACOS_CONFIG_ENV   | 配置环境     | env.192.168.35.61.yaml           | env.local.yaml  |

#### 6.3.2. idea启动应用

![image-20240802165207031](./assets/image-20240802165207031.png)

#### 6.3.3. 验证接口

> 系统管理员默认用户名为`admin`, 密码为`lala`。

```sh
curl --location --request POST 'http://localhost:8080/system/open/sys/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "loginName": "admin",
    "passWord": "lala"
}'
```

![image-20240802170858899](./assets/image-20240802170858899.png)

#### 6.3.4. 接口文档

[【1】在线文档](https://jerry-up-lala-cloud.apifox.cn)

[【2】离线文档](./apidoc/lala-cloud.html)

## 7. 部署

### 7.1. 上传api模块

> api模块有改动时，运行`deploy/deploy_api.sh`，上传最新的api模块至Maven仓库。

![image-20240802172335237](./assets/image-20240802172335237.png)

### 7.2. 部署服务

> 进入 `deploy/jar`目录，进行如下操作。会在配置的部署文件夹路径生成jar包，启动脚本，停止脚本。

1. 修改`jar.properties`配置文件。

   | 变量名              | 说明                              |                                            默认值 |
   | ------------------- | --------------------------------- | ------------------------------------------------: |
   | DEPLOY_DIR          | 部署文件夹，jar包以及运行脚本路径 | /Users/jerry/Desktop/jerry_up_lala/lala-cloud/jar |
   | NACOS_CONFIG_HOST   | 配置中心地址                      |                                    127.0.0.1:8848 |
   | NACOS_CONFIG_GROUP  | 配置中心分组                      |                                  LALA_CLOUD_GROUP |
   | NACOS_CONFIG_ENV    | 配置环境                          |                                    env.local.yaml |
   | GATEWAY_POM_VERSION | 网关pom文件版本                   |                                             1.0.0 |
   | SYSTEM_POM_VERSION    | 系统服务pom文件版本               |                                             1.0.0 |
   | CORE_POM_VERSION    | 核心服务pom文件版本               |                                             1.0.0 |

2. 根据不同的操作系统运行`jar_mac.sh`或者`jar_linux.sh`，会编译服务并将编译后的文件以及部署脚本放至部署文件夹。

   ```shell
   sh jar_mac.sh $服务名称
   # 网关
   sh jar_mac.sh gateway
   # 系统服务
   sh jar_mac.sh system
   # 核心服务
   sh jar_mac.sh core
   ```

   ![image-20240802173818032](./assets/image-20240802173818032.png)

3. 切换至部署文件夹下对应服务，运行`sh start.sh`。

   ![image-20240802174937845](./assets/image-20240802174937845.png)
