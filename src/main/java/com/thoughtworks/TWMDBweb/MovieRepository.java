package com.thoughtworks.TWMDBweb;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;


public interface MovieRepository extends CrudRepository<Object, Integer> {

    //返回所有电影列表信息
    @Query("")
    void getAllMovies(String start, String end);

    //返回所有电影分类信息
    @Query("")
    void getAllClassifiedMovies();

    //返回给定分类下的电影信息
    @Query("")
    void getMoviesForCategory(String category, String start, String end);

    //根据用户输入搜索相关电影信息
    @Query("")
    void searchMoviesForInput(String text);

    //根据电影id返回单个电影详情信息
    @Query("")
    void getMovieForId(String id);

}
