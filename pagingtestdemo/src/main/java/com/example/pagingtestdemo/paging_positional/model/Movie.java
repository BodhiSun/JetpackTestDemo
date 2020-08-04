package com.example.pagingtestdemo.paging_positional.model;

public class Movie {

    public String id;

    public String title;

    public String year;

    public Images images;

    public class Images{
        public String small;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", images=" + images +
                '}';
    }
}
