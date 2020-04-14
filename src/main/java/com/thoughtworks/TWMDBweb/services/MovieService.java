package com.thoughtworks.TWMDBweb.services;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.TWMDBweb.entities.Movie;
import com.thoughtworks.TWMDBweb.entities.MovieCategories;
import com.thoughtworks.TWMDBweb.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class MovieService {
    String API_KEY = "?apikey=0df993c66c0c636e29ecbb5344252a4a";
    @Autowired
    private final MovieRepository movieRepository;
    @Autowired
    private RestTemplate restTemplate;

    public MovieService(MovieRepository movieRepository, RestTemplate restTemplate) {
        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
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
        return movieRepository.getMoviesForCategory(category, start - 1, end - start + 1);
    }

    public List<Movie> searchMoviesForInput(String text) {
        String[] wordList = text.split(" ");
        HashSet<Movie> searchMovies = new HashSet<>();
        for (String word : wordList) {
            searchMovies.addAll(movieRepository.searchMoviesForInput(word));
        }
        return new ArrayList<>(searchMovies);
    }

    public Movie getMovieDetailById(String movieId) {
        return movieRepository.getMovieDetailById(movieId);
    }

    public void addSummaryToTable() {
        List<Integer> idList = movieRepository.getMoviesId();
        for (int id : idList) {
            String url = "http://api.douban.com/v2/movie/subject/" + id + API_KEY;
            ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String json = results.getBody();
            JSONObject detail = JSONObject.parseObject(json);
            String summary = detail.getString("summary");
            movieRepository.insertSummary(summary, id);
        }
    }

    public void addDurations(){
        List<Integer> idList = movieRepository.getMoviesId();
        for (int id : idList) {
            String url = "http://api.douban.com/v2/movie/subject/" + id + API_KEY;
            ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String json = results.getBody();
            JSONObject detail = JSONObject.parseObject(json);
            String durations = detail.getString("durations");
            movieRepository.insertDurations(durations, id);
        }
    }

    public void updateMoviePic(){
        List<Integer> idList = movieRepository.getMoviesId();
        for (int id : idList) {
            String url = "http://api.douban.com/v2/movie/subject/" + id + API_KEY;
            ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String json = results.getBody();
            JSONObject detail = JSONObject.parseObject(json);
            JSONObject images = JSONObject.parseObject(detail.getString("images"));
            String image = images.getString("small");
            movieRepository.updatePicture(image, id);
        }
    }

}