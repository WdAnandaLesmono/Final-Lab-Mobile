package com.example.project.api.repository;

import androidx.annotation.NonNull;

import com.example.project.api.ApiConfig;
import com.example.project.api.MovieApiService;
import com.example.project.api.repository.callback.OnMovieCallback;
import com.example.project.api.repository.callback.OnMovieSearchCallback;
import com.example.project.api.repository.callback.OnPopularMoviesCallback;
import com.example.project.model.Movie;
import com.example.project.model.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private static MovieRepository movieRepository;
    private final MovieApiService movieService;

    private MovieRepository(MovieApiService movieService) {
        this.movieService = movieService;
    }

    public static MovieRepository getInstance() {
        if (movieRepository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            movieRepository = new MovieRepository(retrofit.create(MovieApiService.class));
        }
        return movieRepository;
    }

    public void getPopularMovies(final OnPopularMoviesCallback callback, int page) {
        movieService.getPopularMovies(ApiConfig.API_KEY, page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
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
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public void getMovie(String movieId, final OnMovieCallback callback) {
        movieService.getMovie(movieId, ApiConfig.API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
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
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public void searchMovies(final OnMovieSearchCallback callback, String query, int page) {
        movieService.searchMovies(ApiConfig.API_KEY, query, page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
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
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }
}
