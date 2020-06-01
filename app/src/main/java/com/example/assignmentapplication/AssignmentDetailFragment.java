package com.example.assignmentapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class AssignmentDetailFragment extends Fragment {


    private Assignments mAssignments;
    private File mPhotoFile;
    private File mPhotoFilez;
    private static final String ARG_ASSIGNMENT_ID = "assignment_id";
    private TextView mTitleTextView;
    private TextView mDateTextView;
    private ImageView mSolvedImageView;
    private TextView mSubjectTextView;
    private TextView mReminderTextView;
    private TextView mTypeTextView;
    private ImageView mPictureImageView;
    private Button mDeleteButton;
    private FloatingActionButton mEditButton;
    private EditText mSubtask;
    private ViewPager mViewPager;
    public static boolean isEdit;
    private TextView mSubtitleView;
    private TextView mDateView;
    private Button mAddButton;
    private ImageView mPicImageView;
    private static final int REQUEST_PHOTOZ = 8;

    public static boolean isIsEdit() {
        return isEdit;
    }

    public static void setIsEdit(boolean isEdit) {
        AssignmentDetailFragment.isEdit = isEdit;
    }

    public static AssignmentDetailFragment newInstance(UUID assignmentID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ASSIGNMENT_ID, assignmentID);
        setIsEdit(false);

        AssignmentDetailFragment fragment = new AssignmentDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID assignmentID = (UUID) getArguments().getSerializable(ARG_ASSIGNMENT_ID);
        mAssignments = AssignmentLab.get(getActivity()).getAssignment(assignmentID);
        mPhotoFile = AssignmentLab.get(getActivity()).getPhotoFile(mAssignments);
        mPhotoFilez = AssignmentLab.get(getActivity()).getPhotoFilez(mAssignments);

    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assignment_info, container, false);

        mTitleTextView = (TextView) v.findViewById(R.id.assignment_name);
        mDateTextView = (TextView) v.findViewById(R.id.assignment_due);
        mSubjectTextView = (TextView) v.findViewById(R.id.assignment_subject);
        mReminderTextView = (TextView) v.findViewById(R.id.assignment_reminder);
        mTypeTextView = (TextView) v.findViewById(R.id.assignment_type);
        mPictureImageView = (ImageView) v.findViewById(R.id.assignment_picture);
        mDeleteButton = (Button) v.findViewById(R.id.delete_btn);
        mPhotoFile = AssignmentLab.get(getActivity()).getPhotoFile(mAssignments);
        mSubtitleView = (TextView) v.findViewById(R.id.assignment_subtask);
        mDateView = (TextView) v.findViewById(R.id.subtask_date);
        mAddButton = (Button) v.findViewById(R.id.add_btn);
        mPicImageView = (ImageView) v.findViewById(R.id.assignment_picturez);
        mPhotoFilez = AssignmentLab.get(getActivity()).getPhotoFilez(mAssignments);


        final com.getbase.floatingactionbutton.FloatingActionsMenu fabs = (FloatingActionsMenu) v.findViewById(R.id.fab_expand_menu_button);

        final com.getbase.floatingactionbutton.FloatingActionButton floatingActionButton = v.findViewById(R.id.action_1);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent captureImages = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        Uri uri = FileProvider.getUriForFile(getActivity(),
                                "com.example.assignmentapplication.fileprovider",
                                mPhotoFilez);
                        captureImages.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                        List<ResolveInfo> cameraActivities = getActivity()
                                .getPackageManager().queryIntentActivities(captureImages,
                                        PackageManager.MATCH_DEFAULT_ONLY);

                        for (ResolveInfo activity : cameraActivities) {
                            getActivity().grantUriPermission(activity.activityInfo.packageName,
                                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }

                        startActivityForResult(captureImages, REQUEST_PHOTOZ);


            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton floatingActionButtonz = v.findViewById(R.id.action_2);

        floatingActionButtonz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignmentLab.get(getActivity()).getAssignment(mAssignments.getId());
                setIsEdit(true);
                Intent intent = AssignmentPagerActivity
                        .newIntent(getActivity(), mAssignments.getId());
                startActivity(intent);

            }
        });

        final com.getbase.floatingactionbutton.FloatingActionButton floatingActionButtond = v.findViewById(R.id.action_3);

        floatingActionButtond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add subtask");


                final EditText input = new EditText(getActivity());
                input.setHint("Enter subtask");
                input.setText(mAssignments.getSubTask());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(input);

                final EditText inputz = new EditText(getActivity());
                inputz.setHint("Enter subtask date");
                inputz.setText(mAssignments.getSubDate());
                inputz.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                layout.addView(inputz);


                builder.setView(layout);
                input.requestFocus();

                input.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mAssignments.setSubTask(s.toString());

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                inputz.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mAssignments.setSubdate(s.toString());

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSubtitleView.setText(mAssignments.getSubTask());
                        mDateView.setText(mAssignments.getSubDate());

                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(layout.getWindowToken(), 0);
                        dialog.dismiss();
                        fabs.collapse();




                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(layout.getWindowToken(), 0);
                builder.show();
            }
        });






        Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
        Bitmap bitmapz = PictureUtils.getScaledBitmap(mPhotoFilez.getPath(), getActivity());

      /*  if(bitmap == null){
            mPicImageView.setVisibility(View.INVISIBLE);
        }*/

        mTitleTextView.setText(mAssignments.getTitle());
        Date date = mAssignments.getDate();
        String formattedDate = DateFormatter.formatDateAsString(DateFormat.LONG, date);
        String formattedTime = DateFormatter.formatDateAsTimeString(DateFormat.SHORT, date);
        mTypeTextView.setText(mAssignments.getType());
        mDateTextView.setText(formattedDate + " @ " + formattedTime);
        mSubjectTextView.setText(mAssignments.getSubject());
        Date dates = mAssignments.getReminderDate();
        String formattedDates = DateFormatter.formatDateAsString(DateFormat.LONG, dates);
        String formattedTimes = DateFormatter.formatDateAsTimeString(DateFormat.SHORT, dates);
        mReminderTextView.setText(formattedDates + " @ " + formattedTimes);

        mPictureImageView.setImageBitmap(bitmap);

        mPicImageView.setImageBitmap(bitmapz);
        mSubtitleView.setText(mAssignments.getSubTask());
        mDateView.setText(mAssignments.getSubDate());


        mPictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
                displayPopUpImage(bitmap);

            }
        });

        mPicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmapz = PictureUtils.getScaledBitmap(mPhotoFilez.getPath(), getActivity());
                displayPopUpImage(bitmapz);

            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UUID id = mAssignments.getId();
                mAssignments.setSubTask(mSubtitleView.getText().toString());
                mAssignments.setSubdate(mDateView.getText().toString());
                AssignmentLab.get(getActivity())
                        .updateAssignment(mAssignments);
                getActivity().finish();


            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
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

    private void displayPopUpImage(Bitmap imageBitmap) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageBitmap(imageBitmap);

        alertDialog.setView(imageView);
        alertDialog.create();
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTOZ) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.example.assignmentapplication.fileprovider",
                    mPhotoFilez);

            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }

    private void updatePhotoView() {
        if (mPhotoFilez == null || !mPhotoFilez.exists()) {
            mPicImageView.setImageDrawable(null);
        } else {
            Bitmap bitmapz = PictureUtils.getScaledBitmap(
                    mPhotoFilez.getPath(), getActivity());
            mPicImageView.setImageBitmap(bitmapz);
        }
    }
}
