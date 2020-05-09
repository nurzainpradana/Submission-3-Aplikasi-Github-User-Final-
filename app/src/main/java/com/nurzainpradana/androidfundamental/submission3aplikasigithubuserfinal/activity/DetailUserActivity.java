package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.R;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.adapter.SectionsPageAdapter;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api.Api;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.api.ApiInterface;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.model.User;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.model.UserLocal;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.util.MappingHelper;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.NoteColumns.CONTENT_URI;
import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.NoteColumns.USERNAME;

public class DetailUserActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvDetailUserName, tvBio, tvFollow;
    CircleImageView ivDetailUserAvatar;
    Button btnFavorite;

    public static String login = "";
    public Integer id;

    private final static String addtofavorite = "Add To Favorite";
    private final static String removefromfavorite = "Remove From Favorite";

    public static final String EXTRA_USER = "extra_user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        tvDetailUserName = findViewById(R.id.tvDetailUserName);
        tvBio = findViewById(R.id.tvDetailUserBio);
        tvFollow = findViewById(R.id.tvDetailUserFollowingFollowers);
        ivDetailUserAvatar = findViewById(R.id.ivDetailUserAvatar);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnFavorite.setOnClickListener(this);

        User mUser = getIntent().getParcelableExtra(EXTRA_USER);
        login = mUser.getLogin();

        Glide.with(getApplicationContext())
                .load(mUser.getAvatarUrl())
                .apply(new RequestOptions().override(150,150))
                .into(ivDetailUserAvatar);

        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(this, getSupportFragmentManager(), mUser);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPageAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        String titleDetail = getString(R.string.titleDetail);
        getSupportActionBar().setTitle(titleDetail);

        String followers = getString(R.string.followers);
        String following = getString(R.string.following);

        UserViewModel userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);

        userViewModel.setmUserDataApi(mUser.getLogin(), getContentResolver());
        userViewModel.getmUserDataApi().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tvFollow.setText(user.getFollowers() + " " + followers + " " + user.getFollowing() + " " + following );
                tvDetailUserName.setText(user.getName());
                if (user.getBio() != null){
                    tvBio.setText(user.getBio());
                }
            }
        });

        userViewModel.setUserLocal(mUser.getLogin(), getContentResolver());
        userViewModel.getUserLocal().observe(this, new Observer<List<UserLocal>>() {
            @Override
            public void onChanged(List<UserLocal> userLocals) {
                Boolean check = false;
                for (int i = 0; i < userLocals.size(); i++) {
                    if (userLocals.get(i).getUsername().equals(login))
                    {
                        check = true;
                        id = userLocals.get(i).getId();
                    }
                }
                if (check) {
                    btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                    btnFavorite.setText(removefromfavorite);
                    Toast.makeText(DetailUserActivity.this, btnFavorite.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    btnFavorite.setText(addtofavorite);
                }
            }
        });
        btnFavorite.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {


        ContentValues values = new ContentValues();
        values.put(USERNAME, login);

        if(v.getId() == R.id.btnFavorite) {
             if (btnFavorite.getText() == addtofavorite){
                //content uri untuk insert
                getContentResolver().insert(CONTENT_URI, values);
                btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                btnFavorite.setText(removefromfavorite);

            } else if (btnFavorite.getText() == removefromfavorite) {
                 //uriwithusername untuk delete
                 Uri uriWithId = Uri.parse(CONTENT_URI + "/" + id);
                 Toast.makeText(this, uriWithId.toString(), Toast.LENGTH_SHORT).show();
                 getContentResolver().delete(uriWithId, null, null );
                 btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                 btnFavorite.setText(addtofavorite);
            }
        }
    }
}
