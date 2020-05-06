package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api.Api;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api.ApiInterface;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.model.User;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class FollowersViewModel extends ViewModel {

    private MutableLiveData<List<User>> listFollower = new MutableLiveData<>();
    private Context context;

    public MutableLiveData<List<User>> getListFollower() {
        return listFollower;
    }

    public void setListFollowers(String username, Context context) {
        ApiInterface Service;
        retrofit2.Call<List<User>> Call;

        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getListFollowers(username);
            Call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(retrofit2.Call<List<User>> call, Response<List<User>> response) {

                    Log.d("Response", "" + " " + response.body());
                    List<User> listUser;
                    listUser = response.body();
                    listFollower.postValue(listUser);
                }

                @Override
                public void onFailure(retrofit2.Call<List<User>> call, Throwable t) {
                    Log.d("Message", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
