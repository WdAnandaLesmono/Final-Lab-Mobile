package com.example.project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.MovieDetailActivity;
import com.example.project.adapter.MovieGridAdapter;
import com.example.project.adapter.onclicklistener.OnMovieItemClickListener;
import com.example.project.api.repository.MovieRepository;
import com.example.project.api.repository.callback.OnPopularMoviesCallback;
import com.example.project.databinding.FragmentMoviesBinding;
import com.example.project.model.Movie;

import java.util.List;

public class MovieFragment extends Fragment implements OnMovieItemClickListener {

    private FragmentMoviesBinding bind;
    private MovieRepository movieRepository;
    private MovieGridAdapter movieGridAdapter;
    private int currentPage = 1;

    public MovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentMoviesBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieRepository = MovieRepository.getInstance();
        getRepositoryData(currentPage);
    }

    @Override
    public void onClick(Movie moviePopular) {
        Intent movieDetailActivity = new Intent(getContext(), MovieDetailActivity.class);
        movieDetailActivity.putExtra("ID", moviePopular.getId());
        movieDetailActivity.putExtra("TITLE", moviePopular.getTitle());

        startActivity(movieDetailActivity);
    }

    private void getRepositoryData(int page) {

            movieRepository.getPopularMovies(new OnPopularMoviesCallback() {
                @Override
                public void onSuccess(List<Movie> movieList, int page) {
                    bind.progressBar.setVisibility(View.GONE);
                    bind.rvMovie.setVisibility(View.VISIBLE);

                    if (movieGridAdapter == null) {
                        movieGridAdapter = new MovieGridAdapter(movieList);
                        movieGridAdapter.SetClickListener(MovieFragment.this);
                        movieGridAdapter.notifyDataSetChanged();
                        bind.rvMovie.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        bind.rvMovie.setAdapter(movieGridAdapter);
                    } else {
                        movieGridAdapter.appendToList(movieList);
                    }

                    currentPage = page;
                }

                @Override
                public void onFailure(String message) {

                }
            }, page);
    }
}