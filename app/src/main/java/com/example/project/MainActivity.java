package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.MenuItem;

import com.example.project.databinding.ActivityMainBinding;
import com.example.project.fragment.FavoriteFragment;
import com.example.project.fragment.MovieFragment;
import com.example.project.fragment.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.navigation.setOnNavigationItemSelectedListener(this);
        setSelectedItem(bind.navigation);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        assert getSupportActionBar() != null;
        switch (item.getItemId()) {
            case R.id.menu_item_movie:
                setActionBar("Movie");
                selectedFragment = new MovieFragment();
                break;
            case R.id.menu_item_tv_show:
                setActionBar("Tv Show");
                selectedFragment = new TvShowFragment();
                break;
            case R.id.menu_item_favorite:
                setActionBar("Favorite");
                selectedFragment = new FavoriteFragment();
                break;
        }
        assert selectedFragment != null;
        startFragment(selectedFragment);

        return true;
    }

    private void setSelectedItem(BottomNavigationView bnvMain) {
        if (getIntent().getStringExtra("SELECTED_FRAGMENT") != null) {
            switch (getIntent().getStringExtra("SELECTED_FRAGMENT")) {
                case "MOVIE":
                    bnvMain.setSelectedItemId(R.id.menu_item_movie);
                    break;
                case "TV_SHOW":
                    bnvMain.setSelectedItemId(R.id.menu_item_tv_show);
                    break;
                case "FAVORITE":
                    bnvMain.setSelectedItemId(R.id.menu_item_favorite);
                    break;
            }
        } else {
           bnvMain.setSelectedItemId(R.id.menu_item_movie);
        }
    }

    private void setActionBar (String title){
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#453B3B")));
    }

    private void startFragment(Fragment selectedFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, selectedFragment)
                .commit();
    }
}