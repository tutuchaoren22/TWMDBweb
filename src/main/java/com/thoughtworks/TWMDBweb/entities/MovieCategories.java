package com.thoughtworks.TWMDBweb.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class MovieCategories {
    @Id
    private int id;
    @Column("class_name")
    private String name;
    @Column("class_count")
    private int count;

    public MovieCategories() {
    }

    public MovieCategories(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
