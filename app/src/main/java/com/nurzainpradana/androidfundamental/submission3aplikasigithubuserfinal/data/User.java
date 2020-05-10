package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable{
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;

    @SerializedName("repos_url")
    @Expose
    private String reposUrl;

    @SerializedName("followers")
    @Expose
    private Integer followers;

    @SerializedName("following")
    @Expose
    private Integer following;

    @SerializedName("bio")
    @Expose
    private String bio;

    @SerializedName("name")
    @Expose
    private String name;

    protected User(Parcel in) {
        login = in.readString();
        avatarUrl = in.readString();
        htmlUrl = in.readString();
        reposUrl = in.readString();
        bio = in.readString();
    }

    public User(String login, String avatarUrl, String htmlUrl, String reposUrl, Integer followers, Integer following, String bio) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
        this.reposUrl = reposUrl;
        this.followers = followers;
        this.following = following;
        this.bio = bio;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(avatarUrl);
        dest.writeString(htmlUrl);
        dest.writeString(reposUrl);
        dest.writeString(bio);
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Integer getFollowers() {
        return followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public String getBio() {
        return bio;
    }

    public String getName() {
        return name;
    }

}