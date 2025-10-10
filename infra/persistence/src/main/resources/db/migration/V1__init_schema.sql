create table notification_template_events
(
    events_id          uuid not null,
    template_entity_id uuid not null
);

alter table notification_template_events
    owner to postgres;

create table event
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    id                 uuid         not null
        primary key,
    created_by         varchar(255) not null,
    description        varchar(255) not null,
    last_modified_by   varchar(255) not null,
    name               varchar(255) not null,
    schema_type        varchar(255) not null
        constraint event_schema_type_check
            check ((schema_type)::text = ANY
        ((ARRAY ['AVRO':: character varying, 'PROTOBUF':: character varying])::text[])
) ,
    source             varchar(255)
);

alter table event
    owner to postgres;

create table notification
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    event_id           uuid         not null,
    id                 uuid         not null
        primary key,
    user_id            uuid         not null,
    body               text,
    created_by         varchar(255) not null,
    email              varchar(255) not null,
    last_modified_by   varchar(255) not null,
    phone_number       varchar(255) not null,
    source             varchar(255),
    status             varchar(255) not null
        constraint notification_status_check
            check ((status)::text = ANY
        ((ARRAY ['IN_PROGRESS':: character varying, 'DELIVERED':: character varying, 'FAILED':: character varying])::text[])
) ,
    subject            varchar(255)
);

alter table notification
    owner to postgres;

create table delivery_attempt
(
    attempt            integer      not null,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    id                 uuid         not null
        primary key,
    notification_id    uuid         not null
        constraint fkj3jmrdv479hmk4h50de528t1x
            references notification,
    channel_type       varchar(255) not null
        constraint delivery_attempt_channel_type_check
            check ((channel_type)::text = ANY
        ((ARRAY ['SMS':: character varying, 'PUSH':: character varying, 'EMAIL':: character varying])::text[])
) ,
    created_by         varchar(255) not null,
    error              varchar(255),
    last_modified_by   varchar(255) not null,
    provider_response  varchar(255) not null,
    source             varchar(255)
);

alter table delivery_attempt
    owner to postgres;

create table notification_template
(
    channel            smallint     not null
        constraint notification_template_channel_check
            check ((channel >= 0) AND (channel <= 2)),
    delivery_attempts  integer      not null,
    priority           smallint     not null
        constraint notification_template_priority_check
            check ((priority >= 0) AND (priority <= 2)),
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    id                 uuid         not null
        primary key,
    created_by         varchar(255) not null,
    last_modified_by   varchar(255) not null,
    source             varchar(255)
);

alter table notification_template
    owner to postgres;

create table template_allowed_channels
(
    allowed_channels smallint not null
        constraint template_allowed_channels_allowed_channels_check
            check ((allowed_channels >= 0) AND (allowed_channels <= 2)),
    template_id      uuid     not null
        constraint fktpyjp9owepawy1a7i42jsux6i
            references notification_template
);

alter table template_allowed_channels
    owner to postgres;

create table template_event
(
    event_id    uuid not null
        constraint fkhmo0hmwifyjs734hkq31mcogb
            references event,
    template_id uuid not null
        constraint fk181tstiy7dd0e1k5khge6mabv
            references notification_template
);

alter table template_event
    owner to postgres;

create table template_translation
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    id                 uuid         not null
        primary key,
    template_id        uuid
        constraint fk10c8wh6djdv0shkawy4cctlp9
            references notification_template,
    body               varchar(255) not null,
    created_by         varchar(255) not null,
    last_modified_by   varchar(255) not null,
    locale             varchar(255) not null
        constraint template_translation_locale_check
            check ((locale)::text = ANY ((ARRAY ['kz':: character varying, 'ru':: character varying])::text[])
) ,
    name               varchar(255) not null,
    source             varchar(255),
    subject            varchar(255) not null
);

alter table template_translation
    owner to postgres;

create table user_preference
(
    default_channel_type smallint     not null
        constraint user_preference_default_channel_type_check
            check ((default_channel_type >= 0) AND (default_channel_type <= 2)),
    created_date         timestamp(6) not null,
    last_modified_date   timestamp(6) not null,
    id                   uuid         not null
        primary key,
    user_id              uuid         not null,
    created_by           varchar(255) not null,
    last_modified_by     varchar(255) not null,
    source               varchar(255)
);

alter table user_preference
    owner to postgres;