package com.example.assignmentapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignmentapplication.database.AssignmentDbSchema.AssignmentTable;

public class NotesBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "notes.db";

    public NotesBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NotesDbSchema.NotesTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                NotesDbSchema.NotesTable.Cols.UUID + ", " +
                NotesDbSchema.NotesTable.Cols.TEXT + ", " +
                NotesDbSchema.NotesTable.Cols.HEADER +



                ")"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
