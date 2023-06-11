package com.example.project.api.repository.callback;

import com.example.project.model.TvShow;

import java.util.List;

public interface OnTvShowSearchCallback {
    void onSuccess(List<TvShow> tvShowList, int page, String message);

    void onFailure(String message);
}
