INSERT INTO music_gallery.artist (id, name, country, description) VALUES(1, 'Adele', 'UK', 'A famous singer');
INSERT INTO music_gallery.artist (id, name, country, description) VALUES(2, 'Bruno Mars', 'USA', 'A famous singer');
INSERT INTO music_gallery.artist (id, name, country, description) VALUES(3, 'The Beatles', 'UK', 'An English rock band, formed in Liverpool in 1960');

INSERT INTO music_gallery.album (id, title, description, artist_id, genre, cover_image, `type`, release_date, platform, venue, `year`) VALUES(1, '25', '25 is the third studio album by English singer-songwriter Adele', 1, 'Pop', '', 1, '2015-11-19', '', '', NULL);
INSERT INTO music_gallery.album (id, title, description, artist_id, genre, cover_image, `type`, release_date, platform, venue, `year`) VALUES(2, 'Doo-Wops & Hooligans', 'Doo-Wops & Hooligans is the debut studio album by American singer-songwriter Bruno Mars.', 2, 'Pop', '', 1, '2010-10-03', '', '', NULL);
INSERT INTO music_gallery.album (id, title, description, artist_id, genre, cover_image, `type`, release_date, platform, venue, `year`) VALUES(3, '24K Magic', '24K Magic is the third studio album by American singer-songwriter Bruno Mars.', 2, 'Pop', '', 2, '2016-11-17', 'Apple Music', '', NULL);
INSERT INTO music_gallery.album (id, title, description, artist_id, genre, cover_image, `type`, release_date, platform, venue, `year`) VALUES(4, 'The Beatles at the Hollywood Bowl', 'The Beatles at the Hollywood Bowl is a live album by the Beatles', 3, 'Rhythm and blues, rock and roll', '', 3, '1977-05-03', '', 'New York, USA', 1965);

INSERT INTO music_gallery.music (id, title, genre, duration, artist_id, album_id) VALUES(1, 'Hello', 'Pop', 300, 1, 1);
INSERT INTO music_gallery.music (id, title, genre, duration, artist_id, album_id) VALUES(2, '24K Magic', 'Pop', 360, 2, 3);
INSERT INTO music_gallery.music (id, title, genre, duration, artist_id, album_id) VALUES(3, 'Boys', 'Pop rock', 200, 3, 4);
