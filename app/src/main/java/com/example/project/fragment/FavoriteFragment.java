package com.example.project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.MovieDetailActivity;
import com.example.project.TvShowDetailActivity;
import com.example.project.adapter.FavoriteListAdapter;
import com.example.project.adapter.onclicklistener.OnFavoriteItemClickListener;
import com.example.project.database.FavoriteMovie;
import com.example.project.database.FavoriteDatabase;
import com.example.project.database.FavoriteHelper;
import com.example.project.database.FavoriteTvShow;
import com.example.project.databinding.FragmentFavoritesBinding;
import com.example.project.model.Movie;
import com.example.project.model.TvShow;

import java.util.List;

public class FavoriteFragment extends Fragment implements OnFavoriteItemClickListener {
    private FragmentFavoritesBinding bind;
    private FavoriteListAdapter listAdapter;
    private FavoriteDatabase favoriteDatabase;
    private FavoriteHelper favoriteHelper;

    public FavoriteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentFavoritesBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        favoriteHelper = new FavoriteHelper(requireActivity().getApplicationContext());
        favoriteDatabase = FavoriteDatabase.getInstance(requireActivity().getApplicationContext());

        bind.rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadData();
    }

    private void loadData() {
        favoriteDatabase.favoriteDao().getAllMovie().observe(getViewLifecycleOwner(), new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                favoriteDatabase.favoriteDao().getAllTvShows().observe(getViewLifecycleOwner(), new Observer<List<FavoriteTvShow>>() {
                    @Override
                    public void onChanged(List<FavoriteTvShow> favoriteTvShows) {
                        bind.progressBar.setVisibility(View.GONE);
                        listAdapter = new FavoriteListAdapter(favoriteMovies, favoriteTvShows);
                        listAdapter.setClickListener(FavoriteFragment.this);
                        listAdapter.notifyDataSetChanged();
                        bind.rvFavorite.setAdapter(listAdapter);

                        if (favoriteMovies.size() == 0 && favoriteTvShows.size() == 0) {
                            bind.tvAlert.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(FavoriteMovie favoriteMovie) {
        Intent movieDetailActivity = new Intent(getContext(), MovieDetailActivity.class);

        movieDetailActivity.putExtra("ID", String.valueOf(favoriteMovie.getId()));
        movieDetailActivity.putExtra("TITLE", favoriteMovie.getTitle());

        startActivity(movieDetailActivity);
    }

    @Override
    public void onClick(FavoriteTvShow favoriteTvShow) {
        Intent tvShowDetailActivity = new Intent(getContext(), TvShowDetailActivity.class);

        tvShowDetailActivity.putExtra("ID", String.valueOf(favoriteTvShow.getId()));
        tvShowDetailActivity.putExtra("TITLE", favoriteTvShow.getTitle());

        startActivity(tvShowDetailActivity);
    }
}