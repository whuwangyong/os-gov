# DROP SCHEMA IF EXISTS osguard;
# CREATE SCHEMA osguard;
USE osguard;

DROP TABLE app;
DROP TABLE app_host;
DROP TABLE artifact;
DROP TABLE artifact_app;
DROP TABLE artifact_license;
DROP TABLE artifact_tag;
DROP TABLE artifact_vulnerability;
DROP TABLE ask;
DROP TABLE host;
DROP TABLE license;
DROP TABLE qax_project_task;
DROP TABLE tag;
DROP TABLE vulnerability;


CREATE TABLE artifact
(
    id           int                        NOT NULL AUTO_INCREMENT,
    qax_id       char(36)                   NOT NULL,
    org          varchar(255),
    name         varchar(255)               NOT NULL,
    version      varchar(50)                NOT NULL,
    publish_date date,
    author       enum ('开源','自研','商业','信创') NOT NULL DEFAULT '开源',
    role         enum ('软件','组件','工具','NA') NOT NULL DEFAULT '组件',
    env          enum ('生产库','开发测试库','未入库') NOT NULL DEFAULT '生产库',
    description  varchar(2000),
    create_time  timestamp                  NOT NULL,
    update_time  timestamp                  NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_name (name),
    UNIQUE INDEX uidx_qax_id (qax_id),
    UNIQUE INDEX uidx_onv (org, name, version)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE tag
(
    id          int         NOT NULL AUTO_INCREMENT,
    name        varchar(50) NOT NULL,
    create_time timestamp   NOT NULL,
    update_time timestamp   NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX uidx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE artifact_tag
(
    id          int       NOT NULL AUTO_INCREMENT,
    artifact_id int       NOT NULL,
    tag_id      int       NOT NULL,
    create_time timestamp NOT NULL,
    update_time timestamp NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_art (artifact_id),
    INDEX idx_tag (tag_id),
    UNIQUE INDEX uidx_at (artifact_id, tag_id)
) ENGINE = Innodb
  DEFAULT CHARSET = utf8;

CREATE TABLE license
(
    id          int          NOT NULL AUTO_INCREMENT,
    name        varchar(255) NOT NULL,
    risk        tinyint      NOT NULL,
    create_time timestamp    NOT NULL,
    update_time timestamp    NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX uidx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE artifact_license
(
    id          int       NOT NULL AUTO_INCREMENT,
    artifact_id int       NOT NULL,
    license_id  int       NOT NULL,
    create_time timestamp NOT NULL,
    update_time timestamp NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_art_id (artifact_id),
    INDEX idx_lic_id (license_id),
    UNIQUE INDEX (artifact_id, license_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE vulnerability
(
    id             int          NOT NULL AUTO_INCREMENT,
    cve            varchar(20),
    cnnvd          varchar(20),
    qax_oss_id     varchar(20)  NOT NULL,
    name           varchar(255) NOT NULL,
    level          tinyint      NOT NULL DEFAULT 3,
    utilize_degree tinyint      NOT NULL DEFAULT 3,
    exposed_date   date         NOT NULL,
    description    varchar(2000),
    solution       varchar(2000),
    create_time    timestamp    NOT NULL,
    update_time    timestamp    NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_qax_id (qax_oss_id),
    INDEX idx_cve (cve),
    INDEX idx_cnnvd (cnnvd),
    INDEX idx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE artifact_vulnerability
(
    id               int       NOT NULL AUTO_INCREMENT,
    artifact_id      int       NOT NULL,
    vulnerability_id int       NOT NULL,
    create_time      timestamp NOT NULL,
    update_time      timestamp NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_art (artifact_id),
    INDEX idx_vul (vulnerability_id),
    UNIQUE uidx_av (artifact_id, vulnerability_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE app
(
    id          int         NOT NULL AUTO_INCREMENT,
    name        varchar(50) NOT NULL,
    env         enum ('生产','测试'),
    level       enum ('核心','一般','周边','办公'),
    create_time timestamp   NOT NULL,
    update_time timestamp   NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE artifact_app
(
    id          int       NOT NULL AUTO_INCREMENT,
    artifact_id int       NOT NULL,
    app_id      int       NOT NULL,
    re_dev      bool      NOT NULL,
    support     bool      NOT NULL,
    create_time timestamp NOT NULL,
    update_time timestamp NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_art_id (artifact_id),
    INDEX idx_app_id (app_id),
    UNIQUE INDEX uidx_aa (artifact_id, app_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE host
(
    id          int                     NOT NULL AUTO_INCREMENT,
    ip          varchar(15)             NOT NULL,
    hardware    enum ('物理机','虚拟机','容器') NOT NULL,
    network     enum ('生产','办公','开发')   NOT NULL,
    create_time timestamp               NOT NULL,
    update_time timestamp               NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_ip (ip)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE app_host
(
    id          int       NOT NULL AUTO_INCREMENT,
    app_id      int       NOT NULL,
    host_id     int       NOT NULL,
    create_time timestamp NOT NULL,
    update_time timestamp NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_app (app_id),
    INDEX idx_hst (host_id),
    UNIQUE INDEX uidx_ah (app_id, host_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE ask
(
    id           int                NOT NULL AUTO_INCREMENT,
    artifact_ids varchar(1000)      NOT NULL,
    project      varchar(255)       NOT NULL,
    username     varchar(20)        NOT NULL,
    commit_time  timestamp          NOT NULL,
    status       enum ('审核中','已完成') NOT NULL,
    create_time  timestamp          NOT NULL,
    update_time  timestamp          NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_project (project),
    INDEX idx_username (username)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS qax_project_task;
CREATE TABLE qax_project_task
(
    id                                      int       NOT NULL AUTO_INCREMENT,
    project_id                              char(36),
    project_name                            varchar(255),
    task_id                                 int,
    task_name                               varchar(255),
    task_begin_time                         char(19),
    task_end_time                           char(19),
    vulnerability_imported                  bool      NOT NULL DEFAULT FALSE,
    component_and_license_imported          bool      NOT NULL DEFAULT FALSE,
    component_vul_relationship_imported     bool      NOT NULL DEFAULT FALSE,
    component_license_relationship_imported bool      NOT NULL DEFAULT FALSE,
    component_project_relationship_imported bool      NOT NULL DEFAULT FALSE,
    create_time                             timestamp NOT NULL,
    update_time                             timestamp NOT NULL,
    PRIMARY KEY (id),
    INDEX uidx_proj_tsk (project_id, task_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;