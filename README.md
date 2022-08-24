# 开源治理工具设计

## 数据模型

### 术语

- pk 主键
- idx 索引
- uidx 唯一索引
- NA 不适用

### 制品表(artifact)

> - 包含组件、软件、工具，通过type字段区分。
> - 组件：zk客户端
> - 软件：zk服务端，可独立运行
> - 工具：mattermost、sonarqube等开发测试运维工具
> - 联合索引：uidx: (org, name, version)，简称onv（之所以不叫gav是因为这里面不仅仅是maven仓库的组件）。
> - 关于md5 char(32)：可能存在gnv相同，但实际是不同版本的组件的情况。md5用于解决该问题。但是，这种不合规的组件，就不应该引入，它打破了gnv的唯一索引约束。
>
| 字段         | 名称         | 类型                              | 约束                  | 备注                           |
| ------------ | ------------ | --------------------------------- | --------------------- | ------------------------------ |
| id           | 自增id       | int                               | pk                    |                                |
| org          | 制品所在组织 | varchar(255)                      | not null default 'NA' | 前端组件、go module不适用？    |
| name         | 制品名称     | varchar(255)                      | idx，not null         |                                |
| version      |              | varchar(50)                       | not null              |                                |
| publish_date | 发布日期     | date                              | not null              |                                |
| author       | 来源         | enum('开源','自研','商业','信创') | not null              |                                |
| role         | 角色         | enum('软件','组件','工具','NA')   | not null default 'NA' | 原开源分类。分类这个词太笼统了 |
| description  | 简介         | varchar(1000)                     |                       |                                |

### 制品标签表(artifact_tag)

> 由于分类系统很难实现完备性，因此采用标签系统对制品进行标记以便检索。
>
> 比如ZooKeeper，如要分类，它需要同时存在于高可用、服务发现、命名空间等多个分类下。而使用标签系统，只需给ZooKeeper打3个标签，体现在该表里面，就是3行记录。如果对某个制品有新增标签，新增一行记录即可。
>
> 很明显，该表采用的是竖表设计。原因：
>
> 1. 标签都是字符串，类型一样；
> 2. 标签需要查询。如果采用横表，一个制品的所有标签放在一个字段，那么该字段要么设计为json数组，要么自己做字符串切分。两种方案的crud都很低效。
>
> 该表的功能：
>
> 1. 根据制品查标签
> 2. 根据标签查制品
> 3. 增删改查制品的标签
>
> uidx：(artifact_id, tag_id)

| 字段        | 字段名称 | 类型 | 约束      | 备注 |
| ----------- | -------- | ---- | --------- | ---- |
| id          | 自增id   | int  | pk        |      |
| artifact_id | 制品id   | int  | idx，外键 |      |
| tag_id      | 标签id   | int  | idx，外键 |      |

> 为什么需要定义外键约束？
>
> 当删除一个标签时，标签表里对应的id没有了。若无外键级联删除，此时制品标签表里还存在着指向该标签id的记录。后续查询此表时，根据标签id查标签表，会查不到该记录，报错。
>
> 除了使用外键约束，另一种做法是在代码上，当删除标签表里的记录时，同时删除制品标签表里的记录（使用事务）。
>
> 本系统暂无高性能要求，因此我倾向于使用外键，让数据库去完成这些事情。

### 标签表(tag)

> 为什么标签需要单独建表，而不是直接往制品标签表里添加记录？
>
> 为了避免同样含义的标签出现多个，如日志、log、Log，都是一个东西。通过标签表，约束了为组件随意添加标签的行为，组件的标签必须来自标签表。而标签表里面标签的增删改都是经过评审的。

| 字段        | 字段名称 | 类型 | 约束      | 备注 |
| ----------- | -------- | ---- | --------- | ---- |
| id          | 自增id   | int  | pk        |      |
| name | 标签名 | varchar(50) | uidx |      |

### 协议表(liscense)

| 字段        | 字段名称 | 类型 | 约束      | 备注 |
| ----------- | -------- | ---- | --------- | ---- |
| id          | 自增id   | int  | pk        |      |
| name | 协议名 | varchar(100) | uidx |      |
| risk | 风险等级 | enum(0,1,2,3) | 高传染，中，低，无 | |

### 制品协议表(artifact_license)

| 字段        | 字段名称 | 类型 | 约束      | 备注 |
| ----------- | -------- | ---- | --------- | ---- |
| id          | 自增id   | int  | pk        |      |
| artifact_id | 制品id   | int  | idx，外键 |      |
| license_id  | 协议id   | int  | idx，外键 |      |

### 漏洞表(vulnerability)

> 通过对`风险等级`和`利用难度`数值的设计，可以引出一个新的概念：危险系数。
>
> 危险系数 = 风险等级 * 利用难度，取值范围为`[0,3] * [0, 3]`，值越小，越危险。最小值为0，与0 day的含义一致。

