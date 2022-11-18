create table EVENT
(
    EVENT_ID   bigint       not null auto_increment,
    TITLE      varchar(255) not null,
    START_DATE datetime     not null,
    primary key (EVENT_ID)
);