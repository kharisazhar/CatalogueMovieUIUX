package com.dicoding.kharisazhar.favoritemovie;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dicoding.kharisazhar.favoritemovie.adapter.AdapterFavorite;
import static com.dicoding.kharisazhar.favoritemovie.provider.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvFavorite;
    private Cursor list;
    private AdapterFavorite adapterFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();
    }

    private void setupView(){
        adapterFavorite = new AdapterFavorite(this);
        rvFavorite = findViewById(R.id.rv_favorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));

        adapterFavorite = new AdapterFavorite(this);
        adapterFavorite.setListMovie(list);
        rvFavorite.setAdapter(adapterFavorite);
        new loadDataAsync().execute();
    }

    private class loadDataAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(
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
        }
    }
}
