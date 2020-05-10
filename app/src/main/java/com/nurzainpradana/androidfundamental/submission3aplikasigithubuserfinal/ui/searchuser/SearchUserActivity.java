package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.searchuser;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.R;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.data.User;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.detailuser.DetailUserActivity;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.viewmodel.UserViewModel;

import java.util.Objects;

public class SearchUserActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtUserSearch;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private UserViewModel userViewModel;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        String titleSearch = getString(R.string.titleSearch);
        Objects.requireNonNull(getSupportActionBar()).setTitle(titleSearch);

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);

        edtUserSearch = findViewById(R.id.edtSearchUser);
        progressBar = findViewById(R.id.progressBar);
        ImageButton btnSearch = findViewById(R.id.btnSearchUser);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showLoading(false);
        btnSearch.setOnClickListener(this);

        userViewModel.getListUsers().observe(this, list -> {
            adapter = new UserAdapter();
            adapter.setListUsers(list);
            recyclerView.setAdapter(adapter);
            showLoading(false);

            adapter.setOnItemClickCallback((User data) -> {
                showLoading(true);
                Intent goToDetailUser = new Intent(SearchUserActivity.this, DetailUserActivity.class);
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
    protected void onResume() {
        super.onResume();
        showLoading(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSearchUser) {
            showLoading(true);
            if (edtUserSearch.getText().toString().isEmpty()) {
                edtUserSearch.setError("Username Required");
                edtUserSearch.setFocusable(true);
                showLoading(false);
            } else {
                String username = edtUserSearch.getText().toString();
                userViewModel.setListUsers(username, getApplicationContext());
            }
        }
    }

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_setting) {
            Intent changeLanguageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(changeLanguageIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
