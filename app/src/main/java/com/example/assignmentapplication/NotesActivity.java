package com.example.assignmentapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NotesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPref sharedPref;
        sharedPref = new SharedPref(this);


        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.darktheme);

        } else setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new NotesListFragment())
                .commit();


    }
}