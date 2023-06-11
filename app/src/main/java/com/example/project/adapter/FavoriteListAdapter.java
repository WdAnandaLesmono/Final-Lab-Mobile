package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.example.project.adapter.onclicklistener.OnFavoriteItemClickListener;
import com.example.project.api.ApiConfig;
import com.example.project.database.FavoriteMovie;
import com.example.project.database.FavoriteTvShow;
import com.example.project.databinding.ItemListLayoutBinding;

import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {
    private final List<FavoriteMovie> favoriteMovies;
    private final List<FavoriteTvShow> favoriteTvShows;
    public OnFavoriteItemClickListener onFavoriteItemClickListener;

    public FavoriteListAdapter(List<FavoriteMovie> favoriteMovies, List<FavoriteTvShow> favoriteTvShows) {
        this.favoriteMovies = favoriteMovies;
        this.favoriteTvShows = favoriteTvShows;
    }

    public void setClickListener(OnFavoriteItemClickListener onFavoriteItemClickListener) {
        this.onFavoriteItemClickListener = onFavoriteItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position < favoriteMovies.size()) {
            final FavoriteMovie movie = favoriteMovies.get(position);
            holder.onBindItemMovie(movie);
        } else {
            int tvPosition = position - favoriteMovies.size();
            final FavoriteTvShow tvShow = favoriteTvShows.get(tvPosition);
            holder.onBindItemTvShow(tvShow);

        }
    }

    @Override
    public int getItemCount() {
        return favoriteMovies.size() + favoriteTvShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemListLayoutBinding bind;
        FavoriteMovie favoriteMovie;
        FavoriteTvShow favoriteTvShow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind = ItemListLayoutBinding.bind(itemView);

            itemView.setOnClickListener(this);
        }

        public void onBindItemMovie(FavoriteMovie favoriteMovie) {
            this.favoriteMovie = favoriteMovie;

            Glide.with(itemView.getContext())
                    .load(ApiConfig.IMG_URL_300 + favoriteMovie.getPosterPath())
                    .into(bind.ivPoster);

            bind.tvTitle.setText(favoriteMovie.getTitle());
            bind.tvReleaseDate.setText(favoriteMovie.getReleaseDate());
            bind.ivType.setImageResource(R.drawable.ic_movie);
        }

        public void onBindItemTvShow(FavoriteTvShow favoriteTvShow) {
            this.favoriteTvShow = favoriteTvShow;

            Glide.with(itemView.getContext())
                    .load(ApiConfig.IMG_URL_300 + favoriteTvShow.getPosterPath())
                    .into(bind.ivPoster);

            bind.tvTitle.setText(favoriteTvShow.getTitle());
            bind.tvReleaseDate.setText(favoriteTvShow.getReleaseDate());
            bind.ivType.setImageResource(R.drawable.ic_tv);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position  != RecyclerView.NO_POSITION) {
                if (position < favoriteMovies.size()) {
                    FavoriteMovie clickedMovie = favoriteMovies.get(position);
                    if (onFavoriteItemClickListener != null) {
                        onFavoriteItemClickListener.onClick(clickedMovie);
                    }
                }else {
                    int tvPosition = position - favoriteMovies.size();
                    FavoriteTvShow clickedTvShow = favoriteTvShows.get(tvPosition);
                    if (onFavoriteItemClickListener != null) {
                        onFavoriteItemClickListener.onClick(clickedTvShow);
                    }
                }
            }
        }
    }
}
