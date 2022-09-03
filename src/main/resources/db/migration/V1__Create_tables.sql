drop table if exists events;
drop table if exists files;
drop table if exists users;

create table events (
                        id bigint not null auto_increment,
                        event_type varchar(255) not null,
                        timestamp datetime(6),
                        file_id bigint not null,
                        user_id bigint not null,
                        primary key (id));
create table files (
                       id bigint not null auto_increment,
                       filePath varchar(255) not null,
                       name varchar(255) not null,
                       primary key (id));
create table users (
                       id bigint not null auto_increment,
                       name varchar(25) not null,
                       primary key (id));

alter table events add constraint FK_file_id foreign key (file_id) references files (id);
alter table events add constraint FK_user_id foreign key (user_id) references users (id);