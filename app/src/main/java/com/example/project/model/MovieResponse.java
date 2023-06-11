package com.example.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    @Expose
    private final List<Movie> populars;
    private final int page;

    public MovieResponse(List<Movie> populars, int page) {
        this.populars = populars;
        this.page = page;
    }

    public List<Movie> getPopulars() {
        return populars;
    }

    public int getPage() {
        return page;
    }
}
