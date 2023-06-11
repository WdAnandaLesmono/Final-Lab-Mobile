package com.example.project.api.repository.callback;

import com.example.project.model.TvShow;

import java.util.List;

public interface OnPopularTvShowsCallback {
    void onSuccess(List<TvShow> tvShowList, int page);

    void onFailure(String message);
}
