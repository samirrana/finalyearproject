package com.example.assignmentapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignmentapplication.database.AssignmentBaseHelper;
import com.example.assignmentapplication.database.AssignmentCursorWrapper;
import com.example.assignmentapplication.database.AssignmentDbSchema;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AssignmentLab {
    private static AssignmentLab sAssignmentLab;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    Date c = Calendar.getInstance().getTime();


    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c);

    public static AssignmentLab get(Context context) {
        if (sAssignmentLab == null) {
            sAssignmentLab = new AssignmentLab(context);
        }
        return sAssignmentLab;
    }

    private AssignmentLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AssignmentBaseHelper(mContext)
                .getWritableDatabase();


    }


    public void deleteAssignment(Assignments assignments) {
        String uuidString = assignments.getId().toString();

        mDatabase.delete(AssignmentDbSchema.AssignmentTable.NAME, AssignmentDbSchema.AssignmentTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public void addAssignments(Assignments c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(AssignmentDbSchema.AssignmentTable.NAME, null, values);

    }

    public List<Assignments> getAssignments() {

        List<Assignments> assignments = new ArrayList<>();

        AssignmentCursorWrapper cursor = queryAssignments(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                assignments.add(cursor.getAssignment());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return assignments;

    }



    public List<Assignments> getCurrentAssignments(Date date) {



        List<Assignments> assignments = new ArrayList<>();

        AssignmentCursorWrapper cursor = queryAssignments(AssignmentDbSchema.AssignmentTable.Cols.DATE + " = ?", new String[]{date.toString()});


        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                assignments.add(cursor.getAssignment());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return assignments;

    }
    public Assignments getAssignment(UUID id) {
        AssignmentCursorWrapper cursor = queryAssignments(
                AssignmentDbSchema.AssignmentTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getAssignment();
        } finally {
            cursor.close();
        }


    }


    public File getPhotoFile(Assignments assignment) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, assignment.getPhotoFilename());
    }

    public File getPhotoFilez(Assignments assignment) {
        File filesdx = mContext.getFilesDir();
        return new File(filesdx, assignment.getPhotoFiled());
    }

    public void updateAssignment(Assignments assignment) {
        String uuidString = assignment.getId().toString();
        ContentValues values = getContentValues(assignment);

        mDatabase.update(AssignmentDbSchema.AssignmentTable.NAME, values,
                AssignmentDbSchema.AssignmentTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private AssignmentCursorWrapper queryAssignments(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AssignmentDbSchema.AssignmentTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new AssignmentCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Assignments assignment) {
        ContentValues values = new ContentValues();
        values.put(AssignmentDbSchema.AssignmentTable.Cols.UUID, assignment.getId().toString());
        values.put(AssignmentDbSchema.AssignmentTable.Cols.TITLE, assignment.getTitle());
        values.put(AssignmentDbSchema.AssignmentTable.Cols.DATE, assignment.getDate().getTime());
        values.put(AssignmentDbSchema.AssignmentTable.Cols.SOLVED, assignment.isSolved() ? 1 : 0);
        values.put(AssignmentDbSchema.AssignmentTable.Cols.SUBJECT, assignment.getSubject());
        values.put(AssignmentDbSchema.AssignmentTable.Cols.REMINDER, assignment.getReminderDate().getTime());
        values.put(AssignmentDbSchema.AssignmentTable.Cols.TYPE, assignment.getType());
        values.put(AssignmentDbSchema.AssignmentTable.Cols.SUBTASK, assignment.getSubTask());
        values.put(AssignmentDbSchema.AssignmentTable.Cols.SUBDATE, assignment.getSubDate());

        return values;
    }

}
