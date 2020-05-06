package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("items")
    @Expose
    private List<User> mResultMember;

    public List<User> getmResultMember() {
        return mResultMember;
    }

    public void setmResultMember(List<User> mResultMember) {
        this.mResultMember = mResultMember;
    }
}
