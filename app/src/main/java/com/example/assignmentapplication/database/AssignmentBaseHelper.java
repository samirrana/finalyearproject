package com.example.assignmentapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignmentapplication.database.AssignmentDbSchema.AssignmentTable;

public class AssignmentBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public AssignmentBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + AssignmentTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                AssignmentTable.Cols.UUID + ", " +
                AssignmentTable.Cols.TITLE + ", " +
                AssignmentTable.Cols.DATE + ", " +
                AssignmentTable.Cols.SOLVED +", " +
                AssignmentTable.Cols.SUBJECT + ", " +
                AssignmentTable.Cols.PRIORITY +



                ")"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
