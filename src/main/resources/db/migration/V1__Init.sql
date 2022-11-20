create table EVENT
(
    EVENT_ID   bigint       not null auto_increment,
    VERSION    bigint       not null default 0,
    TITLE      varchar(255) not null,
    START_DATE datetime     not null,
    DELETED    boolean      not null default false,
    primary key (EVENT_ID)
);

create table EVENT_HISTORY
(
    LOG_ID           bigint       not null auto_increment,
    CHANGE_TYPE      varchar(255) not null,
    CHANGE_TIMESTAMP timestamp    not null,
    USER_EMAIL       varchar(255) not null,
    EVENT_ID         bigint       not null,
    TITLE            varchar(255) not null,
    START_DATE       datetime     not null,
    primary key (LOG_ID),
    foreign key (EVENT_ID) REFERENCES EVENT (EVENT_ID)
);
