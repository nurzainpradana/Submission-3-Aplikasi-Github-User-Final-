package com.nurzainpradana.androidfundamental.consumerapp.viewmodel;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurzainpradana.androidfundamental.consumerapp.data.User;
import com.nurzainpradana.androidfundamental.consumerapp.util.helper.MappingHelper;

import java.util.ArrayList;
import java.util.List;

import static com.nurzainpradana.androidfundamental.consumerapp.db.DatabaseContract.UserColumns.CONTENT_URI;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> listUserDataLocal = new MutableLiveData<>();

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<User>> getUserLocal() {
        return listUserDataLocal;

    }

    public void setUserLocal(String username, ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            ArrayList<User> userLocal = MappingHelper.mapCursorToArrayList(cursor);
            cursor.close();
            listUserDataLocal.postValue(userLocal);
        }
    }
}
