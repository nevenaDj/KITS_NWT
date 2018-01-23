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
-- password is 'vodovod' (bcrypt encoded)
insert into user (username, password) values ('vodovod2', '$2a$10$HM/jd7gMTV9gYs7CzutfSeTNVboWAC7Qv81nWB1F6Og4EKgDodfFe');
-- password is 'user' (bcrypt encoded)
insert into user (username, password, email, phone_no) values ('user2', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq', 'user@user.com', '123456');


insert into user_authority (user_id, authority_id) values (1, 1); -- admin has ROLE_ADMIN
insert into user_authority (user_id, authority_id) values (2, 2); -- user has ROLE_USER
insert into user_authority (user_id, authority_id) values (3, 3); -- user has ROLE_COMPANY
insert into user_authority (user_id, authority_id) values (4, 4); -- user has ROLE_OWNER
insert into user_authority (user_id, authority_id) values (5, 5); -- user has ROLE_PRESIDENT
insert into user_authority (user_id, authority_id) values (2, 4); -- user has ROLE_USER
insert into user_authority (user_id, authority_id) values (6, 3); -- vodovod2 has ROLE_COMPANY
insert into user_authority (user_id, authority_id) values (7, 2); -- user2 has ROLE_USER

insert into address (street,number, zip_code, city) values ('address','1a', 21000, 'Novi Sad');
insert into address (street,number, zip_code, city) values ('address','1b', 21000, 'Novi Sad');

insert into building (address_id, president_id) values (1,5);
insert into building (address_id) values (2);

insert into apartment (description,number, building_id, owner_id) values ('description 1', 1, 1, 4);
insert into apartment (description,number, building_id, owner_id) values ('description 2', 2, 1, 2);
insert into apartment (description,number, building_id) values ('description 3', 1, 2);

insert into user_aparment (tenant_id, apartment_id) values (2, 1);
insert into user_aparment (tenant_id, apartment_id) values (7, 2);

insert into meeting (building_id, date_and_time, active) values (1,'2017-11-11', true);


insert into survey (title, end, meeting_id) values ('survey','2017-12-12',1);

insert into question (text, type, survey_id) values ('question 1', 'checkbox', 1);
insert into question (text, type, survey_id) values ('question 2', 'radio', 1);

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
insert into glitch_type (type) values ('WATER');

insert into glitch (description, date_of_report, apartment_id, company_id, responsible_person_id, state_id, tenant_id, date_of_repair_approved, type_id) 
			values ('glitch','2017-12-03', 1, 3, 5, 1, 2, false, 2);
insert into glitch (description, date_of_report, apartment_id, company_id, responsible_person_id, state_id, tenant_id, date_of_repair_approved, type_id) 
			values ('glitch2','2017-12-03', 1, 3, 5, 1, 2, false,1);
insert into glitch (description, date_of_report, apartment_id, company_id, responsible_person_id, state_id, tenant_id, date_of_repair_approved, type_id, date_of_repair) 
			values ('glitch3','2017-12-03', 1, 6, 2, 2, 2, false,1,'2018-02-03');			
insert into glitch (description, date_of_report, apartment_id, company_id, responsible_person_id, state_id, tenant_id, date_of_repair_approved, type_id, date_of_repair) 
			values ('glitch3','2018-01-03', 1, 3, 2, 2, 2, true,2,'2018-01-03');		

insert into bill(company_id, price, date, approved, glitch_id) values (3, 1000, '2017-12-03', false,1);

insert into comment (text, user_id, glitch_id) values ('comment', 2, 1);
insert into comment (text, user_id, glitch_id) values ('new comment', 3, 1);
			
insert into agenda_item(title, meeting_id, number, glitch_id ) values ('Title',1, 1, 1);
insert into item_comment(writer_id, text, date) values (1, 'text', '2017-12-03');
insert into agenda_item_comments(agenda_item_id, comments_id) values (1,1);

insert into communal_problem (description) values ('communal problem');

insert into notification(text, building_id, writer_id ) values ('text', 1, 1);

insert into pricelist(company_id, type_id) values(3,2);
insert into pricelist(company_id, type_id) values(6,2);

insert into item_in_princelist(name_of_type, price, pricelist_id) values('name', 1000, 1);
insert into item_in_princelist(name_of_type, price, pricelist_id) values('new item', 2000, 1);

insert into item_in_bill(name_of_item, price, piece, bill_id) values('name', 1000, 1, 1);

