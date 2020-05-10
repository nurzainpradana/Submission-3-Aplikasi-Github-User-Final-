package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.detailuser.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.R;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.detailuser.DetailUserActivity;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.searchuser.UserAdapter;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.User;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.viewmodel.FollowingViewModel;

import java.util.List;

public class FollowingFragment extends Fragment {

    private RecyclerView rvFollowing;
    private ProgressBar progressBar;
    private UserAdapter userAdapter;

    public static final String EXTRA_FOLLOWING = "extra_following";

    public FollowingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBarDetailFollowing);
        rvFollowing = view.findViewById(R.id.rvFollowing);
        rvFollowing.setLayoutManager(new LinearLayoutManager(getContext()));

        assert getArguments() != null;
        String username = getArguments().getString(EXTRA_FOLLOWING);

        showLoading(false);
        FollowingViewModel followingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);

        followingViewModel.setListFollowing(username, getContext());

        followingViewModel.getListFollowing().observe(getViewLifecycleOwner(), list -> {
            userAdapter = new UserAdapter();
            userAdapter.notifyDataSetChanged();
            userAdapter.setListUsers(list);

            rvFollowing.setAdapter(userAdapter);
            showLoading(false);
            userAdapter.setOnItemClickCallback((User data) -> {
                showLoading(true);
                Intent goToDetailUser = new Intent(getContext(), DetailUserActivity.class);
                goToDetailUser.putExtra(DetailUserActivity.EXTRA_USER, data);
                startActivity(goToDetailUser);
            });
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading(false);
    }
}
