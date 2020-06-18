package com.example.assignmentapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.assignmentapplication.database.DBHelper;
import com.example.assignmentapplication.files.home;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import org.jraf.android.alibglitch.GlitchEffect;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import spencerstudios.com.bungeelib.Bungee;


public class Home extends AppCompatActivity {
    Toolbar tb; FrameLayout fl; DrawerLayout dl; CoordinatorLayout cl; NavigationView nv;
    FragmentManager fm; ActionBarDrawerToggle ActionBarDrawer;
    FloatingActionMenu fab_all;

    @SuppressLint("StaticFieldLeak")
    private static home HomeFragment = new home();
    private DBHelper database;
    private SharedPreferences pref;
    private static schedule ScheduleFragment = new schedule();
    private subjects SubjectFragment = new subjects();
    private teachers TeacherFragment = new teachers();
    private open_source SourceFragment = new open_source();
    private AppDatabase appDatabase;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_home);
        pref = getSharedPreferences("preferences", MODE_PRIVATE);
        database = new DBHelper(this);
        tb = findViewById(R.id.toolbar);
        fl = findViewById(R.id.content_home);
        dl = findViewById(R.id.root_home);
        cl = findViewById(R.id.root_coordinator_home);
        nv = findViewById(R.id.nav_view);
        fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.content_home, HomeFragment).commit();



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==11&&resultCode==RESULT_OK){
            SubjectFragment.onActivityResult(requestCode, resultCode, data);
        }else if(requestCode==2&&resultCode==RESULT_OK){
            ScheduleFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("RequestCode", String.valueOf(requestCode));
        super.startActivityForResult(intent, requestCode);
    }


}
