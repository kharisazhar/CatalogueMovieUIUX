package com.dicoding.kharisazhar.cataloguemovieuiux.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.kharisazhar.cataloguemovieuiux.R;
import com.dicoding.kharisazhar.cataloguemovieuiux.adapter.AdapterMovie;
import com.dicoding.kharisazhar.cataloguemovieuiux.connection.ApiServices;
import com.dicoding.kharisazhar.cataloguemovieuiux.connection.RetroConfig;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.ModelListMovie;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.CONTENT_URI;

public class NowPlayingFragment extends Fragment {

    ArrayList<Result> mData = new ArrayList<>();
    RecyclerView rvMovie;
    ProgressBar progressBar;
    String api_key = "da2c66905b58cbb6b972e167cd56310f";
    String whoops;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            mData = savedInstanceState.getParcelableArrayList("KEY_VALUE");
        } else {
            getData();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("KEY_VALUE", mData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        rvMovie = view.findViewById(R.id.recycler_movie_now_playing);
        progressBar = view.findViewById(R.id.progress_bar);
        whoops = String.format(getResources().getString(R.string.whoops));

        setRetainInstance(true);
        return view;
    }

    private void getData(){
        ApiServices api = RetroConfig.getApiServices();
        final Call<ModelListMovie> call = api.getNowPlaying(api_key,"en-US");
        call.enqueue(new Callback<ModelListMovie>() {
            @Override
            public void onResponse(Call<ModelListMovie> call, Response<ModelListMovie> response) {
                mData = (ArrayList<Result>) response.body().getResults();
                progressBar.setVisibility(View.GONE);

                AdapterMovie adapterMovie = new AdapterMovie(getContext(),mData);
                rvMovie.setAdapter(adapterMovie);
                rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure(Call<ModelListMovie> call, Throwable t) {
                Toast.makeText(getContext(), whoops, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
