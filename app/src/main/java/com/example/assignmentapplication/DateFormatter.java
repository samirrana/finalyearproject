package com.example.assignmentapplication;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;



public class DateFormatter {

    public static String formatDateAsString(int dateStyle, Date date) {
        DateFormat formatter = DateFormat.getDateInstance(dateStyle, Locale.getDefault());
        return formatter.format(date);
    }


    public static String formatDateAsTimeString(int dateStyle, Date date) {
        DateFormat formatter = DateFormat.getTimeInstance(dateStyle, Locale.getDefault());
        return formatter.format(date);
    }
}
