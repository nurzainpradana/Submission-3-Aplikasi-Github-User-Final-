package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.model;

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
    }

    public User(String login, String avatarUrl, String htmlUrl, String reposUrl, Integer followers, Integer following) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
        this.reposUrl = reposUrl;
        this.followers = followers;
        this.following = following;
    }

    public User() {
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
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}