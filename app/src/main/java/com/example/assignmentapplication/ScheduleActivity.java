package com.example.assignmentapplication;



import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.app.Activity.RESULT_OK;


public class ScheduleActivity extends AppCompatActivity {

    private static schedule ScheduleFragment = new schedule();
    private subjects SubjectFragment = new subjects();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPref sharedPref;
        sharedPref = new SharedPref(this);


        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.darktheme);

        } else setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new schedule())
                .commit();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==11&&resultCode==RESULT_OK){
            SubjectFragment.onActivityResult(requestCode, resultCode, data);
        }else if(resultCode==RESULT_OK){
            ScheduleFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("RequestCode", String.valueOf(requestCode));
        super.startActivityForResult(intent, requestCode);
    }
}
