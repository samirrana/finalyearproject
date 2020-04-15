package com.example.assignmentapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class AssignmentFragment extends Fragment  {



    private static final String ARG_ASSIGNMENT_ID = "assignment_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int DATE_FORMAT = DateFormat.FULL;
    private static final int TIME_FORMAT = DateFormat.SHORT;

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    public static final int ACTIVITY_REQUEST_DATE = 3;
    public static final int ACTIVITY_REQUEST_TIME = 4;


    private Assignments mAssignments;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mAddassignment;
    private Button mDeleteassignment;
    private RadioGroup mRadioGroup;
    private Button mTimeButton;
    private CheckBox mPriorityCheckBox;
    private RadioButton mLowPriority;
    private RadioButton mMediumPriority;
    private RadioButton mHighPriority;
    private Button mShareButton;
    private Spinner mSpinner;
    private EditText mSubButton;







    public static AssignmentFragment newInstance(UUID assignmentID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ASSIGNMENT_ID, assignmentID);

        AssignmentFragment fragment = new AssignmentFragment();
        fragment.setArguments(args);
        return fragment;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID assignmentID = (UUID) getArguments().getSerializable(ARG_ASSIGNMENT_ID);
        mAssignments = AssignmentLab.get(getActivity()).getAssignment(assignmentID);

    }




    @Override
    public void onPause() {
        super.onPause();

        AssignmentLab.get(getActivity())
                .updateAssignment(mAssignments);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_assignment, container, false);


        mTitleField = (EditText) v.findViewById(R.id.assignment_title);

        mTitleField.setText(mAssignments.getTitle());


        mTitleField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mAssignments.setTitle(s.toString());

                if (mTitleField.getText().length() < 1) {
                    mTitleField.setError("Please enter a title");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //empty


            }

        });

        mSubButton = (EditText) v.findViewById(R.id.assignment_subject);

        mSubButton.setText(mAssignments.getSubject());


        mSubButton.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mAssignments.setSubject(s.toString());

                if (mTitleField.getText().length() < 1) {
                    mTitleField.setError("Please enter a subject");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //empty


            }

        });

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.assignment_solved);
        mSolvedCheckBox.setChecked(mAssignments.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mAssignments.setSolved(isChecked);
            }
        });

        mDateButton = (Button) v.findViewById(R.id.assignment_date);
        final Date currentDate = mAssignments.getDate();
        String formattedDate = DateFormatter.formatDateAsString(DATE_FORMAT, currentDate);
        mDateButton.setText(formattedDate);

        // Setup time button
        mTimeButton = (Button) v.findViewById(R.id.assignment_time);
        String formattedTime = DateFormatter.formatDateAsTimeString(TIME_FORMAT, currentDate);
        mTimeButton.setText(formattedTime);

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mAssignments.getDate());
                dialog.setTargetFragment(AssignmentFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mAssignments.getDate());
                dialog.setTargetFragment(AssignmentFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);

            }
        });


        mAddassignment = (Button) v.findViewById(R.id.add_btn);
        updateDate();
        mAddassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTitleField.getText().length() < 1 || mSubButton.getText().length() < 1) {
                    mTitleField.setError("Please enter a title");
                    mSubButton.setError("Please enter a subject");
                } else {
                    Intent intent = new Intent(getActivity(), AssignmentListActivity.class);
                    startActivity(intent);


                }
            }
        });

        mDeleteassignment = (Button) v.findViewById(R.id.delete_btn);
        updateDate();
        mDeleteassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UUID showId = mAssignments.getId();
                AssignmentLab.get(getActivity()).deleteAssignment(mAssignments);

                Toast.makeText(getActivity(), R.string.toast_assignment_delete, Toast.LENGTH_SHORT).show();
                getActivity().finish();


            }
        });













        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE || requestCode == REQUEST_TIME ||
                requestCode == ACTIVITY_REQUEST_DATE || requestCode == ACTIVITY_REQUEST_TIME) {
            final Date date;
            if (requestCode == REQUEST_DATE || requestCode == ACTIVITY_REQUEST_DATE) {
                date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            } else {
                date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            }

            mAssignments.setDate(date);
            mDateButton.setText(DateFormatter.formatDateAsString(DATE_FORMAT, date));
            mTimeButton.setText(DateFormatter.formatDateAsTimeString(TIME_FORMAT, date));
            updateDate();
            updateTime();
            }



        }



    private void updateDate() {
        Date date = mAssignments.getDate();
        String formattedDate = DateFormatter.formatDateAsString(DateFormat.LONG,date);
        mDateButton.setText(formattedDate);
    }


    private void updateTime() {
        Date date = mAssignments.getDate();
        String formattedTime = DateFormatter.formatDateAsTimeString(DateFormat.SHORT,date);
        mTimeButton.setText(formattedTime);
    }






}
