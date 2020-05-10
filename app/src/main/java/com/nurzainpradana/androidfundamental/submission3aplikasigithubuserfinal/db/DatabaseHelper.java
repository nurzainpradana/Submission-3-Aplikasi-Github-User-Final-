package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbGithubUserApp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
            + "(%s INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVORITE_NAME,
            DatabaseContract.NoteColumns._ID,
            DatabaseContract.NoteColumns.USERNAME);

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITE_NAME );
        onCreate(db);
    }
}
