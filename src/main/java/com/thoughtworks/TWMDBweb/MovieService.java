package com.thoughtworks.TWMDBweb;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies(int start, int end) {
        return movieRepository.getAllMovies(start, end);
    }

    public List<MovieCategories> getAllClassifiedMovies() {
        List<String> allCategories = getAllCategories();
        List<MovieCategories> allClassifiedMovies = new ArrayList<>();
        for (String category : allCategories) {
            allClassifiedMovies.add(new MovieCategories(category, getMoviesForCategory(category, 1, 250).size()));
        }
        return allClassifiedMovies;
    }

    public List<String> getAllCategories() {
        List<Movie> allMovies = getAllMovies(1, 250);
        List<String> allCategories = new ArrayList<>();
        for (Movie movie : allMovies) {
            for (String genre : movie.getGenres().split(",")) {
                if (!allCategories.contains(genre)) {
                    allCategories.add(genre);
                }
            }
        }
        return allCategories;
    }

    public List<Movie> getMoviesForCategory(String category, int start, int end) {
        return movieRepository.getMoviesForCategory(category, start, end);
    }

    public List<Movie> searchMoviesForInput(String text) {
        return movieRepository.searchMoviesForInput(text);
    }

    //根据电影id返回单个电影详情信息
    public void getMovieForId(String id) {

    }


}
