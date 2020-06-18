package com.example.assignmentapplication;

import java.util.Date;
import java.util.UUID;

public class Assignments {
     UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private int mFormat;
    private String mSubject;
    private boolean mPriority;
    private String mPrior;
    private Date mReminderDate;
    private String mType;
    private String mSubTask;
    private String mSubDate;


    public Assignments() {
        this(UUID.randomUUID());
    }

    public Assignments(UUID id) {
        mId = id;
        mDate = new Date();
        mReminderDate = new Date();

    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }


    public void setFormat(int format) {
        mFormat = format;
    }

    public int isFormat() {
        return mFormat;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }


    public boolean isPriority() {
        return mPriority;
    }

    public void setPriority(boolean priority) {
        mPriority = priority;
    }


    public String getPrior() {
        return mPrior;
    }

    public void setPrior(String radio) {
        mPrior = radio;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }

    public String getPhotoFiled() { return "IMG" + getId().toString() + ".jpg"; }

    public Date getReminderDate() {
        return mReminderDate;
    }

    public void setReminderDate(Date reminder) {
        mReminderDate = reminder;
    }

    public String getType() { return mType;}

    public void setType (String type) { mType = type;}

    public String getSubTask() {
        return mSubTask;
    }

    public void setSubTask(String subtask) {
        mSubTask = subtask;
    }

    public String getSubDate() {
        return mSubDate;
    }

    public void setSubdate(String subdate) {
        mSubDate = subdate;
    }



}
