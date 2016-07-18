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
        mTimer = new CountDownTimer(duration*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeRemaining--;
            }

            @Override
            public void onFinish() {

            }
        };
    }
    public TodoItem(UUID id,int duration){
        mUUID = id;
        mTimer = new CountDownTimer(duration*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeRemaining--;
            }

            @Override
            public void onFinish() {

            }
        };

    }
    public UUID getUUID() {
        return mUUID;
    }
    public void startTimer(){
        mTimer.start();
    }

    public int getTimeRemaining(){
        return mTimeRemaining;
    }
}
