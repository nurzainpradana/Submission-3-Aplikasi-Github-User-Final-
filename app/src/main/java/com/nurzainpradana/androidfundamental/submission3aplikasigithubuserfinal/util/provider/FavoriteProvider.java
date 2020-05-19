package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.util.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.FavoriteHelper;

import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.AUTHORITY;
import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.UserColumns.CONTENT_URI;
import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.TABLE_FAVORITE_NAME;

public class FavoriteProvider extends ContentProvider {
    //untuk mengecek URI yang masuk, bersifat ALL atau ada tambahan ID nya
    private static final int FAV = 1;
    private static final int FAV_ID = 2;
    private FavoriteHelper favoriteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //content://com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_NAME, FAV);

        //content://com.dicoding.androidfundamental.submission3aplikasigithubuserfinal
        sUriMatcher.addURI(AUTHORITY,
                TABLE_FAVORITE_NAME + "/#",
                FAV_ID);
    }

    @Override
    public boolean onCreate() {
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAV:
                cursor = favoriteHelper.queryAll();
                break;

            case FAV_ID:
                cursor = favoriteHelper.queryByLogin(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAV_ID:
                deleted = favoriteHelper.deleteById(uri.getLastPathSegment());
                break;

            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case FAV:
                added = favoriteHelper.insert(values);
                break;

            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }





    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case FAV_ID:
                updated = favoriteHelper.update(uri.getLastPathSegment(), values);
                break;

            default:
                updated = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return updated;
    }
}
