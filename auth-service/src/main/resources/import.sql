insert into app_user(id, username) values (1, 'Laguna');
insert into app_user(id, username) values (2,  'National Geography');
insert into app_user(id, username) values (3,  'Science Mag');

insert into app_user(id, username) values (4,  'Company1');
insert into app_user(id, username) values (5,  'Company2');
insert into app_user(id, username) values (6,  'Admin');


insert into privilege(id, name) value (1, 'READ_PRIVILEGE');
insert into privilege(id, name) value (2, 'WRITE_PRIVILEGE');

insert into role(id, name) value(1, 'CLIENT');
insert into role(id, name) value(2, 'COMPANY');
insert into role(id, name) value(3, 'ADMIN');

insert into app_user_roles(app_user_id, roles_id) value (1, 1);
insert into app_user_roles(app_user_id, roles_id) value (2, 1);
insert into app_user_roles(app_user_id, roles_id) value (3, 2);
insert into app_user_roles(app_user_id, roles_id) value (4, 2);
insert into app_user_roles(app_user_id, roles_id) value (5, 3);