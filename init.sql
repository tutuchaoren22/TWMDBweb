CREATE SCHEMA `movie_sys` DEFAULT CHARACTER SET utf8;
USE movie_sys;
ALTER TABLE movies change id movie_id int(11);
ALTER TABLE movies ADD id INT(5) NOT NULL
    PRIMARY KEY AUTO_INCREMENT FIRST;
ALTER TABLE movies ADD summary LongTEXT;
ALTER TABLE movies ADD durations TEXT;
ALTER TABLE movies ADD countries VARCHAR(30);

SELECT
    *
FROM
    movies;

CREATE TABLE movie_comments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id INT,
    author_name VARCHAR(50),
    rating DOUBLE,
    comment_date DATE,
    comments TEXT
)  ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

SELECT
    *
FROM
    movie_comments;

CREATE TABLE movie_categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    class_name VARCHAR(20),
    class_count INT
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

SELECT
    *
FROM
    movie_categories;