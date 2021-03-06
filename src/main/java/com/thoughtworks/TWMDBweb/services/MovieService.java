package com.thoughtworks.TWMDBweb.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.TWMDBweb.entities.Movie;
import com.thoughtworks.TWMDBweb.entities.MovieCategories;
import com.thoughtworks.TWMDBweb.entities.MovieComments;
import com.thoughtworks.TWMDBweb.repositories.CategoryRepository;
import com.thoughtworks.TWMDBweb.repositories.CommentRepository;
import com.thoughtworks.TWMDBweb.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class MovieService {
    String API_KEY = "apikey=0df993c66c0c636e29ecbb5344252a4a";
    @Autowired
    private final MovieRepository movieRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private RestTemplate restTemplate;

    public MovieService(MovieRepository movieRepository, CommentRepository commentRepository, CategoryRepository categoryRepository, RestTemplate restTemplate) {
        this.movieRepository = movieRepository;
        this.commentRepository = commentRepository;
        this.categoryRepository = categoryRepository;
        this.restTemplate = restTemplate;
    }

    public List<Movie> getAllMovies(int start, int end) {
        return movieRepository.getAllMovies(start, end);
    }


    public List<MovieCategories> getAllCategoriesFromDb() {
        return categoryRepository.getAllCategories();
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

    public List<MovieComments> getMovieCommentsById(String movieId) {
        return commentRepository.getMovieCommentsById(movieId);
    }

    public void addSummaryToTable() {
        List<Integer> idList = movieRepository.getMoviesId();
        for (int id : idList) {
            String url = "http://api.douban.com/v2/movie/subject/" + id + "?" + API_KEY;
            ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String json = results.getBody();
            JSONObject detail = JSONObject.parseObject(json);
            String summary = detail.getString("summary");
            movieRepository.insertSummary(summary, id);
        }
    }

    public void addDurations() {
        List<Integer> idList = movieRepository.getMoviesId();
        for (int id : idList) {
            String url = "http://api.douban.com/v2/movie/subject/" + id + "?" + API_KEY;
            ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String json = results.getBody();
            JSONObject detail = JSONObject.parseObject(json);
            String durations = detail.getString("durations");
            movieRepository.insertDurations(durations, id);
        }
    }

    public void addCountries() {
        List<Integer> idList = movieRepository.getMoviesId();
        for (int id : idList) {
            String url = "http://api.douban.com/v2/movie/subject/" + id + "?" + API_KEY;
            ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String json = results.getBody();
            JSONObject detail = JSONObject.parseObject(json);
            String countries = detail.getString("countries");
            movieRepository.insertCountries(countries, id);
        }
    }

    public void updateMoviePic() {
        List<Integer> idList = movieRepository.getMoviesId();
        for (int id : idList) {
            String url = "http://api.douban.com/v2/movie/subject/" + id + "?" + API_KEY;
            ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String json = results.getBody();
            JSONObject detail = JSONObject.parseObject(json);
            JSONObject images = JSONObject.parseObject(detail.getString("images"));
            String image = images.getString("small");
            movieRepository.updatePicture(image, id);
        }
    }

    public void addCommentsToTable() throws ParseException {
        List<Integer> idList = movieRepository.getMoviesId();
        for (int id : idList) {
            String urlComments = "http://api.douban.com/v2/movie/subject/" + id + "/comments?start=1&count=5&" + API_KEY;
            ResponseEntity<String> commentsRes = restTemplate.exchange(urlComments, HttpMethod.GET, null, String.class);
            JSONObject commentJson = JSONObject.parseObject(commentsRes.getBody());
            JSONArray commentsArr = JSONArray.parseArray(commentJson.getString("comments"));
            JSONObject obj;
            for (int i = 0; i < commentsArr.size(); i++) {
                obj = commentsArr.getJSONObject(i);
                JSONObject author = JSONObject.parseObject(obj.getString("author"));
                String authorName = author.getString("name");
                JSONObject ratings = JSONObject.parseObject(obj.getString("rating"));
                double rating = Double.parseDouble(ratings.getString("value"));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date commentDate = format.parse(obj.getString("created_at"));
                String comments = obj.getString("content");
                commentRepository.insertComments(id, authorName, rating, commentDate, comments);
            }
        }
    }

    public void addMovieCategories() {
        List<MovieCategories> allClassifiedMovies = getAllMovieCategories();
        for (MovieCategories movieCategories : allClassifiedMovies) {
            categoryRepository.insertMovieCategory(movieCategories.getName(), movieCategories.getCount());
        }
    }

    public List<MovieCategories> getAllMovieCategories() {
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


}
