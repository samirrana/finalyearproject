package com.example.assignmentapplication;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.assignmentapplication.database.DBHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;



public class teachers extends Fragment {
    public MaterialDialog.Builder mdb; DBHelper database; RecyclerView rc;
    ArrayList<teacher_model> list; teacher_adapter adapter;


    public teachers() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_teachers, container, false);
        mdb = new MaterialDialog.Builder(Objects.requireNonNull(this.getContext()));
        database = new DBHelper(this.getContext());
        rc = v.findViewById(R.id.rc_teacher);
        list = new ArrayList<>();

        rc.setHasFixedSize(true); rc.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rc.addItemDecoration(new DividerItemDecoration(rc.getContext(), DividerItemDecoration.VERTICAL));


        setupDialog();
        return v;
    }

    @Override
    public void onStart() {
        if (!list.isEmpty()){
            list.clear(); database.data_teachers(list);
        }else{
            database.data_teachers(list);
        }
        adapter = new teacher_adapter(this.getContext(), list);
        rc.setAdapter(adapter);
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_assignment_list, menu);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_show:
             createDialog();
             return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void setupDialog() {
        @SuppressLint("InflateParams") final View v = getLayoutInflater().inflate(R.layout.d_add_teacher, null);
        final EditText input_name = v.findViewById(R.id.input_name_teacher);
        final EditText input_phone = v.findViewById(R.id.input_phone_teacher);
        mdb.title("Add Teacher").customView(v, false).positiveText("Add").negativeText("Cancel").autoDismiss(true);

        mdb.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                String name = input_name.getText().toString();
                String phone = input_phone.getText().toString();

                if(!name.matches("")||!phone.matches("")){
                    if(database.add_teacher(new teacher_model(name, phone))){
                        Log.d("Database Operation :", "Adding Teacher");
                        teacher_model newest = new teacher_model(name, phone);
                        newest.setId(String.valueOf(database.getTeacherId(newest.name)));
                        list.add(newest); adapter.notifyDataSetChanged();
                        Toast.makeText(teachers.this.getContext(), "Teacher Added!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(teachers.this.getContext(), "Adding teacher failed!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(teachers.this.getContext(), "Fill all the fields!", Toast.LENGTH_SHORT).show();
                }
            }
        }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });

        mdb.dismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                input_name.setText(null); input_phone.setText(null);
            }
        });
    }

    private void createDialog(){
        if (true){
           new MaterialDialog.Builder(getContext()).title("Select a Teacher").content("Lets add a teacher")
                    .positiveText("Add new teacher").negativeText("cancel").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    @SuppressLint("InflateParams") final View v = getLayoutInflater().inflate(R.layout.d_add_teacher, null);
                    final EditText input_name = v.findViewById(R.id.input_name_teacher);
                    final EditText input_phone = v.findViewById(R.id.input_phone_teacher);
                    mdb.title("Add Teacher").customView(v, false).positiveText("Add").negativeText("Cancel").autoDismiss(true);

                    mdb.onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            String name = input_name.getText().toString();
                            String phone = input_phone.getText().toString();


                            if(!name.matches("")||!phone.matches("")){
                                if(database.add_teacher(new teacher_model(name, phone))){
                                    Log.d("Database Operation :", "Adding Teacher");
                                    teacher_model newest = new teacher_model(name, phone);
                                    newest.setId(String.valueOf(database.getTeacherId(newest.name)));
                                    list.add(newest);
                                }else{
                                    Toast.makeText(getContext(), "Add Teacher failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }).onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            input_name.setText(null); input_phone.setText(null);
                        }
                    }).build().show();

                }
            }).onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            }).autoDismiss(true).build().show();
        }

    }
}
