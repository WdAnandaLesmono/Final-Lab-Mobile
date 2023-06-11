package com.example.project.api.repository;

import androidx.annotation.NonNull;

import com.example.project.api.ApiConfig;
import com.example.project.api.TvShowApiService;
import com.example.project.api.repository.callback.OnPopularTvShowsCallback;
import com.example.project.api.repository.callback.OnTvShowCallback;
import com.example.project.api.repository.callback.OnTvShowSearchCallback;
import com.example.project.model.TvShow;
import com.example.project.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowRepository {
    private static TvShowRepository tvShowRepository;
    private final TvShowApiService tvShowService;

    private TvShowRepository(TvShowApiService tvShowService) {
        this.tvShowService = tvShowService;
    }

    public static TvShowRepository getInstance() {
        if (tvShowRepository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            tvShowRepository = new TvShowRepository(retrofit.create(TvShowApiService.class));
        }

        return tvShowRepository;
    }

    public void getPopularTvShows(final OnPopularTvShowsCallback callback, int page) {
        tvShowService.getPopularTvShows(ApiConfig.API_KEY, page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getPopulars() != null) {
                            callback.onSuccess(response.body().getPopulars(), response.body().getPage());
                        } else {
                            callback.onFailure(response.message());
                        }
                    } else {
                        callback.onFailure(response.message());
                    }
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public void getTvShow(String tvId, final OnTvShowCallback callback) {
        tvShowService.getTvShow(tvId, ApiConfig.API_KEY).enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(@NonNull Call<TvShow> call, @NonNull Response<TvShow> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body(), response.message());
                    } else {
                        callback.onFailure(response.message());
                    }
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShow> call, @NonNull Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public void searchTvShows(final OnTvShowSearchCallback callback, String query, int page) {
        tvShowService.searchTvShows(ApiConfig.API_KEY, query, page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getPopulars() != null) {
                            callback.onSuccess(response.body().getPopulars(), response.body().getPage(), response.message());
                        } else {
                            callback.onFailure(response.message());
                        }
                    } else {
                        callback.onFailure(response.message());
                    }
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }
}
