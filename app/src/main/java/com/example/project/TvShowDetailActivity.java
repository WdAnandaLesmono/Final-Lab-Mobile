package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project.api.ApiConfig;
import com.example.project.api.repository.TvShowRepository;
import com.example.project.api.repository.callback.OnTvShowCallback;
import com.example.project.database.FavoriteHelper;
import com.example.project.databinding.ActivityTvShowDetailBinding;
import com.example.project.model.TvShow;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityTvShowDetailBinding bind;
    private TvShowRepository tvShowRepository;
    private TvShow tvShow;
    private FavoriteHelper favoriteHelper;
    private String EXTRAS_ID, EXTRAS_TITLE;
    private String favTitle, favPoster, favReleasedate;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityTvShowDetailBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        getSupportActionBar().hide();

        tvShowRepository = TvShowRepository.getInstance();
        favoriteHelper = new FavoriteHelper(this);


        EXTRAS_ID = getIntent().getStringExtra("ID");
        EXTRAS_TITLE = getIntent().getStringExtra("TITLE");

        bind.btnBack.setOnClickListener(this);
        bind.btnFavorite.setOnClickListener(this);

        updateFavoriteButton(EXTRAS_ID);
        loadTvShow(EXTRAS_ID);
    }

    private void loadTvShow(String tvId) {
        tvShowRepository.getTvShow(tvId, new OnTvShowCallback() {
            @Override
            public void onSuccess(TvShow tvShowDetail, String message) {
                tvShow = tvShowDetail;

                setDetailActivityContent();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(TvShowDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDetailActivityContent() {
        Glide.with(TvShowDetailActivity.this)
                .load(ApiConfig.IMG_URL_300 + tvShow.getImgUrl())
                .into(bind.ivPoster);

        Glide.with(TvShowDetailActivity.this)
                .load(ApiConfig.IMG_URL_500 + tvShow.getBackdropUrl())
                .into(bind.ivCarousel);

        favTitle = tvShow.getTitle();
        favPoster = tvShow.getImgUrl();
        favReleasedate = tvShow.getReleaseDate();

        bind.tvTitle.setText(tvShow.getTitle());
        bind.tvReleaseDate.setText(tvShow.getReleaseDate());
        bind.tvDescription.setText(tvShow.getDescription());
        bind.tvRate.setText(tvShow.getVoteAverage());
    }

    private void updateFavoriteButton(String tvId) {
        isFavorite = favoriteHelper.checkFavoriteTvShow(Integer.parseInt(tvId));

        if (!isFavorite) {
            bind.btnFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            bind.btnFavorite.setImageResource(R.drawable.ic_fill_favorite);
        }
    }

    private void btnFavoriteHandler() {

        if (!isFavorite) {
            if (favoriteHelper.insertFavoriteTvShow(Integer.parseInt(EXTRAS_ID), favTitle, favPoster, favReleasedate)) {
                setResult(0);
                Toast.makeText(this, "Tv Show Has Been Added to Favorite.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to Add Tv Show. Try Again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (favoriteHelper.deleteFavoriteTvShow(Integer.parseInt(EXTRAS_ID))) {
                Toast.makeText(this, "Tv Show Has Been Removed from Favorite", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to Remove Tv Show. Try Again.", Toast.LENGTH_SHORT).show();
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