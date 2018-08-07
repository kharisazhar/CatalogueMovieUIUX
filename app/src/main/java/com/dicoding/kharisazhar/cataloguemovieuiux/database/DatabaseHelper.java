package com.dicoding.kharisazhar.cataloguemovieuiux.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.TITLE;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.TABLE_MOVIE;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 14;

    public static String DATABASE_NAME = "movie_db";

    public static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)" ,
            TABLE_MOVIE,
            _ID,
            TITLE,
            DESCRIPTION,
            POSTER_PATH
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_MOVIE);
        onCreate(sqLiteDatabase);
    }
}
