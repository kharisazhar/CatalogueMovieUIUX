package com.dicoding.kharisazhar.cataloguemovieuiux.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns{
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String DATE = "date";
    }

    public static final String AUTHORITY = "com.dicoding.kharisazhar.cataloguemovieuiux";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
