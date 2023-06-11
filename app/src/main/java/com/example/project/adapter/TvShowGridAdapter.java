package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.example.project.adapter.onclicklistener.OnTvShowItemClickListener;
import com.example.project.api.ApiConfig;
import com.example.project.databinding.ItemGridLayoutBinding;
import com.example.project.model.TvShow;

import java.util.List;

public class TvShowGridAdapter extends RecyclerView.Adapter<TvShowGridAdapter.ViewHolder> {

    private final List<TvShow> tvShowPopulars;
    private OnTvShowItemClickListener onTvShowItemClickListener;

    public TvShowGridAdapter(List<TvShow> tvShowPopulars) {
        this.tvShowPopulars = tvShowPopulars;
    }

    public void setClickListener(OnTvShowItemClickListener onTvShowItemClickListener) {
        this.onTvShowItemClickListener = onTvShowItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindItemView(tvShowPopulars.get(position));
    }

    public void appendList(List<TvShow> lisToAppend) {
        tvShowPopulars.addAll(lisToAppend);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tvShowPopulars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemGridLayoutBinding bind;
        TvShow tvShowPopular;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bind = ItemGridLayoutBinding.bind(itemView);

            itemView.setOnClickListener(this);
        }

        public void onBindItemView(TvShow tvShowPopular) {
            this.tvShowPopular = tvShowPopular;

            Glide.with(itemView.getContext())
                    .load(ApiConfig.IMG_URL_300 + tvShowPopular.getImgUrl())
                    .into(bind.ivPoster);
            bind.tvTitle.setText(tvShowPopular.getTitle());
            bind.tvReleaseDate.setText(tvShowPopular.getReleaseDate());
        }

        @Override
        public void onClick(View v) {
            onTvShowItemClickListener.onClick(tvShowPopular);
        }
    }
}
