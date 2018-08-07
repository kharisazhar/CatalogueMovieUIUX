package com.dicoding.kharisazhar.cataloguemovieuiux.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.kharisazhar.cataloguemovieuiux.R;
import com.dicoding.kharisazhar.cataloguemovieuiux.adapter.AdapterFavorite;
import com.dicoding.kharisazhar.cataloguemovieuiux.adapter.AdapterMovie;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;

import java.util.ArrayList;

import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.CONTENT_URI;

public class FavoriteFragment extends Fragment {

    private AdapterFavorite adapterFavorite;
    private Cursor list;
    private Context c;
    RecyclerView rvFavoriteMovie;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvFavoriteMovie = view.findViewById(R.id.rv_favorite);
        rvFavoriteMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavoriteMovie.setHasFixedSize(true);

        adapterFavorite = new AdapterFavorite(c);
        adapterFavorite.setListMovie(list);
        rvFavoriteMovie.setAdapter(adapterFavorite);
        new loadDataAsync().execute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class loadDataAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            list = cursor;
            adapterFavorite.setListMovie(list);
            adapterFavorite.notifyDataSetChanged();
//            if (list.getCount() == 0){
//                Toast.makeText(c, "Tidak ada data saat ini", Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
