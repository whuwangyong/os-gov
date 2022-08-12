-- auto-generated definition
create table user
(
    id           int auto_increment
        primary key,
    email        varchar(50)                         not null,
    name         varchar(30)                         not null,
    address      varchar(200)                        null,
    created_time timestamp default CURRENT_TIMESTAMP not null,
    updated_time timestamp default CURRENT_TIMESTAMP not null,
    constraint uidx_phone
        unique (email)
) engine = InnoDB
  DEFAULT CHARSET = utf8mb4;

create index idx_name
    on user (name);

create table vulnerability
(
    id           int auto_increment,
    cve          varchar(50)  null,
    cnnvd        varchar(50)  null,
    cwe          varchar(50)  null,
    name         varchar(255) null,
    level        char         null,
    exposed_date datetime     null,
    description  varchar(900) null,
    suggestion   varchar(900) null,
    primary key (id),
    unique index uidx_cve (cve),
    unique index uidx_cnnvd (cnnvd),
    unique index uidx_cwe (cwe),
    index idx_name (name)
) engine = InnoDB
  DEFAULT CHARSET = utf8mb4;

create table component
(
    id          int auto_increment,
    group_id    varchar(255) not null ,
    artifact_id varchar(255) not null,
    version     varchar(50)  not null,
    primary key (id),
    index idx_artifact_id (artifact_id),
    constraint gav unique (group_id, artifact_id, version)
) engine = InnoDB;

create table software
(
    id          int auto_increment,
    group_id    varchar(255) not null,
    artifact_id varchar(255) not null,
    version     varchar(50)  not null,
    primary key (id),
    index idx_artifact_id (artifact_id),
    constraint gav unique (group_id, artifact_id, version)
) engine = InnoDB;

create table tt
(
    id int primary key ,
    name char(10)
)

select name, CHAR_LENGTH(name) from tt where id=4;

insert into tt (id, name) values (4, '            ');