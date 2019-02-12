package com.example.macbook.professor_notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.macbook.professor_notes.database.RecordBaseHelper;
import com.example.macbook.professor_notes.database.RecordCursorWrapper;
import com.example.macbook.professor_notes.database.RecordDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordLab {
    private static RecordLab sRecordLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static RecordLab get(Context context) {
        if(sRecordLab == null) {
            sRecordLab = new RecordLab(context);
        }
        return sRecordLab;
    }

    public RecordLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new RecordBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addRecord(Record record) {
        ContentValues values = getContentValues(record);

        mDatabase.insert(RecordDbSchema.RecordTable.NAME,
                null, values);
    }

    public List<Record> getRecords() {
        List<Record> records = new ArrayList<>();

        RecordCursorWrapper cursor = queryRecords(null, null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                records.add(cursor.getRecord());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return records;
    }

    public Record getRecord(UUID id) {

        RecordCursorWrapper cursor = queryRecords(
                RecordDbSchema.RecordTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try {
            if(cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getRecord();
        } finally {
            cursor.close();
        }
    }

    public void updateRecord(Record record) {
        String uuidString = record.getId().toString();
        ContentValues values = getContentValues(record);

        mDatabase.update(RecordDbSchema.RecordTable.NAME, values,
                RecordDbSchema.RecordTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private RecordCursorWrapper queryRecords(String whereClause,
                                             String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                RecordDbSchema.RecordTable.NAME,
                null, //null selects all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new RecordCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Record record) {
        ContentValues values = new ContentValues();
        values.put(RecordDbSchema.RecordTable.Cols.UUID, record.getId().toString());
        values.put(RecordDbSchema.RecordTable.Cols.FIRST, record.getFirstName());
        values.put(RecordDbSchema.RecordTable.Cols.LAST, record.getLastName());
        values.put(RecordDbSchema.RecordTable.Cols.COURSE, record.getCourse());
        values.put(RecordDbSchema.RecordTable.Cols.NOTES, record.getNotes());
        values.put(RecordDbSchema.RecordTable.Cols.DATE, record.getDate().getTime());
        values.put(RecordDbSchema.RecordTable.Cols.DEALT, record.isDealtWith() ? 1 : 0);

        return values;
    }

    public void deleteRecord(UUID recordId) {

        String uuidString = recordId.toString();

        mDatabase.delete(RecordDbSchema.RecordTable.NAME,
                RecordDbSchema.RecordTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }
}

