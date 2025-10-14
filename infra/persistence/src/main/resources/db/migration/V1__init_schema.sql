create table public.event
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    id                 uuid         not null
        primary key,
    created_by         varchar(255) not null,
    description        varchar(255) not null,
    last_modified_by   varchar(255) not null,
    name               varchar(255) not null
        unique,
    schema_type        varchar(255) not null
        constraint event_schema_type_check
            check ((schema_type)::text = ANY
        ((ARRAY ['AVRO':: character varying, 'PROTOBUF':: character varying])::text[])
) ,
    source             varchar(255)
);

create table public.notification
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    event_id           uuid         not null
        unique,
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

create table public.delivery_attempt
(
    attempt            integer      not null,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    id                 uuid         not null
        primary key,
    notification_id    uuid         not null
        constraint fkj3jmrdv479hmk4h50de528t1x
            references public.notification,
    channel_type       varchar(255) not null
        constraint delivery_attempt_channel_type_check
            check ((channel_type)::text = ANY
        ((ARRAY ['SMS':: character varying, 'PUSH':: character varying, 'EMAIL':: character varying])::text[])
) ,
    created_by         varchar(255) not null,
    error              varchar(255),
    last_modified_by   varchar(255) not null,
    provider_response  varchar(255),
    source             varchar(255)
);

create table public.notification_template
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

create table public.template_allowed_channels
(
    allowed_channels smallint not null
        constraint template_allowed_channels_allowed_channels_check
            check ((allowed_channels >= 0) AND (allowed_channels <= 2)),
    template_id      uuid     not null
        constraint fktpyjp9owepawy1a7i42jsux6i
            references public.notification_template
);

create table public.template_event
(
    event_id    uuid not null
        constraint fkhmo0hmwifyjs734hkq31mcogb
            references public.event,
    template_id uuid not null
        constraint fk181tstiy7dd0e1k5khge6mabv
            references public.notification_template
);

create table public.template_translation
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6) not null,
    id                 uuid         not null
        primary key,
    template_id        uuid
        constraint fk10c8wh6djdv0shkawy4cctlp9
            references public.notification_template,
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

create table public.user_preference
(
    default_channel_type smallint     not null
        constraint user_preference_default_channel_type_check
            check ((default_channel_type >= 0) AND (default_channel_type <= 2)),
    created_date         timestamp(6) not null,
    last_modified_date   timestamp(6) not null,
    id                   uuid         not null
        primary key,
    user_id              uuid         not null
        unique,
    created_by           varchar(255) not null,
    last_modified_by     varchar(255) not null,
    source               varchar(255)
);

