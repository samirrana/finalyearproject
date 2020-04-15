package com.example.assignmentapplication.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.assignmentapplication.Assignments;

import java.util.Date;
import java.util.UUID;

public class AssignmentCursorWrapper extends CursorWrapper {
    public AssignmentCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Assignments getAssignment() {
        String uuidString = getString(getColumnIndex(AssignmentDbSchema.AssignmentTable.Cols.UUID));
        String title = getString(getColumnIndex(AssignmentDbSchema.AssignmentTable.Cols.TITLE));
        long date = getLong(getColumnIndex(AssignmentDbSchema.AssignmentTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(AssignmentDbSchema.AssignmentTable.Cols.SOLVED));
        String subject = getString(getColumnIndex(AssignmentDbSchema.AssignmentTable.Cols.SUBJECT));
        int isPriority = getInt(getColumnIndex(AssignmentDbSchema.AssignmentTable.Cols.PRIORITY));





        Assignments assignment = new Assignments(UUID.fromString(uuidString));
        assignment.setTitle(title);
        assignment.setDate(new Date(date));
        assignment.setSolved(isSolved != 0);
        assignment.setSubject(subject);
        assignment.setPriority(isPriority != 0);








        return assignment;


    }
}
