package com.thoughtworks.TWMDBweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/api")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public void getAllMovies(@PathParam("start") String start, @PathParam("end") String end) {

    }

    @GetMapping("/allcategories")
    public void getAllClassifiedMovies() {

    }

    @GetMapping("/category")
    public void getMoviesForCategory(@PathParam("category") String category, @PathParam("start") String start, @PathParam("end") String end) {

    }

    @GetMapping("/search")
    public void searchMoviesForInput(@PathParam("text") String text) {

    }

    @GetMapping("/movie")
    public void getMovieForId(@PathParam("id") String id) {

    }
}
