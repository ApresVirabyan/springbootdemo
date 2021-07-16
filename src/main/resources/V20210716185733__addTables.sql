create table users
(
    id bigint auto_increment,
    email varchar(250) not null,
    password varchar(8) not null,
    create_time date null,
    last_update_time time null,
    constraint users_pk
        primary key (id)
);

create unique index users_email_uindex
	on users (email);



create table notes
(
    id bigint auto_increment,
    title varchar(50) not null,
    note varchar(1000) null,
    create_time date null,
    last_update_time date null,
    user_id bigint null,
    constraint notes_pk
        primary key (id),
    constraint notes___fk
        foreign key (user_id) references users (id)
);
