package com.personal.daniel.timetodo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.TimePicker;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by daniel on 6/28/16.
 */
public class AddItemFragment extends Fragment {

    private TodoItemHolder mItemHolder;
    private Button mCreateButton;
    private TimePicker mTimeSpinner;
    private EditText mDescription;

    public static AddItemFragment newInstance(){
        return new AddItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        mItemHolder = TodoItemHolder.get(getActivity());



        View view = inflater.inflate(R.layout.create_layout,container,false);
        mDescription = (EditText) view.findViewById(R.id.editText);

        mTimeSpinner = (TimePicker) view.findViewById(R.id.timePicker);
        mTimeSpinner.setIs24HourView(true);
        mCreateButton = (Button) view.findViewById(R.id.create_button);

        // if the name is not null, then the program is updating an item
        if(getActivity().getIntent().getSerializableExtra(CardFragment.TodoCardHolder.NAME)!=null){
            mTimeSpinner.setHour(getActivity().getIntent().getIntExtra(CardFragment.TodoCardHolder.TIME,0));
            mDescription.setText(getActivity().getIntent().getSerializableExtra(CardFragment.TodoCardHolder.NAME).toString());
            mCreateButton.setText("Update");
        }



        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( getActivity().getIntent().getSerializableExtra(CardFragment.TodoCardHolder.UUID)!=null){
                    mCreateButton.setText("Update");
                    TodoItem item = mItemHolder.getItem(UUID.fromString(getActivity().getIntent().getSerializableExtra(CardFragment.TodoCardHolder.UUID).toString()));
                    item.setTimeRemaining(mTimeSpinner.getHour());
                    item.setName(mDescription.getText().toString());

                    mItemHolder.updateItem(item);

                }
                else {
                    mItemHolder.addItem(mTimeSpinner.getHour(), mDescription.getText().toString());

                }
                getActivity().finish();
            }
        });


        return view;
    }



}
