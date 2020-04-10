
drop database if exists pmsdb;
create database pmsdb;

create table pmsdb.users
(
    id bigserial not null
        constraint users_pkey
            primary key,
    created_at timestamp,
    email varchar(255) UNIQUE,
    name varchar(255),
    password varchar(255),
    status varchar(255),
    updated_at timestamp
);


create table pmsdb.brands
(
    id bigserial not null
        constraint brands_pkey
            primary key,
    created_at timestamp,
    name varchar(255),
    status varchar(10),
    updated_at timestamp,
    user_id bigint
        constraint brands__user_id
            references users ON UPDATE CASCADE ON DELETE RESTRICT
);


create table pmsdb.properties
(
    id bigserial not null
        constraint properties_pkey
            primary key,
    code varchar(255),
    created_at timestamp,
    description varchar(255),
    name varchar(255) not null,
    status varchar(10),
    updated_at timestamp,
    brand_id bigint not null
        constraint properties__brand_id
            references brands ON UPDATE CASCADE ON DELETE RESTRICT,
    user_id bigint not null
        constraint properties__user_id
            references users ON UPDATE CASCADE ON DELETE RESTRICT
);

create table pmsdb.tokens
(
    id bigserial not null
        constraint tokens_pkey
            primary key,
    created_at timestamp,
    expires_at timestamp,
    token varchar(255),
    user_id bigint
        constraint tokens__user_id
            references users ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE UNIQUE INDEX users__email
    ON users(email);

CREATE UNIQUE INDEX properties__code
    ON properties(code);
