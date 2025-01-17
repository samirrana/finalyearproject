package com.example.assignmentapplication;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.assignmentapplication.R;
import com.example.assignmentapplication.add_subject;
import com.example.assignmentapplication.database.DBHelper;
import com.google.android.material.snackbar.Snackbar;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class add_schedule extends AppCompatActivity {
    DBHelper db; ArrayList<String> subject_name; ArrayList<String> days_name;
    private static ArrayList<String>  subject_type;
    private static Calendar now = Calendar.getInstance();
    private static schedule ScheduleFragment = new schedule();
    TimePickerDialog StartDialog, EndDialog; Toolbar tb;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_add_schedule);
        db = new DBHelper(this);
        tb = findViewById(R.id.toolbar_add_schedule);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true); ab.setHomeAsUpIndicator(R.drawable.ic_back);
        initTimeDialog();


        final SharedPref sharedPref;
        sharedPref = new SharedPref(this);


        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.darktheme);

        } else setTheme(R.style.AppTheme);



        subject_name = new ArrayList<>();
        subject_type = new ArrayList<>();
        days_name = new ArrayList<>();
        days_name.add("Monday"); days_name.add("Tuesday"); days_name.add("Wednesday");
        days_name.add("Thursday"); days_name.add("Friday"); days_name.add("Saturday");
        subject_type.add("Lecture"); subject_type.add("Tutorial");
        db.subject_name(subject_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTimeDialog() {
        StartDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                ((EditText)findViewById(R.id.add_schedule_start_time)).setText(hourOfDay+":"+minute);
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

        EndDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                ((EditText)findViewById(R.id.add_schedule_end_time)).setText(hourOfDay+":"+minute);
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

        StartDialog.setTitle("Pick start time"); EndDialog.setTitle("Pick end time");
        StartDialog.dismissOnPause(true); EndDialog.dismissOnPause(true);
        StartDialog.setThemeDark(true); EndDialog.setThemeDark(true);
        StartDialog.vibrate(true); EndDialog.vibrate(true);
        StartDialog.enableSeconds(false); EndDialog.enableSeconds(false);
    }



    public void add(View view) {
        String name = ((EditText)findViewById(R.id.add_schedule_subject_name)).getText().toString();
        String type = ((EditText)findViewById(R.id.add_schedule_subject_type)).getText().toString();
        String day = ((EditText)findViewById(R.id.add_schedule_day)).getText().toString();
        String start = ((EditText)findViewById(R.id.add_schedule_start_time)).getText().toString();
        String end = ((EditText)findViewById(R.id.add_schedule_end_time)).getText().toString();
        if(name.length()==0||type.length()==0||day.length()==0|start.length()==0||end.length()==0){
            Snackbar.make(findViewById(R.id.root_add_schedule), "Fill all the form!", Snackbar.LENGTH_SHORT).show();
        }else{
            String [] st = start.split(":");
            String [] et = end.split(":");
            if(Integer.valueOf(st[0])>Integer.valueOf(et[0])){
                Snackbar.make(findViewById(R.id.root_add_schedule), "There's something wrong with the time!", Snackbar.LENGTH_SHORT).show();
            }else if(db.add_timetable(new timetable_model(name, type, day, start, end))){
                Log.d("Database Operation :", "Adding Timetable");
                Intent data = new Intent();
                data.putExtra("name", name);
                data.putExtra("type", type);
                data.putExtra("day", day);
                data.putExtra("start", start);
                data.putExtra("end" , end);
                setResult(RESULT_OK, data);
                finish();
                Intent intent = new Intent(this, ScheduleActivity.class);
                startActivity(intent);
                finish();


            }
        }



    }

    public void name_dialog(View view) {
        if (subject_name.size()>0){
            new MaterialDialog.Builder(this).title("Select a subject")
                    .items(subject_name).itemsCallback(new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                    ((EditText)findViewById(R.id.add_schedule_subject_name)).setText(subject_name.get(position));
                }
            }).autoDismiss(true).negativeText("Cancel").show();
        }else {
            new MaterialDialog.Builder(this).title("Select a subject")
                    .content("There is no subject data").autoDismiss(true)
                    .positiveText("Add new subject").negativeText("Cancel").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    startActivityForResult(new Intent(add_schedule.this, add_subject.class), 0);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }).onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            }).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            subject_name.clear();
            db.subject_name(subject_name);
        } else if (resultCode == RESULT_OK) {
            ScheduleFragment.onActivityResult(requestCode, resultCode, data);
        }





    }

    public void type_dialog(View view) {
        new MaterialDialog.Builder(this).title("Select type for the subject")
                .items(subject_type).itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                ((EditText)findViewById(R.id.add_schedule_subject_type)).setText(subject_type.get(position));
            }
        }).autoDismiss(true).negativeText("Cancel").show();
    }

    public void day_dialog(View view) {
        new MaterialDialog.Builder(this).title("Pick a day")
                .items(days_name).itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                ((EditText)findViewById(R.id.add_schedule_day)).setText(days_name.get(position));
            }
        }).autoDismiss(true).negativeText("Cancel").show();
    }

    public void startTime_dialog(View view) {
        StartDialog.show(getFragmentManager(), "Start Time Dialog");
    }

    public void endTime_dialog(View view) {
        EndDialog.show(getFragmentManager(), "End Time Dialog");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}