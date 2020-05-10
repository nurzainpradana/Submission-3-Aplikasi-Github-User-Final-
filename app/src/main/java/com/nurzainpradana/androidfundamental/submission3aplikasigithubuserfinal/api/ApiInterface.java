package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.Result;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/search/users")
    Call<Result> getListUser(@Query("q") String username);

    @GET("/users/{username}")
    Call<User> getDetailUser(@Path("username") String username);

    @GET("/users/{username}/followers")
    Call<List<User>> getListFollowers(@Path("username") String username);

    @GET("/users/{username}/following")
    Call<List<User>> getListFollowing(@Path("username") String username);




}
