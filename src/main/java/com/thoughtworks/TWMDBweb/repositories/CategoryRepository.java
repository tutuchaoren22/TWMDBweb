package com.thoughtworks.TWMDBweb.repositories;

import com.thoughtworks.TWMDBweb.entities.MovieCategories;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CategoryRepository extends CrudRepository<MovieCategories, Integer> {
    @Modifying
    @Query("INSERT INTO movie_categories VALUES (NULL, :name, :count)")
    void insertMovieCategory(@Param("name") String name, @Param("count") int count);

    @Query("SELECT * FROM movie_categories")
    List<MovieCategories> getAllCategories();
}
