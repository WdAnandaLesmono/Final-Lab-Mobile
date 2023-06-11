package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project.api.ApiConfig;
import com.example.project.api.repository.MovieRepository;
import com.example.project.api.repository.callback.OnMovieCallback;
import com.example.project.database.FavoriteHelper;
import com.example.project.databinding.ActivityMovieDetailBinding;
import com.example.project.model.Movie;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMovieDetailBinding bind;
    private MovieRepository movieRepository;
    private Movie movie;
    private FavoriteHelper favoriteHelper;
    private String EXTRAS_ID, EXTRAS_TITLE;
    private String favTitle, favPoster, favReleaseDate;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        getSupportActionBar().hide();

        movieRepository = MovieRepository.getInstance();
        favoriteHelper = new FavoriteHelper(this);

        EXTRAS_ID = getIntent().getStringExtra("ID");
        EXTRAS_TITLE = getIntent().getStringExtra("TITLE");



        bind.btnBack.setOnClickListener(this);
        bind.btnFavorite.setOnClickListener(this);

        updateFavoriteButton(EXTRAS_ID);
        loadMovie(EXTRAS_ID);
    }

    private void loadMovie(String movieId) {
        movieRepository.getMovie(movieId, new OnMovieCallback() {
            @Override
            public void onSuccess(Movie moviedetail, String message) {
                movie = moviedetail;

                setDetailActivityContent();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MovieDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDetailActivityContent() {
        Glide.with(MovieDetailActivity.this)
                .load(ApiConfig.IMG_URL_300 + movie.getImgUrl())
                .into(bind.ivPoster);


        Glide.with(MovieDetailActivity.this)
                .load(ApiConfig.IMG_URL_500 + movie.getBackdropUrl())
                .into(bind.ivCarousel);

        favTitle = movie.getTitle();
        favPoster = movie.getImgUrl();
        favReleaseDate = movie.getReleaseDate();

        bind.tvTitle.setText(movie.getTitle());
        bind.tvReleaseDate.setText(movie.getReleaseDate());
        bind.tvDescription.setText(movie.getDescription());
        bind.tvRate.setText(movie.getVoteAverage());
    }

    private void updateFavoriteButton(String tvId) {
        isFavorite = favoriteHelper.checkFavoriteMovie(Integer.parseInt(tvId));

        if (!isFavorite) {
            bind.btnFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            bind.btnFavorite.setImageResource(R.drawable.ic_fill_favorite);
        }
    }

    private void btnFavoriteHandler() {

        if (!isFavorite) {
            if (favoriteHelper.insertFavoriteMovie(Integer.parseInt(EXTRAS_ID), favTitle, favPoster, favReleaseDate)) {
                setResult(1);
                Toast.makeText(this, "Movie Has Been Added to Favorite.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to Add Movie. Try Again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (favoriteHelper.deleteFavoriteMovie(Integer.parseInt(EXTRAS_ID))) {
                Toast.makeText(this, "Movie Has Been Removed from Favorite", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to Remove Movie. Try Again.", Toast.LENGTH_SHORT).show();
            }
        }
        updateFavoriteButton(EXTRAS_ID);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_favorite) {
            btnFavoriteHandler();
        } else if (v.getId() == R.id.btn_back) {
            finish();
        }
    }
}