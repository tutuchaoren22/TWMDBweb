CREATE SCHEMA `movie_sys` DEFAULT CHARACTER SET utf8;
USE movie_sys;
ALTER table movies ADD PRIMARY KEY (id);
ALTER TABLE movies ADD summary longtext;

SELECT
    *
FROM
    movies;