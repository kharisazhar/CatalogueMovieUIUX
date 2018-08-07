package com.dicoding.kharisazhar.favoritemovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.dicoding.kharisazhar.favoritemovie.provider.DatabaseContract.TABLE_MOVIE;

public class FavoriteHelper {

    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public Cursor getsProvider() {
        return database.query(
                TABLE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC"
        );
    }

    public Cursor getByIdProvider(String id) {
        return database.query(
                TABLE_MOVIE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public long addProvider(ContentValues values) {
        return database.insert(
                TABLE_MOVIE,
                null,
                values
        );
    }

    public int editProvider(String id, ContentValues values) {
        return database.update(
                TABLE_MOVIE,
                values,
                _ID + " = ?",
                new String[]{id}
        );
    }

    public int delProvider(String id) {
        return database.delete(
                TABLE_MOVIE,
                _ID + " = ?",
                new String[]{id}
        );
    }

}
