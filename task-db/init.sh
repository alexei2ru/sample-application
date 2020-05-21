#!/bin/bash


psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" <<-EOSQL
CREATE DATABASE tasks;
CREATE USER dbuser WITH CREATEDB PASSWORD 'dbpassword';

GRANT ALL PRIVILEGES ON DATABASE tasks TO dbuser;
ALTER USER dbuser WITH SUPERUSER;
EOSQL

psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d tasks <<-EOSQL


create table task
(
	id bigint not null,
	description varchar(200) not null,
	primary key (id)
);

create sequence sq_task_id increment by 5 start with 100;

INSERT INTO public.task (Id, description)
VALUES  (nextval(sq_task_id), 'Do some shit'),
        (nextval(sq_task_id), 'Do some shit later'),
        (nextval(sq_task_id),'Do some more shit')


EOSQL
