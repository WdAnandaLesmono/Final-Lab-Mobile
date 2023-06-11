package com.example.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {
    @SerializedName("results")
    @Expose
    private final List<TvShow> populars;

    private final int page;

    public TvShowResponse(List<TvShow> populars, int page) {
        this.populars = populars;
        this.page = page;
    }

    public List<TvShow> getPopulars() {
        return populars;
    }

    public int getPage() {
        return page;
    }
}
