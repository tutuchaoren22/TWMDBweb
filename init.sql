CREATE SCHEMA `movie_sys` DEFAULT CHARACTER SET utf8;
USE movie_sys;
ALTER TABLE movies change id movie_id int(11);
ALTER TABLE movies ADD id INT(5) NOT NULL
    PRIMARY KEY AUTO_INCREMENT FIRST;
ALTER TABLE movies ADD summary LongTEXT;
ALTER TABLE movies ADD durations TEXT ;

SELECT
    *
FROM
    movies;