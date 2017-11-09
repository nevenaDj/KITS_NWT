set foreign_key_checks = 0;

insert into authority (name) values ('ROLE_ADMIN');
insert into authority (name) values ('ROLE_USER');
insert into authority (name) values ('ROLE_COMPANY');
insert into authority (name) values ('ROLE_OWNER');
insert into authority (name) values ('ROLE_PRESIDENT');

-- password is 'admin' (bcrypt encoded) 
insert into user (username, password) values ('admin', '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
-- password is 'user' (bcrypt encoded)
insert into user (username, password) values ('user', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq');

insert into user_authority (user_id, authority_id) values (1, 1); -- admin has ROLE_ADMIN
insert into user_authority (user_id, authority_id) values (2, 2); -- user has ROLE_USER

insert into building (address) values ('address 1');
insert into building (address) values ('address 2');

insert into apartment (description,number) values ('description 1', 1);

