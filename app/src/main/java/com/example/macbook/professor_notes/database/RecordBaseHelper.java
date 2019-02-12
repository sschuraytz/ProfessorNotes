package com.example.macbook.professor_notes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.macbook.professor_notes.database.RecordDbSchema.RecordTable;

public class RecordBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "recordBase.db";

    public RecordBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + RecordDbSchema.RecordTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                RecordTable.Cols.UUID + ", " +
                RecordTable.Cols.FIRST + ", " +
                RecordTable.Cols.LAST + ", " +
                RecordTable.Cols.COURSE + ", " +
                RecordTable.Cols.NOTES + ", " +
                RecordTable.Cols.DATE + ", " +
                RecordTable.Cols.DEALT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
