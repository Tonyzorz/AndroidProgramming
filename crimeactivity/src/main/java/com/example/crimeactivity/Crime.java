package com.example.crimeactivity;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate = new Date();
    private boolean mSolve;


    public Crime() {
    }

    public Crime(UUID id) {
        //고유한 식별자를 생성한다
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId(){
        return mId;
    }

    public void setTitle(String mTitle){
        this.mTitle = mTitle;
    }

    public String getTitle(){
        return mTitle;
    }

    public Date getDate(){return mDate;}

    public boolean getSolve(){return mSolve;}

    public void setDate(Date mDate){this.mDate = mDate;}

    public void setSolve(boolean mSolve){this.mSolve = mSolve;}


}
