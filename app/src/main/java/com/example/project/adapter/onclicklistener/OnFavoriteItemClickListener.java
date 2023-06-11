package com.example.project.adapter.onclicklistener;

import com.example.project.database.FavoriteMovie;
import com.example.project.database.FavoriteTvShow;

public interface OnFavoriteItemClickListener {
    void onClick(FavoriteMovie favoriteMovie);
    void onClick(FavoriteTvShow favoriteTvShow);
}
