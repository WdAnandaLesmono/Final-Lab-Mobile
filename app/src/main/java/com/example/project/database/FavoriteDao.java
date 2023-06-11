package com.example.project.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorite_movie_table")
    LiveData<List<FavoriteMovie>> getAllMovie();

    @Query("SELECT * FROM favorite_movie_table WHERE id=:id LIMIT 1")
    FavoriteMovie findMovieById(int id);

    @Query("SELECT EXISTS (SELECT * FROM favorite_movie_table WHERE id=:id)")
    boolean isMovieExist(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addFavoriteMovie(FavoriteMovie favoriteMovie);

    @Delete
    Completable deleteFavoriteMovie(FavoriteMovie favoriteMovie);



    @Query("SELECT * FROM favorite_tv_show_table")
    LiveData<List<FavoriteTvShow>> getAllTvShows();

    @Query("SELECT * FROM favorite_tv_show_table WHERE id=:id LIMIT 1")
    FavoriteTvShow findByTvShowId(int id);

    @Query("SELECT EXISTS (SELECT * FROM favorite_tv_show_table WHERE id=:id)")
    boolean isTvShowExist(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addFavoriteTvShow(FavoriteTvShow favoriteTvShow);

    @Delete()
    Completable deleteFavoriteTvShow(FavoriteTvShow favoriteTvShow);

}
