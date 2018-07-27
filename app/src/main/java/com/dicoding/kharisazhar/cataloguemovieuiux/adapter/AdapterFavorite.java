package com.dicoding.kharisazhar.cataloguemovieuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.kharisazhar.cataloguemovieuiux.DetailMovieActivity;
import com.dicoding.kharisazhar.cataloguemovieuiux.R;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.MyViewHoler> {

    private Cursor list;
    private Context context;

    public AdapterFavorite(Context context) {
        this.context = context;
    }

    private Result getItem(int position){
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position INvalid");
        }
        return new Result(list);
    }

    public void setFilm(Cursor items){
        list = items;
        if (list !=null){
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_movie, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {

        final Result result = getItem(position);

        holder.tvTitle.setText(result.getTitle());

        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+result.getPosterPath()).into(holder.imgMovie);

        holder.cvMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("EXTRA_TITLE",result.getTitle());
                intent.putExtra("EXTRA_OVERVIEW",result.getOverview());
                intent.putExtra("EXTRA_DATE",result.getReleaseDate());
//                intent.putExtra("EXTRA_POSTER",dataMovie.get(position).getPosterPath());//cooming soon
                intent.putExtra("EXTRA_BACKDROP", result.getBackdropPath());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        } else {
            return list.getCount();
        }
    }

    public class MyViewHoler extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView imgMovie;
        CardView cvMovie;

        public MyViewHoler(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            imgMovie = itemView.findViewById(R.id.iv_movie);
            cvMovie = itemView.findViewById(R.id.cv_movie);
        }
    }
}
