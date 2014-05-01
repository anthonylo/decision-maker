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
  
commit;insert into dm_account_jumbo 
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