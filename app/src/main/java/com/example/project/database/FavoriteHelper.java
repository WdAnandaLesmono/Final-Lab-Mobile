package com.example.project.database;

import android.annotation.SuppressLint;
import android.content.Context;

public class FavoriteHelper {
    private final FavoriteDatabase roomDatabase;
    private boolean status;

    public FavoriteHelper(Context context) {
        roomDatabase = FavoriteDatabase.getInstance(context);
        status = false;
    }

    public boolean checkFavoriteMovie(int id) {
        return roomDatabase.favoriteDao().isMovieExist(id);
    }
    public boolean checkFavoriteTvShow(int id) {
        return roomDatabase.favoriteDao().isTvShowExist(id);
    }

    @SuppressLint("CheckResult")
    public boolean insertFavoriteMovie(int id, String title, String posterPath, String releaseDate) {
        FavoriteMovie favoriteMovie = new FavoriteMovie(id, title, posterPath, releaseDate);

        roomDatabase.favoriteDao().addFavoriteMovie(favoriteMovie).subscribe(() -> {
            status = true;
        }, throwable -> {
            status = false;
        });

        return status;
    }

    @SuppressLint("CheckResult")
    public boolean deleteFavoriteMovie(int id) {
        FavoriteMovie favoriteMovie = roomDatabase.favoriteDao().findMovieById(id);

        roomDatabase.favoriteDao().deleteFavoriteMovie(favoriteMovie).subscribe(() -> {
            status = true;
        }, throwable -> {
            status = false;
        });

        return status;
    }

    @SuppressLint("CheckResult")
    public boolean insertFavoriteTvShow(int id, String title, String posterPath, String releasedate) {
        FavoriteTvShow favoriteTvShow = new FavoriteTvShow(id, title, posterPath, releasedate);

        roomDatabase.favoriteDao().addFavoriteTvShow(favoriteTvShow).subscribe(() -> {
            status = true;
        }, throwable -> {
            status = false;
        });

        return status;
    }

    @SuppressLint("CheckResult")
    public boolean deleteFavorite(int id) {
        FavoriteMovie favoriteMovie = roomDatabase.favoriteDao().findMovieById(id);

        roomDatabase.favoriteDao().deleteFavoriteMovie(favoriteMovie).subscribe(() -> {
            status = true;
        }, throwable -> {
            status = false;
        });

        return status;
    }

    @SuppressLint("CheckResult")
    public boolean deleteFavoriteTvShow(int id) {
        FavoriteTvShow favoriteTvShow = roomDatabase.favoriteDao().findByTvShowId(id);

        roomDatabase.favoriteDao().deleteFavoriteTvShow(favoriteTvShow).subscribe(() -> {
            status = true;
        }, throwable -> {
            status = false;
        });

        return status;
    }
}
