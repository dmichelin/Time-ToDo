package com.personal.daniel.timetodo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by daniel on 6/28/16.
 */
public class CardFragment extends Fragment {


    private Button mAddButton;
    private Button mStartButton;
    private TodoItemHolder mItemHolder;
    private CardAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static CardFragment newInstance(){
        return new CardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Create the view
        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.activity_scrolling,container,false);

        mItemHolder = TodoItemHolder.get(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.card_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(llm);

        updateUI();
        mAddButton = (Button) view.findViewById(R.id.add_item_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),CreateActivity.class);
                startActivity(intent);
                updateUI();

            }
        });

        mStartButton= (Button) view.findViewById(R.id.start_timer_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }

        });



        return view;
    }

    /**
     * Provides functionality for the timer side of the application.
     */
    public void startTimer(){
        TodoItem item = mAdapter.getItem(0);
        if (!item.equals(null)) {
            int time = item.getTimeRemaining();
            CountDownTimer timer = new CountDownTimer(time * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Toast.makeText(getContext(), millisUntilFinished+"", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {
                    Toast.makeText(getContext(), "Finished timer", Toast.LENGTH_SHORT).show();
                    mAdapter.onItemDismiss(0);
                    if( !(mAdapter.getItem(0)==null) ){
                        startTimer();
                    }
                }
            };
            timer.start();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ItemTouchHelper.Callback callback =
                new ItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }
    // This is the adapter for the view
    public class CardAdapter extends RecyclerView.Adapter<TodoCardHolder> implements ItemTouchAdapter {




        private List<TodoItem> mItemList;

        public CardAdapter(List<TodoItem> list){
            this.mItemList = list;
        }

        /**
         * Returns the item at a given position
         * @param pos
         * @return
         */
        public TodoItem getItem(int pos){
            if(pos < mItemList.size()){
                return  mItemList.get(pos);
            }
            else return null;
        }

        public void setItemList(List<TodoItem> itemList) {
            mItemList = itemList;
        }

        /** Currently not implemented. Provides adapter functionality to move items in a list from holding it down
         *
         * @param fromPos
         * @param toPos
         * @return
         */
        @Override
        public boolean onItemMove(int fromPos, int toPos){
            if(fromPos<toPos){
                for (int i = fromPos; i < toPos; i++) {
                    Collections.swap(mItemList, i, i + 1);
                }
            }
            else {
                for (int i = fromPos; i > toPos; i--) {
                    Collections.swap(mItemList, i, i - 1);
                }
            }
            notifyItemMoved(fromPos, toPos);
            return true;
        }

        /**
         * Provides functionality for the swipe. It currently deletes the item out of the temporary holder as well as the database
         * @param pos
         */
        @Override
        public void onItemDismiss(int pos){

            TodoItem itemToRemove = mItemList.get(pos);
            mItemHolder.delete(itemToRemove);
            mItemList.remove(pos);
            notifyItemRemoved(pos);
        }



        @Override
        public int getItemCount(){
            return mItemList.size();
        }

        /**
         *  Provides functionality to each card
         * @param cardHolder
         * @param i
         */
        @Override
        public void onBindViewHolder(TodoCardHolder cardHolder, int i){
            TodoItem item = mItemList.get(i);
            cardHolder.setItem(item);
            cardHolder.vName.setText(item.getName());
            cardHolder.vTime.setText(item.getTimeRemaining()+"");
        }
        @Override
        public TodoCardHolder onCreateViewHolder(ViewGroup v, int i){
            View itemView = LayoutInflater.from(v.getContext()).inflate(R.layout.card_layout,v,false);
            return new TodoCardHolder(itemView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        mItemHolder = TodoItemHolder.get(getActivity());
        List<TodoItem> items = mItemHolder.getTodoItems();
        if (mAdapter == null) {
            mAdapter = new CardAdapter(items);

            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItemList(items);
            mAdapter.notifyDataSetChanged();
        }
    }

    public class TodoCardHolder extends RecyclerView.ViewHolder {

        public static final String NAME = "name";
        public static final String UUID = "uuid";
        public static final String TIME = "time";


        protected TextView vName;
        protected TextView vTime;
        protected TodoItem vItem;
        protected View view;

        public TodoCardHolder(View v){
            super(v);
            view = v;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(),CreateActivity.class);
                    intent.putExtra(TIME,vItem.getTimeRemaining());

                    intent.putExtra(NAME,vItem.getName());
                    intent.putExtra(UUID,vItem.getUUID());
                    startActivity(intent);

                }
            });
            vName = (TextView) v.findViewById(R.id.item_name);
            vTime = (TextView) v.findViewById(R.id.item_time);

        }
        public void setItem(TodoItem item){
            vItem = item;
        }

    }




}

