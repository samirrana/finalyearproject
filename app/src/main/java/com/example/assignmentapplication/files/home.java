package com.example.assignmentapplication.files;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.AssignmentLab;
import com.example.assignmentapplication.Assignments;
import com.example.assignmentapplication.R;
import com.example.assignmentapplication.TodayAssignmentAdapter;
import com.example.assignmentapplication.TodayClassAdapter;
import com.example.assignmentapplication.add_schedule;
import com.example.assignmentapplication.database.DBHelper;
import com.example.assignmentapplication.timetable_model;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class home extends Fragment {
    private static ArrayList<timetable_model> listData = new ArrayList<>();
    private LinearLayout no_class;
    private RecyclerView rec;
    private DBHelper helper;
    private RecyclerView recd;





    public home() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.f_home, container, false);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        no_class = view.findViewById(R.id.no_class);
        rec = view.findViewById(R.id.today_class);
        recd = view.findViewById(R.id.today_assignment);
        TextView current_date = view.findViewById(R.id.current_date);
        helper = new DBHelper(getContext());




        current_date.setText(new SimpleDateFormat("EEEE, d MMMM yyyy").format(new Date()));
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onStart() {
        super.onStart();
        if (!listData.isEmpty()){
            listData.clear();
            helper.data_timetable_by_day(listData, new SimpleDateFormat("EEEE").format(new Date()));
        }else {
            helper.data_timetable_by_day(listData, new SimpleDateFormat("EEEE").format(new Date()));
        }

        AssignmentLab assignmentLab = AssignmentLab.get(getActivity());
        List<Assignments> mAssignments = assignmentLab.getAssignments();

        checkTodayClass(listData);
        checkTodayAssignment(mAssignments);

    }

    private void checkTodayClass(ArrayList<timetable_model> data){
        if (data.isEmpty()){
            no_class.setVisibility(View.VISIBLE);
            rec.setVisibility(View.GONE);
        }else {
            no_class.setVisibility(View.GONE);
            rec.setVisibility(View.VISIBLE);
            TodayClassAdapter adapter = new TodayClassAdapter(data, helper);
            rec.setLayoutManager(new LinearLayoutManager(getContext()));
            rec.setHasFixedSize(true);
            rec.addItemDecoration(new DividerItemDecoration(rec.getContext(), DividerItemDecoration.VERTICAL));
            rec.setAdapter(adapter);
        }
    }

    private void checkTodayAssignment(List<Assignments> data){
        if (data.isEmpty()){
            no_class.setVisibility(View.VISIBLE);
            recd.setVisibility(View.GONE);
        }else {
            no_class.setVisibility(View.GONE);
            recd.setVisibility(View.VISIBLE);
            TodayAssignmentAdapter adapter = new TodayAssignmentAdapter(data, helper);
            recd.setLayoutManager(new LinearLayoutManager(getContext()));
            recd.setHasFixedSize(true);
            recd.addItemDecoration(new DividerItemDecoration(rec.getContext(), DividerItemDecoration.VERTICAL));
            recd.setAdapter(adapter);
        }
    }
}
