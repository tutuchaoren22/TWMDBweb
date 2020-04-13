package com.thoughtworks.TWMDBweb;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.server.PathParam;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    //返回所有电影列表信息
    public void getAllMovies(String start, String end) {

    }

    //返回所有电影分类信息
    public void getAllClassifiedMovies() {

    }

    //返回给定分类下的电影信息
    public void getMoviesForCategory(String category, String start, String end) {

    }

    //根据用户输入搜索相关电影信息
    public void searchMoviesForInput(String text) {

    }

    //根据电影id返回单个电影详情信息
    public void getMovieForId(String id) {

    }


}
