package com.example.assignmentapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignmentapplication.database.AssignmentBaseHelper;
import com.example.assignmentapplication.database.AssignmentCursorWrapper;
import com.example.assignmentapplication.database.AssignmentDbSchema;
import com.example.assignmentapplication.database.NotesBaseHelper;
import com.example.assignmentapplication.database.NotesCursorWrapper;
import com.example.assignmentapplication.database.NotesDbSchema;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotesLab {
    private static NotesLab sNotesLab;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static NotesLab get(Context context) {
        if (sNotesLab == null) {
            sNotesLab = new NotesLab(context);
        }
        return sNotesLab;
    }

    private NotesLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new NotesBaseHelper(mContext)
                .getWritableDatabase();


    }

    public void deleteNotes(Notes notes) {
        String uuidString = notes.getId().toString();

        mDatabase.delete(NotesDbSchema.NotesTable.NAME, NotesDbSchema.NotesTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public void addNotes(Notes c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(NotesDbSchema.NotesTable.NAME, null, values);

    }

    public List<Notes> getNotes() {

        List<Notes> notes = new ArrayList<>();

        NotesCursorWrapper cursor = queryNotes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                notes.add(cursor.getNotes());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return notes;

    }

    public Notes getNotes(UUID id) {
        NotesCursorWrapper cursor = queryNotes(
                NotesDbSchema.NotesTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getNotes();
        } finally {
            cursor.close();
        }


    }



    public File getPhotoFile(Notes notes) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, notes.getPhotoFilename());
    }



    public void updateNotes(Notes notes) {
        String uuidString = notes.getId().toString();
        ContentValues values = getContentValues(notes);

        mDatabase.update(NotesDbSchema.NotesTable.NAME, values,
                NotesDbSchema.NotesTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private NotesCursorWrapper queryNotes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                NotesDbSchema.NotesTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new NotesCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Notes notes) {
        ContentValues values = new ContentValues();
        values.put(NotesDbSchema.NotesTable.Cols.UUID, notes.getId().toString());
        values.put(NotesDbSchema.NotesTable.Cols.TEXT, notes.getText());
        values.put(NotesDbSchema.NotesTable.Cols.HEADER, notes.getHeader());


        return values;
    }

}
