package com.example.assignmentapplication;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.assignmentapplication.database.DBHelper;

import java.util.ArrayList;
import java.util.Objects;


import static android.app.Activity.RESULT_OK;

public class subjects extends Fragment {
    MaterialDialog.Builder mdb; DBHelper database; RecyclerView rc;
    ArrayList<subject_model> list; subject_adapter adapter;
    public subjects() {
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.f_subjects, container, false);
        mdb = new MaterialDialog.Builder(Objects.requireNonNull(this.getContext()));
        database = new DBHelper(this.getContext());
        rc = v.findViewById(R.id.rc_subject);
        list = new ArrayList<>();

        rc.setHasFixedSize(true); rc.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return v;
    }

    @Override
    public void onStart() {
        if(!list.isEmpty()){
            list.clear();database.data_subjects(list);
        }else{
            database.data_subjects(list);
        }
        adapter = new subject_adapter(this.getContext(), list);
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
                Intent intent = new Intent(getActivity(), add_subject.class);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==11&&resultCode==RESULT_OK){
            subject_model newest = new subject_model(data.getStringExtra("name"), data.getStringExtra("room"), data.getStringExtra("teacher"), data.getStringExtra("note"));
            newest.setId(data.getStringExtra("id"));
            list.add(newest);
            adapter.notifyDataSetChanged();
        }else if(requestCode==12&&resultCode==RESULT_OK){
            subject_model newest = new subject_model(data.getStringExtra("name"), data.getStringExtra("room"), data.getStringExtra("teacher"), data.getStringExtra("note"));
            newest.setId(data.getStringExtra("id"));
            list.set(Integer.valueOf(data.getStringExtra("index")), newest);
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this.getContext(), "Add subject canceled!", Toast.LENGTH_SHORT).show();
        }

    }
}
