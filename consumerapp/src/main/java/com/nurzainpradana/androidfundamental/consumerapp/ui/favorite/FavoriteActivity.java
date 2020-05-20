package com.nurzainpradana.androidfundamental.consumerapp.ui.favorite;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nurzainpradana.androidfundamental.consumerapp.R;
import com.nurzainpradana.androidfundamental.consumerapp.data.User;
import com.nurzainpradana.androidfundamental.consumerapp.util.helper.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.nurzainpradana.androidfundamental.consumerapp.db.DatabaseContract.UserColumns.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity implements LoadUserCallback {
    private ProgressBar progressBar;
    private FavoriteAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";
    public static final String EXTRA_USER = "extra_user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.user_favorite));
        }

        progressBar = findViewById(R.id.favorite_progressBar);

        RecyclerView rvFavorites = findViewById(R.id.favorite_recyclerView);

        rvFavorites.setLayoutManager(new LinearLayoutManager(this));
        rvFavorites.setHasFixedSize(true);

        adapter = new FavoriteAdapter();
        rvFavorites.setAdapter(adapter);


        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadUserAsync(this, this).execute();
        } else {
            ArrayList<User> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListUser(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListUser());
    }


    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<User> userLocal) {
        progressBar.setVisibility(View.INVISIBLE);
        if (userLocal.size() > 0) {
            adapter.setListUser(userLocal);
        } else {
            adapter.setListUser(new ArrayList<User>());
            Toast.makeText(this, "Tidak Ada Data Saat Ini", Toast.LENGTH_SHORT).show();
        }
    }


    private static class LoadUserAsync extends AsyncTask<Void, Void, ArrayList<User>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadUserCallback> weakCallback;

        LoadUserAsync(Context context, LoadUserCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            assert dataCursor != null;
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<User> userLocals) {
            super.onPostExecute(userLocals);
            weakCallback.get().postExecute(userLocals);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadUserAsync(context, (LoadUserCallback) context).execute();
        }
    }
}

interface LoadUserCallback {
    void preExecute();
    void postExecute(ArrayList<User> userLocal);
}
