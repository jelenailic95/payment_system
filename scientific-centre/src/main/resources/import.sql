
insert into user (id,username, password, email, first_name, last_name, city, zip_code, country, role) values
(1,'admin', 'root', 'jeca@gamil.com', 'Jelena', 'Ilic', 'Novi Sad', 21000, 'Serbia', 'GUEST');
insert into user (id,username, password, email, first_name, last_name, city, zip_code, country, role) values
(2,'david', '123', 'david@gamil.com', 'David', 'Vuletas', 'Beograd', 11000, 'Serbia', 'GUEST');
--author
insert into user (id,username, password, email, first_name, last_name, city, zip_code, country, role) values
(3,'milica', '123', 'milica@gamil.com', 'Milica', 'Ilic', 'Beograd', 11000, 'Serbia', 'AUTHOR');
insert into user (id,username, password, email, first_name, last_name, city, zip_code, country, role) values
(4,'marija', '123', 'marija@gamil.com', 'Marija', 'Kovacevic', 'Nis', 18000, 'Serbia', 'AUTHOR');

-- company with 2 journals

insert into user (id,username, password, email, first_name, last_name, city, zip_code, country, role) values
(5,'Company1', '123', 'company1@gamil.com', 'Laguna', 'Lagunic', 'Nis', 18000, 'Serbia', 'COMPANY');

insert into journal (issn_number, name, open_access, price, period) values ('N1', 'Laguna', true ,1, 12);
insert into journal (issn_number, name, open_access,  price, period) values ('N2', 'National Geography', false, 1,2 );

insert into user_my_journals (user_id, my_journals_id) values (5,1);
insert into user_my_journals (user_id, my_journals_id) values (5,2);

-- company with 1 journal

insert into user (id,username, password, email, first_name, last_name, city, zip_code, country, role) values
(6,'Company2', '123', 'company2@gamil.com', 'Kompanija', 'Neka', 'Novi Sad', 21000, 'Serbia', 'COMPANY');

insert into journal (issn_number, name, open_access, price, period) values ('N3', 'Science Mag', false , 1, 12);
insert into user_my_journals (user_id, my_journals_id) values (6,3);

insert into paid_journal (activity_date, journal_id) values ('2020-02-08 10:15:01', 1);
insert into user_journals (user_id, journals_id) values (2, 1);
-- papers

insert into paper (title, key_words, abstrect, context, accepted, price, journal_id) values
('Paper1', 'medicine, biomedicine', 'Abstract1', 'path1', false , 1, 1);
insert into paper (title, key_words, abstrect, context, accepted, price, journal_id) values
('Paper2', 'hidraulic', 'Abstract2', 'path2', false , 1, 1);
insert into paper (title, key_words, abstrect, context, accepted, price, journal_id) values
('Paper3', 'urbanism', 'Abstract3', 'path3', false , 1, 2);











