package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal";
    private static final String SCHEME = "content";

    public static String TABLE_FAVORITE_NAME = "favorite";

    public DatabaseContract() {
    }

    public static final class NoteColumns implements BaseColumns {

        public static String USERNAME = "username";

        //Untuk membuat URI content://com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE_NAME)
                .build();

    }
}
