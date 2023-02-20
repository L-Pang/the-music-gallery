create database music_gallery;
create user 'gallery_admin'@'%' identified by 'Bp5zwoayGvCfQGhmP87j';
grant all privileges on music_gallery.* to 'gallery_admin'@'%';
flush privileges;
use music_gallery;