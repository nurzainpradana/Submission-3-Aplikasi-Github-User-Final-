package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.TABLE_FAVORITE_NAME;

public class FavoriteHelper {
    private final String DATABASE_TABLE = TABLE_FAVORITE_NAME;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    //Untuk inisisasi database
    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    //membuka dan menutup koneksi ke database
    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();

        if (database.isOpen()) {
            database.close();
        }
    }

    //CRUD
    //Ambil Data
    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    //Ambil Data dengan ID
    public Cursor queryByUsername(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    //insert data
    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    //update data
    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[] {id});
    }

    //delete data
    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
