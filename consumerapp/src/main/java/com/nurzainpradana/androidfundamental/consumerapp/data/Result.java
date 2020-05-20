package com.nurzainpradana.androidfundamental.consumerapp.data;

import java.util.List;

public class Result {

    private List<User> mResultMember;

    public Result(List<User> mResultMember) {
        this.mResultMember = mResultMember;
    }

    public List<User> getmResultMember() {
        return mResultMember;
    }

}
