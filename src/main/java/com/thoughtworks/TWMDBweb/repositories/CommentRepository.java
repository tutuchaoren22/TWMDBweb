package com.thoughtworks.TWMDBweb.repositories;

import com.thoughtworks.TWMDBweb.entities.Movie;
import com.thoughtworks.TWMDBweb.entities.MovieComments;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends CrudRepository<MovieComments, Integer> {
    @Modifying
    @Query("INSERT INTO movie_comments VALUES (NULL, :movie_id, :author_name, :rating, :comment_date, :comments)")
    void insertComments(@Param("movie_id") int id, @Param("author_name") String authorName, @Param("rating") double rating, @Param("comment_date") Date commentDate, @Param("comments") String comments);


    @Query("SELECT * FROM movie_comments WHERE movie_id=:movieId")
    List<MovieComments> getMovieCommentsById(@Param("movieId") String movieId);
}
