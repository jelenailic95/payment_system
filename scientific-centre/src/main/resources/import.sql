
insert into user (id,username, password, email, first_name, last_name, city, zip_code, country, role) values
(1,'admin', 'root', 'jeca@gamil.com', 'Jelena', 'Ilic', 'Novi Sad', 21000, 'Serbia', 'GUEST');
insert into user (id,username, password, email, first_name, last_name, city, zip_code, country, role) values
(2,'david', '123', 'david@gamil.com', 'David', 'Vuletas', 'Beograd', 11000, 'Serbia', 'GUEST');
insert into user (id,username, password, email, first_name, last_name, city, zip_code, country, role) values
(3,'milica', '123', 'milica@gamil.com', 'Milica', 'Ilic', 'Beograd', 11000, 'Serbia', 'AUTHOR');
insert into user (id,username, password, email, first_name, last_name, city, zip_code, country, role) values
(4,'marija', '123', 'marija@gamil.com', 'Marija', 'Kovacevic', 'Nis', 18000, 'Serbia', 'AUTHOR');


insert into journal (issn_number, name, open_access, price, period) values ('N1', 'Laguna', true ,1, 12);
insert into journal (issn_number, name, open_access,  price, period) values ('N2', 'National Geography', false, 1,2 );

insert into paper (title, key_words, abstrect, context, accepted, price, journal_id) values
('Paper1', 'medicine, biomedicine', 'Abstract1', 'path1', false , 1, 1);
insert into paper (title, key_words, abstrect, context, accepted, price, journal_id) values
('Paper2', 'hidraulic', 'Abstract2', 'path2', false , 1, 1);
insert into paper (title, key_words, abstrect, context, accepted, price, journal_id) values
('Paper3', 'urbanism', 'Abstract3', 'path3', false , 1, 2);











