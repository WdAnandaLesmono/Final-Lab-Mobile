package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.example.project.adapter.onclicklistener.OnMovieItemClickListener;
import com.example.project.api.ApiConfig;
import com.example.project.databinding.ItemGridLayoutBinding;
import com.example.project.model.Movie;

import java.util.List;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.ViewHolder> {

    private final List<Movie> moviePopulars;
    private OnMovieItemClickListener onMovieItemClickListener;

    public MovieGridAdapter(List<Movie> moviePopulars) {
        this.moviePopulars = moviePopulars;
    }

    public void SetClickListener(OnMovieItemClickListener onMovieItemClickListener) {
        this.onMovieItemClickListener = onMovieItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindItemView(moviePopulars.get(position));
    }

    public void appendToList(List<Movie> listToAppend) {
        moviePopulars.addAll(listToAppend);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return moviePopulars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemGridLayoutBinding bind;
        Movie moviePopular;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bind = ItemGridLayoutBinding.bind(itemView);

            itemView.setOnClickListener(this);
        }

        public void onBindItemView(Movie moviePopular) {
            this.moviePopular = moviePopular;

            Glide.with(itemView.getContext())
                    .load(ApiConfig.IMG_URL_300 + moviePopular.getImgUrl())
                    .into(bind.ivPoster);
            bind.tvTitle.setText(moviePopular.getTitle());
            bind.tvReleaseDate.setText(moviePopular.getReleaseDate());
        }

        @Override
        public void onClick(View v) {
            onMovieItemClickListener.onClick(moviePopular);
        }
    }
}
