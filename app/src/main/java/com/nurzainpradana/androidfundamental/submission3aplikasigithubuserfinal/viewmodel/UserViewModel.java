package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.viewmodel;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.R;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api.Api;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api.ApiInterface;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.Result;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.User;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.UserLocal;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.util.helper.MappingHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.NoteColumns.CONTENT_URI;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> listUsers = new MutableLiveData<>();
    private MutableLiveData<List<UserLocal>> listUserDataLocal = new MutableLiveData<>();
    private MutableLiveData<User> mUserDataApi = new MutableLiveData<>();

    private User user;
    private Context context;
    private Uri uriWithString;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setListUsers(MutableLiveData<List<User>> listUsers) {
        this.listUsers = listUsers;
    }

    public MutableLiveData<List<User>> getListUsers() {
        return listUsers;
    }

    public void setListUsers(String username, Context context) {
        ApiInterface Service;
        retrofit2.Call<Result> Call;

        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getListUser(username);
            Call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(retrofit2.Call<Result> call, Response<Result> response) {
                    Log.d("Response", "" + " " + response.body());
                    List<User> listUser;
                    listUser = response.body().getmResultMember();
                    listUsers.postValue(listUser);
                    if (listUser.isEmpty()) {
                        Toast.makeText(context, R.string.not_found, Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(retrofit2.Call<Result> call, Throwable t) {
                    Log.d("Message", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<UserLocal>> getUserLocal() {
        return listUserDataLocal;

    }

    public void setUserLocal(String username, ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            ArrayList<UserLocal> userLocal = MappingHelper.mapCursorToArrayList(cursor);
            cursor.close();
            listUserDataLocal.postValue(userLocal);
        }
    }

    public MutableLiveData<User> getmUserDataApi() {
        return mUserDataApi;
    }

    public void setmUserDataApi(String username, ContentResolver contentResolver) {
        ApiInterface Service;
        retrofit2.Call<User> Call;
        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getDetailUser(username);
            Call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                    user = response.body();
                    mUserDataApi.postValue(user);
                }

                @Override
                public void onFailure(retrofit2.Call<User> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
