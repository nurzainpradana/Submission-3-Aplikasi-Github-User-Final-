package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.util.helper;

import android.database.Cursor;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.User;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.UserLocal;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<User> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<User> userList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns._ID));
            String login = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN));
            String name = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME));
            String avatar_url = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL));

            userList.add(new User(id, login, name, avatar_url));
        }

        return userList;
    }
}
