package com.example.assignmentapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AssignmentFragment extends Fragment  {



    private static final String ARG_ASSIGNMENT_ID = "assignment_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int DATE_FORMAT = DateFormat.FULL;
    private static final int TIME_FORMAT = DateFormat.SHORT;

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_PHOTO= 2;
    private static final int REQUEST_DATES = 5;
    private static final int REQUEST_TIMES = 6;

    public static final int ACTIVITY_REQUEST_DATE = 3;
    public static final int ACTIVITY_REQUEST_TIME = 4;
    public static final int ACTIVITY_REQUEST_DATES = 7;
    public static final int ACTIVITY_REQUEST_TIMES = 8;


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
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
    private Button mReminderTime;
    private Button mReminderDate;


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
        mPhotoFile = AssignmentLab.get(getActivity()).getPhotoFile(mAssignments);

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


        mReminderDate = (Button) v.findViewById(R.id.reminder_date);
        final Date currentDates = mAssignments.getReminderDate();
        String formattedDates = DateFormatter.formatDateAsString(DATE_FORMAT,currentDates);
        mReminderDate.setText(formattedDates);

        mReminderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager= getFragmentManager();
                ReminderPickerFragment dialog =  ReminderPickerFragment.newInstance(mAssignments.getReminderDate());
                dialog.setTargetFragment(AssignmentFragment.this, REQUEST_DATES);
                dialog.show(manager, DIALOG_DATE);
            }
        });


        // Setup time button
        mReminderTime = (Button) v.findViewById(R.id.reminder_time);
        String formattedTimes = DateFormatter.formatDateAsTimeString(TIME_FORMAT,currentDates);
        mReminderTime.setText(formattedTimes);

        mReminderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getFragmentManager();
                ReminderTimeFragment dialog = ReminderTimeFragment.newInstance(mAssignments.getReminderDate());
                dialog.setTargetFragment(AssignmentFragment.this, REQUEST_TIMES);
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


        PackageManager packageManager = getActivity().getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                !packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            mPhotoButton.setEnabled(false);
        }


        mPhotoButton = (ImageButton) v.findViewById(R.id.assignment_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.example.assignmentapplication.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });


        mPhotoView = (ImageView) v.findViewById(R.id.assignment_picture);

        updatePhotoView();


        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(),getActivity());
                    displayPopUpImage(bitmap);

            }
        });

        return v;
    }


    private void myCustomerAlertDialog(){
        final Dialog MyDialog = new Dialog(getActivity());
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.activity_full_screen_image);
        MyDialog.setTitle("My Custom Dialog");

        MyDialog.show();

    }

    private void displayPopUpImage(Bitmap imageBitmap){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        ImageView imageView = new ImageView (getActivity());
        imageView.setImageBitmap(imageBitmap);

        alertDialog.setView(imageView);
        alertDialog.create();
        alertDialog.show();
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
            mDateButton.setText(DateFormatter.formatDateAsTimeString(TIME_FORMAT, date));
            updateDate();
            updateTime();
            }
        else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.example.assignmentapplication.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();

        } else if (requestCode == REQUEST_DATES || requestCode == REQUEST_TIMES ||
                requestCode == ACTIVITY_REQUEST_DATES || requestCode == ACTIVITY_REQUEST_TIMES) {
            final Date dates;
            if (requestCode == REQUEST_DATES || requestCode == ACTIVITY_REQUEST_DATES) {
                dates = (Date) data.getSerializableExtra(ReminderPickerFragment.EXTRA_DATES);
            } else {
                dates = (Date) data.getSerializableExtra(ReminderTimeFragment.EXTRA_TIMES);
            }

            mAssignments.setReminderDate(dates);
            mReminderDate.setText(DateFormatter.formatDateAsString(DATE_FORMAT, dates));
            mReminderTime.setText(DateFormatter.formatDateAsTimeString(TIME_FORMAT, dates));
            updateDate();
            updateTime();
        }

        }

    private void updateDate() {
        Date date = mAssignments.getDate();
        String formattedDate = DateFormatter.formatDateAsString(DateFormat.LONG,date);
        mDateButton.setText(formattedDate);

        Date dates = mAssignments.getReminderDate();
        String formattedDates = DateFormatter.formatDateAsString(DateFormat.LONG,dates);
        mReminderDate.setText(formattedDates);



    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    private void updateTime() {
        Date date = mAssignments.getDate();
        String formattedTime = DateFormatter.formatDateAsTimeString(DateFormat.SHORT,date);
        mTimeButton.setText(formattedTime);

        Date dates = mAssignments.getReminderDate();
        String formattedTimes = DateFormatter.formatDateAsTimeString(DateFormat.SHORT,dates);
        mReminderTime.setText(formattedTimes);

    }






}
