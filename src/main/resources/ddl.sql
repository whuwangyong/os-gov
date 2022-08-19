CREATE TABLE artifact
(
    id           int          NOT NULL AUTO_INCREMENT,
    org          varchar(255) NOT NULL DEFAULT 'NA',
    name         varchar(255) NOT NULL,
    version      varchar(50)  NOT NULL,
    publish_date date,
    author       varchar(10),
    type         varchar(10),
    license      varchar(100),
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
    FOREIGN KEY fk_art (artifact_id) REFERENCES artifact (id),
    FOREIGN KEY fk_tag (tag_id) REFERENCES tag (id)
) ENGINE = Innodb
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE vulnerability
(
    id           int           NOT NULL AUTO_INCREMENT,
    cve          varchar(50)   NULL,
    cnnvd        varchar(50)   NULL,
    cwe          varchar(50)   NULL,
    name         varchar(255)  NOT NULL,
    level        char(1)       NOT NULL DEFAULT '3',
    difficulty   char(1)       NOT NULL DEFAULT '3',
    exposed_date date          NULL,
    description  varchar(2000) NULL,
    suggestion   varchar(2000) NULL,
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
    FOREIGN KEY fk_art (artifact_id) REFERENCES artifact (id),
    FOREIGN KEY fk_vul (vulnerability_id) REFERENCES vulnerability (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE app
(
    id   int         NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    env  varchar(10) NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE artifact_app
(
    id          int NOT NULL AUTO_INCREMENT,
    artifact_id int NOT NULL,
    app_id      int NOT NULL,
    re_dev      tinyint(1),
    support     tinyint(1),
    INDEX idx_art_id (artifact_id),
    INDEX idx_app_id (app_id),
    UNIQUE INDEX uidx_aa (artifact_id, app_id),
    FOREIGN KEY fk_art (artifact_id) REFERENCES artifact (id),
    FOREIGN KEY fk_app (app_id) REFERENCES app (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE host
(
    id       int NOT NULL AUTO_INCREMENT,
    ip       int UNSIGNED,
    hardware varchar(10),
    network  varchar(10),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE app_host
(
    id      int NOT NULL AUTO_INCREMENT,
    app_id  int,
    host_id int,
    PRIMARY KEY (id),
    INDEX idx_app (app_id),
    INDEX idx_hst (host_id),
    UNIQUE INDEX uidx_ah (app_id, host_id),
    FOREIGN KEY fk_app (app_id) REFERENCES app (id),
    FOREIGN KEY fk_hst (host_id) REFERENCES host (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;