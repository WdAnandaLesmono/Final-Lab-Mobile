package com.example.project.api.repository.callback;

import com.example.project.model.Movie;

public interface OnMovieCallback {
    void onSuccess(Movie moviePopular, String message);

    void onFailure(String message);
}
