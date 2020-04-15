package com.example.assignmentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenuFragment extends Fragment {


    private ImageButton mAssignmentButton;

    public static MainMenuFragment newInstance() {


        MainMenuFragment fragment = new MainMenuFragment();
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_menu, container, false);

        mAssignmentButton = (ImageButton) v.findViewById(R.id.assignment);
        mAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AssignmentListActivity.class);
                startActivity(intent);
            }
        });


        return v;
    }
}
