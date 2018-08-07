package com.dicoding.kharisazhar.cataloguemovieuiux;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.kharisazhar.cataloguemovieuiux.adapter.AdapterMovie;
import com.dicoding.kharisazhar.cataloguemovieuiux.connection.ApiServices;
import com.dicoding.kharisazhar.cataloguemovieuiux.connection.RetroConfig;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.ModelListMovie;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    ArrayList<Result> mData = new ArrayList<>();
    RecyclerView rvMovie;
    ProgressBar progressBar;
    String api_key = "da2c66905b58cbb6b972e167cd56310f";
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        handleIntent(getIntent());
        getData();

        rvMovie = findViewById(R.id.recycler_movie_search);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void getData(){
        Intent intent = getIntent();
        query = intent.getStringExtra("EXTRA_QUERY");

        ApiServices api = RetroConfig.getApiServices();
        final Call<ModelListMovie> call = api.getQuery(api_key,query,"en-US");

        call.enqueue(new Callback<ModelListMovie>() {
            @Override
            public void onResponse(Call<ModelListMovie> call, Response<ModelListMovie> response) {
                mData = (ArrayList<Result>) response.body().getResults();
                progressBar.setVisibility(View.GONE);

                AdapterMovie adapterMovie = new AdapterMovie(getBaseContext(),mData);
                rvMovie.setAdapter(adapterMovie);
                rvMovie.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            }

            @Override
            public void onFailure(Call<ModelListMovie> call, Throwable t) {
                Toast.makeText(getBaseContext(), "whoops failed to load movie", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
}
