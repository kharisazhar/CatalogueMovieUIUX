package com.dicoding.kharisazhar.cataloguemovieuiux;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract;
import com.dicoding.kharisazhar.cataloguemovieuiux.database.FavoriteHelper;
import com.dicoding.kharisazhar.cataloguemovieuiux.database.MovieHelper;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.CONTENT_URI;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.DATE;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.TITLE;

public class DetailMovieActivity extends AppCompatActivity {

    ImageView imgBackdrop,imgFab;
    TextView tvOverView, tvReleaseDate, tvTitle;
    String overView, releaseDate, imgUrl, title;
    String baseUrlImg = "https://image.tmdb.org/t/p/w500/";

    private Result item;
    private boolean isFavorite = false;
    private FavoriteHelper favoriteHelper;
    private Gson gson = new Gson();
//    private String PARCEL_LIST = "BUNDLE_FILMS";

    public static final String MOVIE_ITEM = "movie_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgBackdrop = findViewById(R.id.iv_backdrop);
        tvOverView = findViewById(R.id.tv_overview);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        imgFab = findViewById(R.id.fab);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.colorPrimary));

//        tvOverView.setText(overView);
//        tvReleaseDate.setText(releaseDate);
//        Glide.with(this).load(baseUrlImg+imgUrl).into(imgBackdrop);

        String json = getIntent().getStringExtra(MOVIE_ITEM);
        item = gson.fromJson(json, Result.class);
        init();

        imgFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite){
                    deleteFavorite();
                } else {
                    addFavorite();
                }

                isFavorite = !isFavorite;
                setFavorite();
            }
        });
    }

    private void init(){
        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();
        loadDataSQLite();
    }

    private void loadDataSQLite(){
        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + item.getId()),
                null,
                null,
                null,
                null
        );

        if (cursor != null){
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }

        setFavorite();
    }

    private void setFavorite() {
        if (isFavorite) imgFab.setImageResource(R.drawable.ic_favorite);
        else imgFab.setImageResource(R.drawable.ic_favorite);
    }

    private void addFavorite(){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.MovieColumns._ID,item.getId());
        cv.put(DatabaseContract.MovieColumns.TITLE,item.getTitle());
        cv.put(DatabaseContract.MovieColumns.DESCRIPTION, item.getOverview());
        cv.put(DatabaseContract.MovieColumns.DATE, item.getReleaseDate());

        getContentResolver().insert(CONTENT_URI, cv);
        Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
    }

    private void deleteFavorite(){
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + item.getId()),
                null,
                null
        );
    }
}
