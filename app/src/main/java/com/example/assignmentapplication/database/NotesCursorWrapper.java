package com.example.assignmentapplication.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.assignmentapplication.Assignments;
import com.example.assignmentapplication.Notes;

import java.util.Date;
import java.util.UUID;

public class NotesCursorWrapper extends CursorWrapper {
    public NotesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Notes getNotes() {
        String uuidString = getString(getColumnIndex(NotesDbSchema.NotesTable.Cols.UUID));
        String text = getString(getColumnIndex(NotesDbSchema.NotesTable.Cols.TEXT));
        String header = getString(getColumnIndex(NotesDbSchema.NotesTable.Cols.HEADER));



        Notes notes = new Notes(UUID.fromString(uuidString));
        notes.setText(text);
        notes.setHeader(header);



        return notes;


    }
}
