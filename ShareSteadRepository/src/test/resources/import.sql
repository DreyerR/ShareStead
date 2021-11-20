insert into member (member_firstname, member_lastname, member_email, member_password) values ('Rudi', 'Dreyer', 'rudi@gmail.com', 'testpass');
insert into member (member_firstname, member_lastname, member_email, member_password) values ('Ruben', 'Dreyer', 'ruben@gmail.com', 'pass123');
insert into photo (photo_url) values ('test-img.png');
insert into photo (photo_url) values ('mountain.jpg');
insert into metadata (meta_content_type, meta_date_created, meta_img_size, meta_original_file_name, photo_id) values ('image/png', '2021-11-20', 11487, 'test-img.png', 1);
insert into metadata (meta_content_type, meta_date_created, meta_img_size, meta_original_file_name, photo_id) values ('image/jpg', '2021-11-20', 54871, 'mountain.jpg', 2);
insert into member_photo (mp_is_modifiable, owner_id, member_id, photo_id) values (0, 1, 1, 1);
insert into member_photo (mp_is_modifiable, owner_id, member_id, photo_id) values (1, 2, 2, 2);
insert into member_photo (mp_is_modifiable, owner_id, member_id, photo_id) values (0, 2, 1, 2);

