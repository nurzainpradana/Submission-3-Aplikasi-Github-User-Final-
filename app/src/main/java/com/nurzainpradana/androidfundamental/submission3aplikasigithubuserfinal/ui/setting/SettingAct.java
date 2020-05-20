package com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.R;
import com.nurzainpradana.androidfundamental.submission3aplikasigithubuserfinal.util.receiver.AlarmReceiver;

import java.util.Locale;
import java.util.Objects;

public class SettingAct extends AppCompatActivity implements View.OnClickListener {
    LinearLayout changeLanguage;
    TextView current_language;
    Switch switchToggle;

    AlarmReceiver alarmReceiver;

    private String PREF_REMINDER = "pref_reminder";
    private SharedPreferences preferences = getSharedPreferences(PREF_REMINDER, Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        changeLanguage = findViewById(R.id.action_setting_language);
        switchToggle = findViewById(R.id.switch_toggle);
        current_language = findViewById(R.id.current_language);

        alarmReceiver = new AlarmReceiver();
        current_language.setText(Locale.getDefault().getDisplayLanguage());

        changeLanguage.setOnClickListener(this);
        preferences = getSharedPreferences(PREF_REMINDER, Context.MODE_PRIVATE);
        boolean reminderSet = preferences.getBoolean(PREF_REMINDER, false);
        if (reminderSet){
            switchToggle.setChecked(true);
        } else {
            switchToggle.setChecked(false);
        }

        switchToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                String repeatTime = "09:00";
                String repeatMessage = getResources().getString(R.string.alarm_message);
                Context context = getApplicationContext();
                alarmReceiver.setRepeatingAlarm(context, AlarmReceiver.TITLE, repeatTime, repeatMessage);
                Toast.makeText(context, repeatMessage, Toast.LENGTH_SHORT).show();
                saveSetting(true);
            } else {
                alarmReceiver.cancelAlarm(this);
                saveSetting(false);
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

    public void saveSetting(Boolean reminder_setting) {
        preferences = getSharedPreferences(PREF_REMINDER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_REMINDER, reminder_setting);
        editor.apply();
    }
}
