package com.dicoding.kharisazhar.cataloguemovieuiux.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.kharisazhar.cataloguemovieuiux.R;
import com.dicoding.kharisazhar.cataloguemovieuiux.adapter.AdapterFavorite;
import com.dicoding.kharisazhar.cataloguemovieuiux.adapter.AdapterMovie;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;

import java.util.ArrayList;

import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.CONTENT_URI;

public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    Cursor cursor;
    AdapterFavorite adapterFavorite;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
       return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rvFavoriteMovie;

        rvFavoriteMovie = view.findViewById(R.id.rv_favorite);
        rvFavoriteMovie.setHasFixedSize(true);
        rvFavoriteMovie.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterFavorite = new AdapterFavorite(getContext());
        rvFavoriteMovie.setAdapter(adapterFavorite);

        getLoaderManager().initLoader(0, new Bundle(), this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapterFavorite.setFilm(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapterFavorite.setFilm(null);
    }
}
