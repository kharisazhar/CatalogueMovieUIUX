package com.dicoding.kharisazhar.cataloguemovieuiux.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.DATE;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.MovieColumns.TITLE;
import static com.dicoding.kharisazhar.cataloguemovieuiux.database.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<Result> query(){
        ArrayList<Result> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE
                ,null,
                null,
                null,
                null,
                null,
                _ID +" DESC",
                null);
        cursor.moveToFirst();
        Result result;
        if (cursor.getCount()>0){
            do {

                result = new Result();
                result.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                result.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                result.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                result.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                result.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                arrayList.add(result);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Result result){
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, result.getTitle());
        initialValues.put(DESCRIPTION, result.getOverview());
        initialValues.put(DATE, result.getReleaseDate());
        initialValues.put(POSTER_PATH, result.getPosterPath());

        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Result result){
        ContentValues args = new ContentValues();
        args.put(TITLE, result.getTitle());
        args.put(DESCRIPTION, result.getOverview());
        args.put(DATE, result.getReleaseDate());
        args.put(POSTER_PATH, result.getPosterPath());

        return database.update(DATABASE_TABLE, args,_ID + "= '" + result.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(DATABASE_TABLE,_ID+ " = '" +id+"'",null );
    }

//    Content Provider

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE, null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" =?",new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
