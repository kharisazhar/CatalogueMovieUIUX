package com.dicoding.kharisazhar.cataloguemovieuiux;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
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
import com.dicoding.kharisazhar.cataloguemovieuiux.database.MovieHelper;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;

import static android.provider.BaseColumns._ID;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.CONTENT_URI;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.DATE;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.TITLE;

public class DetailMovieActivity extends AppCompatActivity {

    ImageView imgBackdrop,imgFab;
    TextView tvOverView, tvReleaseDate;
    String title;
    String baseUrlImg = "https://image.tmdb.org/t/p/w500/";

    private Result item;
    private boolean isFavorite = false;
    private MovieHelper movieHelper;

    private String PARCEL_OBJECT = "parcel_object";

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.colorPrimary));
        loadData();

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

    private void loadData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            item = bundle.getParcelable(PARCEL_OBJECT);
            loadDataSQLite();
            if (item != null){
                Glide.with(this).load(baseUrlImg + item.getPosterPath()).into(imgBackdrop);
                tvOverView.setText(item.getOverview());
                tvReleaseDate.setText(item.getReleaseDate());
            }
        }
    }

    private void loadDataSQLite(){
        movieHelper = new MovieHelper(this);
        movieHelper.open();

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
        else imgFab.setImageResource(R.drawable.ic_favorite_border);
    }

    private void addFavorite(){
        ContentValues cv = new ContentValues();
        cv.put(_ID,item.getId());
        cv.put(TITLE,item.getTitle());
        cv.put(DESCRIPTION, item.getOverview());
        cv.put(DATE, item.getReleaseDate());

//        getContentResolver().insert(CONTENT_URI, cv);
        getContentResolver().insert(CONTENT_URI, cv);
        Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
    }

    private void deleteFavorite(){
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + item.getId()),
                null,
                null
        );
        Toast.makeText(this, "Data berhasil di hapus", Toast.LENGTH_SHORT).show();
    }
}
