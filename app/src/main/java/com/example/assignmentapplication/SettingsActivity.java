package com.example.assignmentapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    private Switch myswitch;
    SharedPref mSharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSharedPref = new SharedPref(this);
        if(mSharedPref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);

        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
       setContentView(R.layout.preferencepage);
        myswitch=(Switch)findViewById(R.id.myswitch);
        if (mSharedPref.loadNightModeState()==true) {
            myswitch.setChecked(true);
        }

        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSharedPref.setNightModeState(true);
                    restartApp();
                }
                else {
                    mSharedPref.setNightModeState(false);
                    restartApp();
                }
            }
        });

    }

    private void restartApp() {
        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(i);
        finish();
    }

}
