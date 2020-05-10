package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.util.helper;

import android.database.Cursor;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.UserLocal;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<UserLocal> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<UserLocal> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID));
            String username = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.USERNAME));
            notesList.add(new UserLocal(id, username));
        }

        return notesList;
    }

    public static UserLocal mapCursorToObject(Cursor notesCursor) {
        notesCursor.moveToFirst();
        int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID));
        String username = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.USERNAME));
        return new UserLocal(id, username);
    }
}
