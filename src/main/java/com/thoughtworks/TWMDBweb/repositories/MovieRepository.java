package com.thoughtworks.TWMDBweb.repositories;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.xdevapi.JsonArray;
import com.thoughtworks.TWMDBweb.entities.Movie;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    @Query("SELECT * FROM movies WHERE id BETWEEN :start AND :end")
    List<Movie> getAllMovies(@Param("start") int start, @Param("end") int end);

    @Query("SELECT * FROM movies WHERE genres LIKE CONCAT('%',:category,'%') LIMIT :start,:len")
    List<Movie> getMoviesForCategory(@Param("category") String category, @Param("start") int start, @Param("len") int len);

    @Query("select * from movies where title LIKE CONCAT('%',:text,'%') ")
    List<Movie> searchMoviesForInput(@Param("text") String text);

    @Query("SELECT * FROM movies WHERE movie_id=:movieId")
    Movie getMovieDetailById(@Param("movieId") String movieId);

    @Query("SELECT movie_id FROM movies")
    List<Integer> getMoviesId();

    @Modifying
    @Query("UPDATE movies SET summary=:summary WHERE movie_id=:id")
    void insertSummary(@Param("summary") String summary, @Param("id") Integer id);

    @Modifying
    @Query("UPDATE movies SET durations=:durations WHERE movie_id=:id")
    void insertDurations(@Param("durations") String durations, @Param("id") Integer id);

    @Modifying
    @Query("UPDATE movies SET countries=:countries WHERE movie_id=:id")
    void insertCountries(@Param("countries") String countries, @Param("id") Integer id);

    @Modifying
    @Query("UPDATE movies SET image=:image WHERE movie_id=:id")
    void updatePicture(@Param("image") String image, @Param("id") Integer id);
}
