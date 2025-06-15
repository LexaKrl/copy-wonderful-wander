create table walks
(
    meters      integer,
    steps       integer,
    created_at  timestamp(6),
    finished_at timestamp(6),
    user_id     uuid,
    walk_id     uuid not null
        primary key,
    description varchar(255),
    name        varchar(255),
    walk_status varchar(255)
        constraint walks_walk_status_check
            check ((walk_status)::text = ANY
                   ((ARRAY ['NOT_STARTED'::character varying, 'RUNNING'::character varying, 'FINISHED'::character varying])::text[]))
);

create table walk_participants
(
    participant_id uuid,
    walk_id        uuid not null
        constraint fkajthe9lh5tnkiqbq7403co8cq
            references walks
);

create table walk_photos
(
    walk_id uuid not null
        constraint fkf8lpurhmcca259u22hhtirky5
            references walks,
    photo   varchar(255)
);