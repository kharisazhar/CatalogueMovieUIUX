package com.dicoding.kharisazhar.favoritemovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.dicoding.kharisazhar.favoritemovie.provider.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.dicoding.kharisazhar.favoritemovie.provider.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.dicoding.kharisazhar.favoritemovie.provider.DatabaseContract.MovieColumns.TITLE;
import static com.dicoding.kharisazhar.favoritemovie.provider.DatabaseContract.TABLE_MOVIE;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "catalogue_movie";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = String.format("CREATE TABLE %s"
                        + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " %s TEXT NOT NULL," +
                        " %s TEXT NOT NULL," +
                        " %s TEXT NOT NULL)",
                TABLE_MOVIE,
                _ID,
                TITLE,
                DESCRIPTION,
                POSTER_PATH
        );

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
