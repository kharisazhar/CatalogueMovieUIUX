package com.dicoding.kharisazhar.favoritemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.kharisazhar.favoritemovie.DetailMovieActivity;
import com.dicoding.kharisazhar.favoritemovie.R;
import com.dicoding.kharisazhar.favoritemovie.model.Result;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.MyViewHolder>{

    private Cursor list;
    private Context context;
    private String PARCEL_OBJECT = "parcel_object";

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
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+result.getPosterPath()).into(holder.imgMovie);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(PARCEL_OBJECT, result);
                context.startActivity(intent);
            }
        });
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
        Button btnDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            imgMovie = itemView.findViewById(R.id.iv_movie);
            cvMovie = itemView.findViewById(R.id.cv_movie);
            btnDetail = itemView.findViewById(R.id.btn_detail);
        }
    }
}
