package com.dicoding.kharisazhar.cataloguemovieuiux.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dicoding.kharisazhar.cataloguemovieuiux.DetailMovieActivity;
import com.dicoding.kharisazhar.cataloguemovieuiux.R;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.MyViewHolder> {

    private Cursor list;
    private Context context;

    public AdapterFavorite(Context context) {
        this.context = context;
    }

    public void setListMovie(Cursor listMovie) {
        this.list = listMovie;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Result result = getItem(position);
        holder.tvTitle.setText(result.getTitle());
        holder.tvOverview.setText(result.getOverview());
        Log.e("DEBUG", "onBindViewHolder: "+result.getPosterPath() );
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+result.getPosterPath()).into(holder.imgMovie);
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.getCount();
    }

    private Result getItem(int position){
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Result(list);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview;
        ImageView imgMovie;
        CardView cvMovie;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            imgMovie = itemView.findViewById(R.id.iv_movie);
            cvMovie = itemView.findViewById(R.id.cv_movie);
        }
    }
}
