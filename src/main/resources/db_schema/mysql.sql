CREATE SCHEMA IF NOT EXISTS mobile_questions;

USE mobile_questions;

DROP TABLE IF EXISTS mobile_questions.dm_contact_info;
DROP TABLE IF EXISTS mobile_questions.dm_account_jumbo;
DROP TABLE IF EXISTS mobile_questions.dm_message;
DROP TABLE IF EXISTS mobile_questions.dm_message_user;
DROP TABLE IF EXISTS mobile_questions.dm_user;

CREATE TABLE IF NOT EXISTS mobile_questions.dm_contact_info (
	contact_info_id		BIGINT auto_increment,
	email_address		VARCHAR(50),
	phone_number		VARCHAR(14),
	primary key (contact_info_id)
);

CREATE TABLE IF NOT EXISTS mobile_questions.dm_account_jumbo (
	account_id		BIGINT auto_increment,
	username		VARCHAR(30) not null unique,
	password		VARCHAR(30) not null,
	secret_question VARCHAR(75) not null,
	secret_answer	varchar(50) not null,
	primary key (account_id)
);


CREATE TABLE IF NOT EXISTS mobile_questions.dm_user (
	user_id			BIGINT auto_increment unique,
	first_name		VARCHAR(50) not null,
	last_name   	VARCHAR(50) not null,
	age				INT not null,
	contact_info_id	BIGINT,
	account_id 		BIGINT not null,
	primary key (user_id),
	foreign key (contact_info_id) 
		references dm_contact_info(contact_info_id) on delete set null,
	foreign key (account_id) 
		references dm_account_jumbo(account_id) on delete cascade
);

CREATE TABLE IF NOT EXISTS mobile_questions.dm_message (
	message_id		BIGINT not null,
	user_id			BIGINT not null,
	message			varchar(140) not null,
	date_posted		timestamp default now(),
	primary key (message_id, user_id, friend_id),
	foreign key (user_id) references dm_user(user_id) on delete cascade
);

CREATE TABLE IF NOT EXISTS mobile_questions.dm_message_user (
	message_id		number(10) not null,
	user_id			number(10) not null,
	friend_id		number(10) not null,
	primary key (message_id, user_id, friend_id),
	foreign key (message_id, user_id)
		references dm_message(message_id, user_id) on delete cascade,
	foreign key (user_id)
		references dm_user(user_id) on delete cascade,
	foreign key (friend_id)
		references dm_user(user_id) on delete cascade
);