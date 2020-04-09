

create database apidb;

create table brands
(
	id bigserial not null
		constraint brands_id
			primary key,
	created_at timestamp  not null DEFAULT NOW(),
	name varchar(255),
	status varchar(10),
	updated_at timestamp
);

create table users
(
	id bigserial not null
		constraint users_id
			primary key,
	created_at timestamp  not null DEFAULT NOW(),
	email varchar(70) UNIQUE,
	name varchar(255),
	password varchar(255),
	status varchar(10),
	updated_at timestamp
);

create table properties
(
	id bigserial not null
		constraint properties_id
			primary key,
	code varchar(255),
	created_at timestamp not null DEFAULT NOW(),
	description varchar(255),
	name varchar(255),
	status varchar(10),
	updated_at timestamp,
	brand_id bigint,
	CONSTRAINT properties__brand_id FOREIGN KEY (brand_id)
      REFERENCES brands (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE RESTRICT,
    user_id bigint,
	CONSTRAINT properties__user_id FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE RESTRICT
	
);

create table tokens
(
	id bigserial not null
		constraint tokens_id
			primary key,
	created_at timestamp  not null DEFAULT NOW(),
	expires_at timestamp,
	token varchar(255),
	user_id bigint,
		CONSTRAINT tokens__user_id FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

