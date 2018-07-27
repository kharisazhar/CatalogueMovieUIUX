package com.dicoding.kharisazhar.cataloguemovieuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.kharisazhar.cataloguemovieuiux.DetailMovieActivity;
import com.dicoding.kharisazhar.cataloguemovieuiux.R;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.ModelListMovie;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyViewHolder>{

    private Context c;
    private ArrayList<Result> dataMovie;

    public AdapterMovie(Context c, ArrayList<Result> dataMovie) {
        this.c = c;
        this.dataMovie = dataMovie;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(c).inflate(R.layout.rv_movie, parent, false);
       MyViewHolder holder = new MyViewHolder(v);
       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//        holder.tvTitle.setText(dataMovie.get(position).getTitle());
        holder.tvTitle.setText(dataMovie.get(position).getTitle());
        Glide.with(c).load("https://image.tmdb.org/t/p/w500/"+dataMovie.get(position)
                .getPosterPath()).into(holder.imgMovie);

        holder.cvMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, DetailMovieActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("EXTRA_TITLE",dataMovie.get(position).getTitle());
//                intent.putExtra("EXTRA_OVERVIEW",dataMovie.get(position).getOverview());
//                intent.putExtra("EXTRA_DATE",dataMovie.get(position).getReleaseDate());
//                intent.putExtra("EXTRA_POSTER",dataMovie.get(position).getPosterPath());//cooming soon
//                intent.putExtra("EXTRA_BACKDROP", dataMovie.get(position).getBackdropPath());
                intent.putExtra(DetailMovieActivity.MOVIE_ITEM, new Gson().toJson(holder));
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataMovie.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        ImageView imgMovie;
        CardView cvMovie;

        public MyViewHolder(View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            imgMovie = itemView.findViewById(R.id.iv_movie);
            cvMovie = itemView.findViewById(R.id.cv_movie);
        }
    }

}
