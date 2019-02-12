package com.example.macbook.professor_notes;

import java.util.Date;
import java.util.UUID;

public class Record {
    private UUID mId;
    private String mFirstName;
    private String mLastName;
    private String mCourse;
    private String mNotes;
    private Date mDate;
    private boolean mDealtWith;

    public Record() {
        this(UUID.randomUUID());
    }

    public Record(UUID id) {
        mId = id;
        mDate  = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) { mFirstName = firstName; }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) { mLastName = lastName; }

    public String getCourse() { return mCourse; }

    public void setCourse(String course) { mCourse = course; }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isDealtWith() {
        return mDealtWith;
    }

    public void setDealtWith(boolean solved) {
        mDealtWith = solved;
    }

}
