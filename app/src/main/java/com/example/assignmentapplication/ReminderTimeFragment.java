package com.example.assignmentapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Chris on 2/10/2017.
 */

public class ReminderTimeFragment extends DialogFragment {

    public static final String ARG_DATES = "date";

    public int notificationId = 1;

    public static final String EXTRA_TIMES =
            "com.example.android.assignmentapplication.time";

    private TimePicker mReminderTimePicker;

    public Assignments mAssignments;

    private static final String ARG_ASSIGNMENT_ID = "assignment_id";



    // For attaching arguments to the dialog fragment
    public static ReminderTimeFragment newInstance(Date date, UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATES, date);
        args.putSerializable(ARG_ASSIGNMENT_ID, uuid);




        ReminderTimeFragment fragment = new ReminderTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATES);


        UUID assignmentID = (UUID) getArguments().getSerializable(ARG_ASSIGNMENT_ID);
        mAssignments = AssignmentLab.get(getActivity()).getAssignment(assignmentID);



        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);


        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);


        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);


        mReminderTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);
        mReminderTimePicker.setIs24HourView(true);




        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                Date dates = mAssignments.getDate();

                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                String targetdate = dateFormat.format(dates);



                                Intent intents = new Intent(getActivity(), AlarmReceiver.class);
                                intents.putExtra("notificationId", notificationId);
                                intents.putExtra("todo", " Assignment " + mAssignments.getTitle() + " is due on " + targetdate);


                                PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(), 0,
                                        intents, PendingIntent.FLAG_CANCEL_CURRENT);

                                AlarmManager alarm = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);


                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                int hour;
                                int minutes;

                                hour = mReminderTimePicker.getCurrentHour();
                                minutes = mReminderTimePicker.getCurrentMinute();

                                Calendar startTime = Calendar.getInstance();
                                startTime.set(Calendar.HOUR_OF_DAY, hour);
                                startTime.set(Calendar.MINUTE, minutes);
                                startTime.set(Calendar.SECOND, 0);
                                long alarmStartTime = startTime.getTimeInMillis();

                                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);


                                Date date = new GregorianCalendar(year, month, day, hour, minutes).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                        })
                .create();
    }


    private void sendResult(int resultCode, Date newDate) {
        if (getTargetFragment() != null) {

            Intent intent = new Intent();
            intent.putExtra(EXTRA_TIMES, newDate);
            getTargetFragment()
                    .onActivityResult(getTargetRequestCode(), resultCode, intent);
            dismiss();
            return;
        }


        // Otherwise fragment was called from an activity
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIMES, newDate);
        getActivity().setResult(resultCode, intent);
        getActivity().finish();
    }
}
