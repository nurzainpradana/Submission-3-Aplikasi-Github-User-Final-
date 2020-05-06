package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.R;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api.Api;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api.ApiInterface;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.model.Result;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.model.User;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> listUsers = new MutableLiveData<>();
    private Context context;

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
}