| 字段         | 字段名称 | 类型          | 约束 | 备注                   |
| ------------ | -------- | ------------- | ---- | ---------------------- |
| id           | 自增id   | int           | pk   |                        |
| cve          | 漏洞编号 | varchar(50)   | uidx |                        |
| cnnvd        | 漏洞编号 | varchar(50)   | uidx |                        |
| cwe          | 漏洞编号 | varchar(50)   | uidx |                        |
| name         | 漏洞名称 | varchar(255)  | idx  |                        |
| level        | 风险等级 | enum(0,1,2,3) |      | 严重，高危，中危，低危 |
| difficulty   | 利用难度 | enum(0,1,2,3) |      | 容易，一般，困难，未知 |
| exposed_date | 曝光日期 | date          |      |                        |
| description  | 简介     | varchar(2000) |      |                        |
| suggestion   | 修复建议 | varchar(2000) |      |                        |

### 制品漏洞表(artifact_vulnerability)

> 这也是个竖表，因为一个制品可能有多个漏洞。
>
> uidx: (artifact_id, vulnerability_id)

| 字段             | 字段名称 | 类型 | 约束      | 备注 |
| ---------------- | -------- | ---- | --------- | ---- |
| id               | 自增id   | int  | pk        |      |
| artifact_id      | 制品id   | int  | idx，外键 |      |
| vulnerability_id | 漏洞id   | int  | idx，外键 |      |

### 应用表(app)

| 字段 | 字段名称 | 类型                | 约束          | 备注 |
| ---- | -------- | ------------------- | ------------- | ---- |
| id   | 自增id   | int                 | pk            |      |
| name | 名称     | varchar(50)         | idx, not null |      |
| env  | 环境     | enum('生产','测试') | not null      |      |


### 制品应用表(artifact_app)

> 描述制品与应用之间的关系；应用如何使用制品。
>
> 功能：
>
> - 根据制品id，找出所有使用了该制品的应用
> - 根据应用id，找出该应用使用的全部制品
>
> uidx:(artifact_id, app_id)

| 字段        | 字段名称 | 类型    | 约束      | 备注 |
| ----------- | -------- | ------- | --------- | ---- |
| id          | 自增id   | int     | pk        |      |
| artifact_id | 制品id   | int     | idx，外键 |      |
| app_id      | 应用id   | int     | idx，外键 |      |
| re_dev      | 二次开发 | boolean |           |      |
| support     | 厂商支持 | boolean |           |      |

### 主机表(host)

> ip字段，《高性能MySQL》推荐使用int unsigned类型。但是为了与java对象保持一致，还是采用字符串吧。免得写rowMapper()。

| 字段     | 字段名称 | 类型                           | 约束 | 备注 |
| -------- | -------- | ------------------------------ | ---- | ---- |
| id       | 自增id   | int                            | pk   |      |
| ip       |          | varchar(15)                    |      |      |
| hardware | 硬件     | enum('物理机','虚拟机','容器') |      |      |
| network  | 网段     | enum('生产','办公','开发')     |      |      |

### 应用部署表(app_host)

> 功能：
>
> - 根据应用id，找到其部署在哪些主机上
> - 根据主机id，查询运行在主机上的应用
>
> uidx: (app_id, host_id)

| 字段    | 字段名称 | 类型    | 约束      | 备注 |
| ------- | -------- | ------- | --------- | ---- |
| id      | 自增id   | int     | pk        |      |
| app_id  | 应用id   | int     | idx，外键 |      |
| host_id | 主机id   | int     | idx，外键 |      |


## 领域

### 根据组件名称查询漏洞信息

1. 输入名称，模糊查询，返回匹配的组件列表
2. 用户选定一个组件，查看该组件的漏洞信息
3. 根据`component_id`查询`component_vulnerability`，返回漏洞id列表
4. 根据`vulnerability_id`查询漏洞表，获取漏洞详情列表

```sql
select vulnerability_id from component_vulnerability where component_id=xxx;
select * from vulnerability where vulnerability_id=xxx;
```

## 架构设计

- `class Entity`，仅有id字段。所有的表都继承该类
- `class BaseRepository<T extends Entity>`，实现通用的增删改查
- `class BaseController<T extends Entity>`，实现通用的增删改查

### 贫血模型

controller/anemic目录下为贫血模型，提供了单表增删改查的REST
API。因为几乎没有业务逻辑，所以controller直接使用repository，不再多此一举地通过service层转发。

### 领域模型

controller/domain目录下为领域模型。这里的操作涉及到多表，有丰富的业务逻辑，需要将领域模型封装到对应的service层。

## 页面设计

侧边栏

- 基础信息维护（admin可修改，test可查看）
  - 制品
  - 漏洞
  - 协议
  - 标签
  - 应用
  - 主机
- 关联关系维护
  - 制品-漏洞
  - 制品-协议
  - 制品-标签
  - 制品-应用
  - 应用-主机