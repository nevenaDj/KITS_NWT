set foreign_key_checks = 0;

insert into authority (name) values ('ROLE_ADMIN');
insert into authority (name) values ('ROLE_USER');
insert into authority (name) values ('ROLE_COMPANY');
insert into authority (name) values ('ROLE_OWNER');
insert into authority (name) values ('ROLE_PRESIDENT');

-- password is 'admin' (bcrypt encoded) 
insert into user (username, password) values ('admin', '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
-- password is 'user' (bcrypt encoded)
insert into user (username, password, email, phone_no) values ('user', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq', 'user@user.com', '123456');
-- password is 'vodovod' (bcrypt encoded)
insert into user (username, password) values ('vodovod', '$2a$10$HM/jd7gMTV9gYs7CzutfSeTNVboWAC7Qv81nWB1F6Og4EKgDodfFe');
-- password is 'user' (bcrypt encoded)
insert into user (username, password) values ('owner', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq');
-- password is 'user' (bcrypt encoded)
insert into user (username, password) values ('president', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq');

insert into user_authority (user_id, authority_id) values (1, 1); -- admin has ROLE_ADMIN
insert into user_authority (user_id, authority_id) values (2, 2); -- user has ROLE_USER
insert into user_authority (user_id, authority_id) values (3, 3); -- user has ROLE_COMPANY
insert into user_authority (user_id, authority_id) values (4, 4); -- user has ROLE_OWNER
insert into user_authority (user_id, authority_id) values (5, 5); -- user has ROLE_PRESIDENT

insert into address (street,number, zip_code, city) values ('address','1a', 21000, 'Novi Sad');
insert into address (street,number, zip_code, city) values ('address','1b', 21000, 'Novi Sad');

insert into building (address_id, president_id) values (1,5);
insert into building (address_id) values (2);

insert into apartment (description,number, building_id, owner_id) values ('description 1', 1, 1, 4);
insert into apartment (description,number, building_id) values ('description 2', 2, 1);
insert into apartment (description,number, building_id) values ('description 3', 1, 2);

insert into meeting (building_id, date_and_time, active) values (1,'2017-11-11', true);

insert into survey (title, end, meeting_id) values ('survey','2017-12-12',1);

insert into question (text, survey_id) values ('question 1', 1);
insert into question (text, survey_id) values ('question 2', 1);

insert into question_option (text, question_id) values ('option 1', 1);
insert into question_option (text, question_id) values ('option 2', 1);
insert into question_option (text, question_id) values ('option 3', 1);
insert into question_option (text, question_id) values ('option 4', 1);

insert into question_option (text, question_id) values ('option 1', 2);
insert into question_option (text, question_id) values ('option 2', 2);

insert into glitch_state (state) values ('REPORTED');
insert into glitch_state (state) values ('IN PROGRESS');
insert into glitch_state (state) values ('DONE');

insert into glitch_type (type) values ('TYPE');

insert into bill(company_id, price, date, approved) values (3, 1000, '2017-12-03', false);

insert into glitch (description, date_of_report, apartment_id, company_id, responsible_person_id, state_id, tenant_id, date_of_repair_approved, bill_id) 
			values ('glitch','2017-12-03', 1, 1, 5, 1, 2, false, 1);
insert into glitch (description, date_of_report, apartment_id, company_id, responsible_person_id, state_id, tenant_id, date_of_repair_approved) 
			values ('glitch2','2017-12-03', 1, 1, 5, 1, 2, false);
			
insert into agenda_item(title, meeting_id, number, glitch_id ) values ('Title',1, 1, 1);
			
insert into communal_problem (description) values ('communal problem');





