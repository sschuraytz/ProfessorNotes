package com.example.macbook.professor_notes.database;

public class RecordDbSchema {
    public static final class RecordTable {
        public static final String NAME = "records";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String FIRST = "first";
            public static final String LAST = "last";
            public static final String COURSE = "course";
            public static final String NOTES = "notes";
            public static final String DATE = "date";
            public static final String DEALT = "dealt";
        }
    }
}
