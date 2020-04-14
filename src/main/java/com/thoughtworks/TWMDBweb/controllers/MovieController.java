package com.thoughtworks.TWMDBweb.controllers;

import com.thoughtworks.TWMDBweb.entities.Movie;
import com.thoughtworks.TWMDBweb.entities.MovieCategories;
import com.thoughtworks.TWMDBweb.services.MovieService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public List<Movie> getAllMovies(@PathParam("start") int start, @PathParam("end") int end) {
        return movieService.getAllMovies(start, end);
    }

    @GetMapping("/allcategories")
    public List<MovieCategories> getAllClassifiedMovies() {
        return movieService.getAllClassifiedMovies();
    }

    @GetMapping("/category")
    public List<Movie> getMoviesForCategory(@PathParam("category") String category, @PathParam("start") int start, @PathParam("end") int end) {
        return movieService.getMoviesForCategory(category, start, end);
    }

    @GetMapping("/search")
    public List<Movie> searchMoviesForInput(@PathParam("text") String text) {
        return movieService.searchMoviesForInput(text);
    }

    @GetMapping("/movie")
    public Movie getMovieDetailById(@PathParam("movieId") String movieId) {
        return movieService.getMovieDetailById(movieId);
    }

    @GetMapping("/update")
    public void updateTable() {
        movieService.addSummaryToTable();
        movieService.addDurations();
        movieService.updateMoviePic();
    }


}