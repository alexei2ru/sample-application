create table task
(
	id bigint not null,
	description varchar(200) not null,
	primary key (id)
);

create sequence sq_task_id increment by 5 start with 100;
