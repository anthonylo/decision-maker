-- Drop anything related to the Decision Maker application
DROP TABLE dm_contact_info    CASCADE CONSTRAINTS;
DROP TABLE dm_account_jumbo   CASCADE CONSTRAINTS;
DROP TABLE dm_message_user    CASCADE CONSTRAINTS;
DROP TABLE dm_message         CASCADE CONSTRAINTS;
DROP TABLE dm_user_friend     CASCADE CONSTRAINTS;
DROP TABLE dm_user            CASCADE CONSTRAINTS;

DROP SEQUENCE dm_contact_info_seq;
DROP SEQUENCE dm_account_jumbo_seq;
DROP SEQUENCE dm_message_seq;
DROP SEQUENCE dm_user_seq;

-- Tables
CREATE TABLE dm_contact_info (
	contact_info_id       number(10) not null,
	email_address		  varchar(50),
	phone_number		  varchar(14),
	constraint pk_dm_contact_info primary key (contact_info_id)
);

CREATE TABLE dm_account_jumbo (
	account_id          number(10)  not null,
	username		    varchar(30) not null unique,
	password		    varchar(30) not null,
	secret_question     varchar(75) not null,
	secret_answer	    varchar(50) not null,
    date_created        date default sysdate,
    active              number(1) default 0 not null,
	constraint pk_dm_account_jumbo primary key (account_id)
);

CREATE TABLE dm_user (
	user_id             number(10)  not null,
	first_name		    varchar(50) not null,
	last_name   	    varchar(50) not null,
	age				    number(4)   not null,
	contact_info_id	    number(10),
	account_id 		    number(10)  not null,
	constraint pk_dm_user primary key (user_id),
	constraint fk1_dm_user
		foreign key (contact_info_id) 
		references dm_contact_info(contact_info_id) on delete set null,
	constraint fk2_dm_user
		foreign key (account_id) 
		references dm_account_jumbo(account_id) on delete cascade
);

CREATE TABLE dm_user_friend (
  user_id         number(10) not null,
  friend_id       number(10) not null,
  friendship_started    date default sysdate,
  constraint pk_dm_user_friend primary key (user_id, friend_id),
  constraint fk1_dm_user_friend
    foreign key (user_id)
    references dm_user(user_id) on delete cascade,
  constraint fk2_dm_user_friend
    foreign key (friend_id)
    references dm_user(user_id) on delete cascade
);

CREATE TABLE dm_message (
	message_id		number(10) 			 not null,
	user_id			number(10) 	     	 not null,
	message			varchar(140) 	  	 not null,
	date_posted	    date default sysdate not null,
	constraint pk_dm_message primary key (message_id),
	constraint fk1_dm_message
		foreign key (user_id)
		references dm_user(user_id) on delete cascade
);

CREATE TABLE dm_message_user (
	message_id		number(10) not null,
	sender_id		number(10) not null,
	recipient_id	number(10) not null,
	constraint pk_dm_message_user 
		primary key (message_id, sender_id, recipient_id),
	constraint fk1_dm_message_user
		foreign key (message_id)
		references dm_message(message_id) on delete cascade,
	constraint fk2_dm_message_user
		foreign key (sender_id)
		references dm_user(user_id) on delete cascade,
	constraint fk3_dm_message_user
		foreign key (recipient_id)
		references dm_user(user_id) on delete cascade
);

-- Sequences
CREATE SEQUENCE dm_contact_info_seq START WITH 1 INCREMENT BY 1 CACHE 100;
CREATE SEQUENCE dm_account_jumbo_seq START WITH 1 INCREMENT BY 1 CACHE 100;
CREATE SEQUENCE dm_user_seq START WITH 1 INCREMENT BY 1 CACHE 100;
CREATE SEQUENCE dm_message_seq START WITH 1 INCREMENT BY 1 CACHE 100;

-- Triggers
CREATE OR REPLACE TRIGGER dm_contact_info_trigger
  BEFORE INSERT ON dm_contact_info
  FOR EACH ROW
  BEGIN
    IF :new.contact_info_id IS NULL THEN
      SELECT dm_contact_info_seq.NEXTVAL
      INTO :new.contact_info_id
      FROM dual;
    END IF;
  END;
/  

