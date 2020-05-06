package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.R;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.adapter.SectionsPageAdapter;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api.Api;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api.ApiInterface;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.model.User;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity {

    TextView tvDetailUserName, tvBio, tvFollow;
    CircleImageView ivDetailUserAvatar;

    public static final String EXTRA_USER = "extra_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        tvDetailUserName = findViewById(R.id.tvDetailUserName);
        tvBio = findViewById(R.id.tvDetailUserBio);
        tvFollow = findViewById(R.id.tvDetailUserFollowingFollowers);
        ivDetailUserAvatar = findViewById(R.id.ivDetailUserAvatar);

        User user = getIntent().getParcelableExtra(EXTRA_USER);
        getDetailUser(user.getLogin());

        Glide.with(getApplicationContext())
                .load(user.getAvatarUrl())
                .apply(new RequestOptions().override(150,150))
                .into(ivDetailUserAvatar);

        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(this, getSupportFragmentManager(), user);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPageAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        String titleDetail = getString(R.string.titleDetail);
        getSupportActionBar().setTitle(titleDetail);
    }

    private void getDetailUser(String username) {
        ApiInterface Service;
        retrofit2.Call<User> Call;
        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getDetailUser(username);
            Call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                    String followers = getString(R.string.followers);
                    String following = getString(R.string.following);
                    User mUser = response.body();
                    tvDetailUserName.setText(mUser.getName());
                    tvFollow.setText(mUser.getFollowers() + " " + followers + " " + mUser.getFollowing() + " " + following);
                    if (mUser.getBio() != null) {
                        tvBio.setText(mUser.getBio());
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<User> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.language_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_setting) {
            Intent changeLanguageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(changeLanguageIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
