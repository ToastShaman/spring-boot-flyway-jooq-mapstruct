create table EVENT
(
    EVENT_ID      bigint       not null auto_increment,
    VERSION       bigint       not null,
    TITLE         varchar(255) not null,
    START_DATE    datetime     not null,
    DELETED       boolean      not null,
    LAST_MODIFIED datetime     not null,
    primary key (EVENT_ID)
);

create table EVENT_HISTORY
(
    HISTORY_ID    bigint       not null auto_increment,
    AUDIT_DATE    datetime     not null,
    AUDIT_USER    varchar(255) not null,
    EVENT_ID      bigint       not null,
    VERSION       bigint       not null,
    TITLE         varchar(255) not null,
    START_DATE    datetime     not null,
    DELETED       boolean      not null,
    LAST_MODIFIED datetime     not null,
    primary key (HISTORY_ID),
    foreign key (EVENT_ID) references EVENT (EVENT_ID)
);
