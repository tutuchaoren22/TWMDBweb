package com.thoughtworks.TWMDBweb.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("movie_comments")
public class MovieComments {
    @Id
    private int id;
    @Column("movie_id")
    private int movieId;
    @Column("author_name")
    private String authorName;
    private double rating;
    @Column("comment_date")
    private Date commentDate;

    public MovieComments() {
    }

    public MovieComments(int id, int movieId, String authorName, double rating, Date commentDate) {
        this.id = id;
        this.movieId = movieId;
        this.authorName = authorName;
        this.rating = rating;
        this.commentDate = commentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }
}
