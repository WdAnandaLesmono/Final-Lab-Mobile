package com.example.project.api.repository.callback;

import com.example.project.model.TvShow;

public interface OnTvShowCallback {
    void onSuccess(TvShow tvShow, String message);

    void onFailure(String message);
}
