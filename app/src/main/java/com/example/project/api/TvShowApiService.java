package com.example.project.api;

import com.example.project.model.TvShow;
import com.example.project.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvShowApiService {
    @GET("tv/popular")
    Call<TvShowResponse> getPopularTvShows(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("tv/{tv_id}")
    Call<TvShow> getTvShow(
            @Path("tv_id") String id,
            @Query("api_key") String apiKey
    );

    @GET("search/tv")
    Call<TvShowResponse> searchTvShows(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );
}
