package com.example.assignmentapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MainMenuFragment extends Fragment {


    private ImageButton mAssignmentButton;
    private ImageButton mNotesButton;
    private ImageButton mTimeTableButton;
    private ImageButton mCalendarButton;

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

        mNotesButton = (ImageButton) v.findViewById(R.id.notes);
        mNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), NotesActivity.class);
                startActivity(intent);
            }
        });



        mTimeTableButton = (ImageButton) v.findViewById(R.id.timetable);
        mTimeTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(intent);

            }
        });

        mCalendarButton = (ImageButton) v.findViewById(R.id.calendar_btn);
        mCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment someFragment = new subjects();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });


        return v;
    }
}
