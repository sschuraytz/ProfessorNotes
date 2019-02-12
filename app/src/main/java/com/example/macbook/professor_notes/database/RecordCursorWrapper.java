package com.example.macbook.professor_notes.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.macbook.professor_notes.Record;
import com.example.macbook.professor_notes.database.RecordDbSchema.RecordTable;

import java.util.Date;
import java.util.UUID;

public class RecordCursorWrapper extends CursorWrapper {
    public RecordCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Record getRecord() {
        String uuidString = getString(getColumnIndex(RecordTable.Cols.UUID));
        String first = getString(getColumnIndex(RecordTable.Cols.FIRST));
        String last = getString(getColumnIndex(RecordTable.Cols.LAST));
        String course = getString(getColumnIndex(RecordTable.Cols.COURSE));
        String notes = getString(getColumnIndex(RecordTable.Cols.NOTES));
        long date = getLong(getColumnIndex((RecordTable.Cols.DATE)));
        int isSolved = getInt(getColumnIndex(RecordTable.Cols.DEALT));


        Record record = new Record(UUID.fromString(uuidString));
        record.setFirstName(first);
        record.setLastName(last);
        record.setCourse(course);
        record.setNotes(notes);
        record.setDate(new Date(date));
        record.setDealtWith(isSolved != 0);

        return record;
    }
}
