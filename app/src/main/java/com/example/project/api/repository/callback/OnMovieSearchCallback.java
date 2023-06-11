package com.example.project.api.repository.callback;

import com.example.project.model.Movie;

import java.util.List;

public interface OnMovieSearchCallback {
    void onSuccess(List<Movie> movieList, int page, String message);

    void onFailure(String message);
}
