package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.detailuser;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.R;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.detailuser.fragment.FollowersFragment;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.detailuser.fragment.FollowingFragment;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.User;

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private User user;

    SectionsPageAdapter(Context mContext, FragmentManager fm, User user) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = mContext;
        this.user = user;

    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.followers,
            R.string.following
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FollowersFragment();
                Bundle bundleFollowers = new Bundle();
                bundleFollowers.putString(FollowersFragment.EXTRA_FOLLOWERS, user.getLogin());
                fragment.setArguments(bundleFollowers);
                break;

            case 1:
                fragment = new FollowingFragment();
                Bundle bundleFollowing = new Bundle();
                bundleFollowing.putString(FollowingFragment.EXTRA_FOLLOWING, user.getLogin());
                fragment.setArguments(bundleFollowing);
                break;
        }
        assert fragment != null;
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
