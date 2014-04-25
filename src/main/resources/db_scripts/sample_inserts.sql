INSERT INTO dm_account_jumbo
  (username, password, secret_question, secret_answer)
  VALUES
  ('test.guy', '54321', 'what is this', '42');

INSERT INTO dm_account_jumbo
  (username, password, secret_question, secret_answer)
  VALUES
  ('guy.test', '54321', 'what is this', '42');

INSERT INTO dm_account_jumbo
  (username, password, secret_question, secret_answer)
  VALUES
  ('tester.guy', '54321', 'what is this', '42');


INSERT INTO dm_contact_info
  (email_address, phone_number)
  VALUES
  ('test.guy@ggg.com', '54321');

INSERT INTO dm_contact_info
  (email_address, phone_number)
  VALUES
  ('guy.test@ggg.com', '54321');

INSERT INTO dm_contact_info
  (email_address, phone_number)
  VALUES
  ('tester.guy@ggg.com', '54321');



INSERT INTO dm_user
  (first_name , last_name, age, contact_info_id, account_id)
  values
  ('test', 'guy', 52, 1, 1);

INSERT INTO dm_user
  (first_name , last_name, age, contact_info_id, account_id)
  values
  ('guy', 'test', 17, 2, 2);

INSERT INTO dm_user
  (first_name , last_name, age, contact_info_id, account_id)
  values
  ('tester', 'guy', 27, 3, 3);



INSERT INTO dm_message
  (user_id, message)
  VALUES
  (1, 'This is a test 1');

INSERT INTO dm_message
  (user_id, message)
  VALUES
  (2, 'This is a test 2');

INSERT INTO dm_message
  (user_id, message)
  VALUES
  (3, 'This is a test 3');


INSERT INTO dm_message_user
  (message_id, user_id, friend_id)
  values
  (1, 1, 2);
INSERT INTO dm_message_user
  (message_id, user_id, friend_id)
  values
  (1, 1, 3);

INSERT INTO dm_message_user
  (message_id, user_id, friend_id)
  values
  (2, 2, 1);
INSERT INTO dm_message_user
  (message_id, user_id, friend_id)
  values
  (2, 2, 3);

INSERT INTO dm_message_user
  (message_id, user_id, friend_id)
  values
  (3, 3, 2);