CREATE OR REPLACE TRIGGER dm_account_jumbo_trigger
  BEFORE INSERT ON dm_account_jumbo
  FOR EACH ROW
  BEGIN
    IF :new.account_id IS NULL THEN
      SELECT dm_account_jumbo_seq.NEXTVAL
      INTO :new.account_id
      FROM dual;
    END IF;
    
    IF :new.date_created IS NULL THEN
      SELECT sysdate 
      INTO :new.date_created
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER dm_user_trigger
  BEFORE INSERT ON dm_user
  FOR EACH ROW
  BEGIN
    IF :new.user_id IS NULL THEN
      SELECT dm_user_seq.NEXTVAL
      INTO :new.user_id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER dm_user_friend_trigger
  BEFORE INSERT ON dm_user_friend
  FOR EACH ROW
  BEGIN
    IF :new.friendship_started IS NULL THEN
      SELECT sysdate 
      INTO :new.friendship_started
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER dm_message_trigger
  BEFORE INSERT ON dm_message
  FOR EACH ROW
  BEGIN
    IF :new.message_id IS NULL THEN
      SELECT dm_message_seq.NEXTVAL
      INTO :new.message_id
      FROM dual;
    END IF;
    
    IF :new.date_posted IS NULL THEN
      SELECT sysdate 
      INTO :new.date_posted
      FROM dual;
    END IF;
  END;
/

COMMIT;


insert into dm_account_jumbo 
  (username, password, secret_question, secret_answer)
  values
  ('test.guy', '54321', 'what is this', '42');

insert into dm_account_jumbo 
  (username, password, secret_question, secret_answer)
  values
  ('guy.test', '54321', 'what is this', '42');

insert into dm_account_jumbo 
  (username, password, secret_question, secret_answer)
  values
  ('tester.guy', '54321', 'what is this', '42');
  
insert into dm_account_jumbo 
  (username, password, secret_question, secret_answer)
  values
  ('accounters', '54321', 'hello', 'world');

  

insert into dm_contact_info
  (email_address, phone_number)
  values
  ('test.guy@ggg.com', '54321');

insert into dm_contact_info
  (email_address, phone_number)
  values
  ('guy.test@ggg.com', '54321');

insert into dm_contact_info
  (email_address, phone_number)
  values
  ('tester.guy@ggg.com', '54321');



insert into dm_user
  (first_name , last_name, age, contact_info_id, account_id)
  values
  ('test', 'guy', 52, 1, 1);

insert into dm_user
  (first_name , last_name, age, contact_info_id, account_id)
  values
  ('guy', 'test', 17, 2, 2);

insert into dm_user
  (first_name , last_name, age, contact_info_id, account_id)
  values
  ('tester', 'guy', 27, 3, 3);

insert into dm_user
  (first_name, last_name, age, account_id)
  values
  ('hello', 'world', 30, 4);
  
  
insert into dm_user_friend (user_id, friend_id) values (1, 2);
insert into dm_user_friend (user_id, friend_id) values (2, 1);
  
insert into dm_user_friend (user_id, friend_id) values (1, 3);
insert into dm_user_friend (user_id, friend_id) values (3, 1);
  
insert into dm_user_friend (user_id, friend_id) values (2, 3);
insert into dm_user_friend (user_id, friend_id) values (3, 2);

insert into dm_user_friend (user_id, friend_id) values (4, 2);
insert into dm_user_friend (user_id, friend_id) values (2, 4);


insert into dm_message
  (user_id, message)
  values
  (1, 'This is test 1');

insert into dm_message
  (user_id, message)
  values
  (1, 'This is test 2');

insert into dm_message
  (user_id, message)
  values
  (2, 'This is test 3');
  
insert into dm_message
  (user_id, message)
  values
  (4, 'This is test 4');
  
insert into dm_message
  (user_id, message)
  values
  (3, 'This is test 5');


insert into dm_message_user
  (message_id, sender_id, recipient_id)
  values
  (1, 1, 2);
insert into dm_message_user
  (message_id, sender_id, recipient_id)
  values
  (1, 1, 3);

insert into dm_message_user
  (message_id, sender_id, recipient_id)
  values
  (2, 1, 2);
  
insert into dm_message_user
  (message_id, sender_id, recipient_id)
  values
  (3, 2, 3);

insert into dm_message_user
  (message_id, sender_id, recipient_id)
  values
  (4, 4, 2);
  
insert into dm_message_user
  (message_id, sender_id, recipient_id)
  values
  (5, 3, 1);
  
commit;