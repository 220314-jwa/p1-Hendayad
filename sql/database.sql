drop table story cascade;
drop table user_role cascade;
drop table person cascade ;
drop table status cascade ;
create table user_role (
	id serial primary key,
	name varchar(50)
);
insert into user_role (name) values
	('User'),
	('Employee');
create table person (
	id serial primary key,
	full_name varchar(100) not null,
	username varchar(30) unique not null,
	passwd varchar(45) not null,
	role_id integer not null references user_role
);
insert into person (full_name,username,passwd,role_id) values
 ('Sierra Nicholes','snicholes','pass',2),
 ('Brian Arayathel','barayathel','pass',1),
 ('Rory Eiffe','reiffe','pass',1),
 ('Henry Hsieh', 'hhsieh','pass',1);



create table status (
	id serial primary key,
	name varchar(50)
);
insert into status (name) values
	('Pendinng'),
	('Approved');







create table story (
id integer not null,
authorname varchar (50) not null,
title varchar (50) not null,
completiondata varchar (50) not null,
genre varchar (50) not null,
lengthofstory varchar (50) not null,
onesentence varchar (50) not null ,
status varchar (50) not null,
description varchar (500) not null
);


insert into story  (id,authorname,title ,completiondata ,genre,lengthofstory ,onesentence ,status,description) 
values ('1000','hana','hanastory','(9/5/1996)','action','long','you will love it','Approved','workstory');
insert into story  (id,authorname,title ,completiondata ,genre,lengthofstory ,onesentence ,status,description)
values('1001','tara','tarastory','(9/7/1996)','drama','long','you will adore it','Approved','storyofmine');
insert into story  (id,authorname,title ,completiondata ,genre,lengthofstory ,onesentence ,status,description)
values('1002','lana','lanastory','(9/4/1996)','comedy','short','you will go forit','pending','comedy in this time');
insert into story  (id,authorname,title ,completiondata ,genre,lengthofstory ,onesentence ,status,description)
values('1003','sama','samastory','(9/3/1996)','romance','short','you will master it','pending','romance in my time');

 
insert into user_role (name) values
	('User'),
	('Employee');

select *from story;
select*from person;
