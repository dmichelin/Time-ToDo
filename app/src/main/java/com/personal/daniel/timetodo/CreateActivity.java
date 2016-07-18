package com.personal.daniel.timetodo;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by daniel on 6/20/16.
 */
public class CreateActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return AddItemFragment.newInstance();
    }
}
