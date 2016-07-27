package com.personal.daniel.timetodo;

import android.os.CountDownTimer;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by daniel on 6/20/16.
 */
public class TodoItem {
    private CountDownTimer mTimer;
    private int mTimeRemaining;
    private String mName;



    private UUID mUUID;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public TodoItem(int duration, String name){
        mUUID = UUID.randomUUID();
        mName = name;
        mTimeRemaining = duration;

    }
    public TodoItem(UUID id,int duration){
        mUUID = id;
        mTimeRemaining = duration;

    }
    public UUID getUUID() {
        return mUUID;
    }
    public void setTimeRemaining( int time){
        mTimeRemaining=time;
    }

    public int getTimeRemaining(){
        return mTimeRemaining;
    }
}
