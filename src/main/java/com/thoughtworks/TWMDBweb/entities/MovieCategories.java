package com.thoughtworks.TWMDBweb.entities;

public class MovieCategories {
    private String name;
    private int count;

    public MovieCategories() {
    }

    public MovieCategories(String name, int count) {
        this.name = name;
        this.count = count;
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

    @Override
    public String toString() {
        return "MovieCategories{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
