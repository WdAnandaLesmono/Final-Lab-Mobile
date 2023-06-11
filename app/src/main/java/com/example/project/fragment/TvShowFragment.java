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

import com.example.project.TvShowDetailActivity;
import com.example.project.adapter.TvShowGridAdapter;
import com.example.project.adapter.onclicklistener.OnTvShowItemClickListener;
import com.example.project.api.repository.TvShowRepository;
import com.example.project.api.repository.callback.OnPopularTvShowsCallback;
import com.example.project.databinding.FragmentTvShowsBinding;
import com.example.project.model.TvShow;

import java.util.List;

public class TvShowFragment extends Fragment implements OnTvShowItemClickListener {

    FragmentTvShowsBinding bind;
    private TvShowGridAdapter tvShowGridAdapter;
    private TvShowRepository tvShowRepository;
    private boolean responseSuccess = false;
    private int currentPage = 1;

    public TvShowFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentTvShowsBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvShowRepository =TvShowRepository.getInstance();
        getRepositoryData(currentPage);
    }

    @Override
    public void onClick(TvShow tvShowPopular) {
        Intent tvShowDetailActivity = new Intent(getContext(), TvShowDetailActivity.class);

        tvShowDetailActivity.putExtra("ID", tvShowPopular.getId());
        tvShowDetailActivity.putExtra("TITLE", tvShowPopular.getTitle());

        startActivity(tvShowDetailActivity);
    }
    private void getRepositoryData(int page) {

        tvShowRepository.getPopularTvShows(new OnPopularTvShowsCallback() {
            @Override
            public void onSuccess(List<TvShow> tvShowList, int page) {
                responseSuccess = true;
                bind.progressBar.setVisibility(View.GONE);
                bind.rvTvshow.setVisibility(View.VISIBLE);

                if (tvShowGridAdapter == null) {
                    tvShowGridAdapter = new TvShowGridAdapter(tvShowList);
                    tvShowGridAdapter.setClickListener(TvShowFragment.this);
                    tvShowGridAdapter.notifyDataSetChanged();
                    bind.rvTvshow.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    bind.rvTvshow.setAdapter(tvShowGridAdapter);
                } else {
                    tvShowGridAdapter.appendList(tvShowList);
                }

                currentPage = page;
            }

            @Override
            public void onFailure(String message) {
                if (!responseSuccess) bind.progressBar.setVisibility(View.VISIBLE);
            }
        }, page);
    }
}