DROP SCHEMA test;
CREATE SCHEMA test;
USE test;

CREATE TABLE artifact
(
    id           int                                NOT NULL AUTO_INCREMENT,
    org          varchar(255)                       NOT NULL DEFAULT 'NA',
    name         varchar(255)                       NOT NULL,
    version      varchar(50)                        NOT NULL,
    publish_date date                               NOT NULL,
    author       enum ('开源','自研','商业','信创') NOT NULL,
    role         enum ('软件','组件','工具','NA')   NOT NULL DEFAULT 'NA',
    description  varchar(1000),
    PRIMARY KEY (id),
    INDEX idx_name (name),
    UNIQUE INDEX uidx_onv (org, name, version)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE tag
(
    id   int         NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX uidx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE artifact_tag
(
    id          int NOT NULL AUTO_INCREMENT,
    artifact_id int NOT NULL,
    tag_id      int NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_art (artifact_id),
    INDEX idx_tag (tag_id),
    UNIQUE INDEX uidx_at (artifact_id, tag_id),
    CONSTRAINT FOREIGN KEY (artifact_id) REFERENCES artifact (id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
) ENGINE = Innodb
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE license
(
    id   int                    NOT NULL AUTO_INCREMENT,
    name varchar(255)           NOT NULL,
    risk enum ('0','1','2','3') NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX uidx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE artifact_license
(
    id          int NOT NULL AUTO_INCREMENT,
    artifact_id int NOT NULL,
    license_id  int NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_art_id (artifact_id),
    INDEX idx_lic_id (license_id),
    UNIQUE INDEX (artifact_id, license_id),
    CONSTRAINT FOREIGN KEY (artifact_id) REFERENCES artifact (id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (license_id) REFERENCES license (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE vulnerability
(
    id           int                    NOT NULL AUTO_INCREMENT,
    cve          varchar(50)            NULL,
    cnnvd        varchar(50)            NULL,
    cwe          varchar(50)            NULL,
    name         varchar(255)           NOT NULL,
    level        enum ('0','1','2','3') NOT NULL DEFAULT '3',
    difficulty   enum ('0','1','2','3') NOT NULL DEFAULT '3',
    exposed_date date                   NOT NULL,
    description  varchar(2000),
    suggestion   varchar(2000),
    PRIMARY KEY (id),
    UNIQUE INDEX uidx_cve (cve),
    UNIQUE INDEX uidx_cnnvd (cnnvd),
    UNIQUE INDEX uidx_cwe (cwe),
    INDEX idx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE artifact_vulnerability
(
    id               int NOT NULL AUTO_INCREMENT,
    artifact_id      int NOT NULL,
    vulnerability_id int NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_art (artifact_id),
    INDEX idx_vul (vulnerability_id),
    UNIQUE uidx_av (artifact_id, vulnerability_id),
    CONSTRAINT FOREIGN KEY (artifact_id) REFERENCES artifact (id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (vulnerability_id) REFERENCES vulnerability (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE app
(
    id   int                  NOT NULL AUTO_INCREMENT,
    name varchar(50)          NOT NULL,
    env  enum ('生产','测试') NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE artifact_app
(
    id          int  NOT NULL AUTO_INCREMENT,
    artifact_id int  NOT NULL,
    app_id      int  NOT NULL,
    re_dev      bool NOT NULL,
    support     bool NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_art_id (artifact_id),
    INDEX idx_app_id (app_id),
    UNIQUE INDEX uidx_aa (artifact_id, app_id),
    CONSTRAINT FOREIGN KEY (artifact_id) REFERENCES artifact (id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (app_id) REFERENCES app (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE host
(
    id       int                             NOT NULL AUTO_INCREMENT,
    ip       int UNSIGNED                    NOT NULL,
    hardware enum ('物理机','虚拟机','容器') NOT NULL,
    network  enum ('生产','办公','开发')     NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE app_host
(
    id      int NOT NULL AUTO_INCREMENT,
    app_id  int NOT NULL,
    host_id int NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_app (app_id),
    INDEX idx_hst (host_id),
    UNIQUE INDEX uidx_ah (app_id, host_id),
    CONSTRAINT FOREIGN KEY (app_id) REFERENCES app (id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (host_id) REFERENCES host (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;