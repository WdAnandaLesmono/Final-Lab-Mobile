package com.example.project.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMovie.class, FavoriteTvShow.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    private static FavoriteDatabase instance;

    public abstract FavoriteDao favoriteDao();

    public synchronized static FavoriteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            FavoriteDatabase.class,
                            "favorite_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}
