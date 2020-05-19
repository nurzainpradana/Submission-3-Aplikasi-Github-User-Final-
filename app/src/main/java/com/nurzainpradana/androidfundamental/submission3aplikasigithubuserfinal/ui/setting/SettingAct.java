package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.R;

import java.util.Locale;
import java.util.Objects;

public class SettingAct extends AppCompatActivity implements View.OnClickListener {
    LinearLayout changeLanguage;
    TextView current_language;
    Switch switchToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        changeLanguage = findViewById(R.id.action_setting_language);
        switchToggle = findViewById(R.id.switch_toggle);
        current_language = findViewById(R.id.current_language);

        current_language.setText(Locale.getDefault().getDisplayLanguage());

        changeLanguage.setOnClickListener(this);
        switchToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //toggle is enabled
                } else {
                    //toggle is disabled
                }
            }
        });

        String titleDetail = getString(R.string.setting);
        Objects.requireNonNull(getSupportActionBar()).setTitle(titleDetail);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_setting_language) {
            Intent changeLanguageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(changeLanguageIntent);
        }
    }
}
