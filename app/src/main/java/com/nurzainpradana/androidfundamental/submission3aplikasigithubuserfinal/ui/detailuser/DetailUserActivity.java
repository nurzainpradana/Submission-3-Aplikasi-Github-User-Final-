package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.detailuser;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.R;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.User;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.viewmodel.UserViewModel;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.UserColumns.AVATAR_URL;
import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.UserColumns.CONTENT_URI;
import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.UserColumns.LOGIN;
import static com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.db.DatabaseContract.UserColumns.NAME;

public class DetailUserActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvDetailUserName, tvBio, tvFollow;
    CircleImageView ivDetailUserAvatar;
    Button btnFavorite;

    ContentValues values = new ContentValues();

    public String login = "";
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

        String followers = getString(R.string.followers);
        String following = getString(R.string.following);


        User mUser = getIntent().getParcelableExtra(EXTRA_USER);
        if(mUser != null){
            login = mUser.getLogin();
            Glide.with(getApplicationContext())
                    .load(mUser.getAvatarUrl())
                    .apply(new RequestOptions().override(150, 150))
                    .into(ivDetailUserAvatar);

            UserViewModel userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);

            userViewModel.setmUserDataApi(mUser.getLogin());
            userViewModel.getmUserDataApi().observe(this, user -> {
                String folow_text = user.getFollowers() + " " + followers + " " + user.getFollowing() + " " + following;
                tvFollow.setText(folow_text);
                tvDetailUserName.setText(user.getName());
                if (user.getBio() != null){
                    tvBio.setText(user.getBio());
                }

                values.put(LOGIN, user.getLogin());
                values.put(NAME, user.getName());
                values.put(AVATAR_URL, user.getAvatarUrl());

            });

            userViewModel.setUserLocal(mUser.getLogin(), getContentResolver());
            userViewModel.getUserLocal().observe(this, userLocals -> {
                boolean check = false;
                for (int i = 0; i < userLocals.size(); i++) {
                    if (userLocals.get(i).getLogin().equals(login))
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
            });
        }

        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(this, getSupportFragmentManager(), mUser);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPageAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        String titleDetail = getString(R.string.titleDetail);
        Objects.requireNonNull(getSupportActionBar()).setTitle(titleDetail);

        btnFavorite.setOnClickListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Intent changeLanguageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(changeLanguageIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnFavorite) {
             if (btnFavorite.getText() == addtofavorite){
                //content uri untuk insert
                getContentResolver().insert(CONTENT_URI, values);
                addFavorite();
                Toast.makeText(this, getResources().getString(R.string.success_add_to_favorite), Toast.LENGTH_SHORT).show();

            } else if (btnFavorite.getText() == removefromfavorite) {
                 //uriwithusername untuk delete
                 Uri uriWithId = Uri.parse(CONTENT_URI + "/" + id);
                 getContentResolver().delete(uriWithId, null, null );
                 removeFavorite();
                 Toast.makeText(this, getResources().getString(R.string.success_remove_from_favorite), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addFavorite(){
        btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        btnFavorite.setText(getResources().getString(R.string.removefromfavorite));
    }

    public void removeFavorite() {
        btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        btnFavorite.setText(getResources().getString(R.string.addtofavorite));
    }
}
