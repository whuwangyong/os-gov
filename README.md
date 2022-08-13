# 开源治理工具设计



## pojo表

### 组件表（component）

| 字段        | 字段名称 | 类型 | 备注 |
| ----------- | -------- | ---- | ---- |
| id          |          | long | pk   |
| group_id    |          |      | idx  |
| artifact_id |          |      | idx  |
| version     |          |      |      |


### 软件表（software）

可独立运行的软件。如OpenLDAP、Kafka

- id
- group_id
- artifact_id
- version



### 漏洞表（vulnerability）

- id
- cve
- cnnvd
- cwe
- name
- char(1) level 风险等级
- exposed_date 曝光日期
- description 描述
- suggestion 修复建议



### 漏洞等级表（vulnerability_level）

- 0：严重
- 1：高危
- 2：中危
- 3：低危
- 4：无



## 关联表

### 组件漏洞表（component_vulnerability）

- id
- 组件id `component_id`
- 漏洞id `vulnerability_id`



## 领域

### 根据组件名称查询漏洞信息

1. 输入名称，模糊查询，返回匹配的组件列表
2. 用户选定一个组件，查看该组件的漏洞信息
3. 根据`component_id`查询`component_vulnerability`，返回漏洞id列表
4. 根据`vulnerability_id`查询漏洞表，获取漏洞详情列表

## 架构设计

- `class Entity`，仅有id字段。所有的表都继承该类
- `class BaseRepository<T extends Entity>`，实现通用的增删改查
- `class BaseController<T extends Entity>`，实现通用的增删改查