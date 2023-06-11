package com.example.project.api.repository.callback;

import com.example.project.model.Movie;

import java.util.List;

public interface OnPopularMoviesCallback {

    void onSuccess(List<Movie> movieList, int page);
    void onFailure(String message);
}
