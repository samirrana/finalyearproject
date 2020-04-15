package com.example.assignmentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainMenuActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadFragment(new MainMenuFragment());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }



    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            final SharedPref sharedPref;
            sharedPref = new SharedPref(this);


            if(sharedPref.loadNightModeState()){
                setTheme(R.style.darktheme);

            } else setTheme(R.style.AppTheme);


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        final SharedPref sharedPref;
        sharedPref = new SharedPref(this);


        if(sharedPref.loadNightModeState()){
            setTheme(R.style.darktheme);

        } else setTheme(R.style.AppTheme);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        final SharedPref sharedPref;
        sharedPref = new SharedPref(this);


        if(sharedPref.loadNightModeState()){
            setTheme(R.style.darktheme);

        } else setTheme(R.style.AppTheme);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, AssignmentListActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_assignments) {
            Assignments assignment = new Assignments();
            AssignmentLab.get(this).addAssignments(assignment);
            Intent intent = AssignmentPagerActivity
                    .newIntent(this, assignment.getId());
            startActivity(intent);

        } else if (id == R.id.nav_notes) {

            Intent intent = new Intent(this, SubActivity.class);
            startActivity(intent);
            return true;


        } else if (id == R.id.nav_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_logout) {

            Intent intent = new Intent(this, LogOutActivity.class);
            startActivity(intent);
            finish();

            return true;



        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
