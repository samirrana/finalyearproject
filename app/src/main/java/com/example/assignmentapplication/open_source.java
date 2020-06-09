package com.example.assignmentapplication;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class open_source extends Fragment {
    RecyclerView rc; libraries_adapter adapter;
    ArrayList<library_source> list;

    public open_source() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.f_open_source, container, false);
        rc = v.findViewById(R.id.rc_open_source);
        list = new ArrayList<>();
        adapter = new libraries_adapter(this.getContext(), list);
        initData();
        rc.setHasFixedSize(true); rc.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rc.addItemDecoration(new DividerItemDecoration(rc.getContext(), DividerItemDecoration.VERTICAL));
        rc.setAdapter(adapter);
        return v;
    }

    private void initData() {
        list.add(new library_source("material-dialog", "https://github.com/afollestad/material-dialogs", "afollestad"));
        list.add(new library_source("CircleImageView", "https://github.com/hdodenhof/CircleImageView", "hdodenhof"));
        list.add(new library_source("TimeTable", "https://github.com/EunsilJo/TimeTable", "EunsilJo"));
        list.add(new library_source("MaterialDateTimePicker", "https://github.com/wdullaer/MaterialDateTimePicker", "wdullaer"));
        list.add(new library_source("SmoothProgressBar", "https://github.com/castorflex/SmoothProgressBar", "castorflex"));
        list.add(new library_source("FloatingActionButton", "https://github.com/Clans/FloatingActionButton", "Clans"));
        list.add(new library_source("Android-Image-Cropper", "https://github.com/ArthurHub/Android-Image-Cropper", "ArthurHub"));
        list.add(new library_source("Bungee", "https://github.com/Binary-Finery/Bungee", "Binary-Finery"));
    }
}
