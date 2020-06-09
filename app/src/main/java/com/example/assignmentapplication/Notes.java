package com.example.assignmentapplication;

import java.util.Date;
import java.util.UUID;

public class Notes {
    private UUID mId;
    private String mText;
    private String mHeader;



    public Notes() {
        this(UUID.randomUUID());
    }

    public Notes(UUID id) {
        mId = id;

    }

    public UUID getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String header) {
        mHeader = header;
    }




}
