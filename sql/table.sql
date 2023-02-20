
-- ----------------------------
-- Table structure for artist
-- ----------------------------
DROP TABLE IF EXISTS `artist`;
CREATE TABLE artist (
  id INT(20) NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  country VARCHAR(255),
  description VARCHAR(255),
  PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for album
-- ----------------------------
DROP TABLE IF EXISTS `album`;
CREATE TABLE album (
  id INT(20) NOT NULL AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  description VARCHAR(100),
  artist_id INT(20) NOT NULL,
  genre VARCHAR(255),
  cover_image VARCHAR(255),
  type tinyint(4) NOT NULL,
  release_date DATE NOT NULL,
  platform VARCHAR(255),
  venue VARCHAR(100),
  year YEAR,
  PRIMARY KEY (id),
  FOREIGN KEY (artist_id) REFERENCES artist(id)
);

-- ----------------------------
-- Table structure for music
-- ----------------------------
DROP TABLE IF EXISTS `music`;
CREATE TABLE music (
  id INT(20) NOT NULL AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  genre VARCHAR(255),
  duration INT NOT NULL,
  artist_id INT(20) NOT NULL,
  album_id INT(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (artist_id) REFERENCES artist(id),
  FOREIGN KEY (album_id) REFERENCES album(id)
);