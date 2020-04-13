package com.thoughtworks.TWMDBweb;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MovieRepository extends CrudRepository<Movie, Integer> {

    @Query("SELECT * FROM movies WHERE id BETWEEN :start AND :end")
    List<Movie> getAllMovies(@Param("start") int start, @Param("end") int end);

    @Query("SELECT * FROM movies WHERE genres LIKE CONCAT('%',:category,'%') LIMIT :start,:end")
    List<Movie> getMoviesForCategory(String category, int start, int end);

    @Query("select * from movies where title LIKE CONCAT('%',:text,'%') ")
    List<Movie> searchMoviesForInput(@Param("text") String text);

/*    //根据电影id返回单个电影详情信息
    @Query("")
    void getMovieForId(String id);*/

}
