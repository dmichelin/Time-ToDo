package com.personal.daniel.timetodo;

import android.content.Intent;
import android.os.Bundle;
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


    private FloatingActionButton mAddButton;
    private TodoItemHolder mItemHolder;
    private CardAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static CardFragment newInstance(){
        return new CardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.activity_scrolling,container,false);

        mItemHolder = TodoItemHolder.get(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.card_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(llm);

        updateUI();
        mAddButton = (FloatingActionButton) view.findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),CreateActivity.class);
                startActivity(intent);
                updateUI();

            }
        });


        return view;
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

        //Todo: Create an onclick listener to open a todo detail. Create corresponding fragment



        private List<TodoItem> mItemList;

        public CardAdapter(List<TodoItem> list){
            this.mItemList = list;
        }

        public void setItemList(List<TodoItem> itemList) {
            mItemList = itemList;
        }

        //Todo: Fix this, it currently uses the temporary list. Must implement methods to
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
            String posText = pos +"";

            TodoItem itemToRemove = mItemList.get(pos);
            mItemHolder.delete(itemToRemove);
            mItemList.remove(pos);
            notifyItemRemoved(pos);
        }

        @Override
        public int getItemCount(){
            return mItemList.size();
        }

        @Override
        public void onBindViewHolder(TodoCardHolder cardHolder, int i){
            TodoItem item = mItemList.get(i);
            cardHolder.setItem(item);
            cardHolder.vName.setText(item.getName());
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


        protected TextView vName;
        protected TodoItem vItem;
        protected View view;

        public TodoCardHolder(View v){
            super(v);
            view = v;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(),CreateActivity.class);
                    intent.putExtra(NAME,vItem.getName());
                    intent.putExtra(UUID,vItem.getUUID());
                    startActivity(intent);

                }
            });
            vName = (TextView) v.findViewById(R.id.item_name);

        }
        public void setItem(TodoItem item){
            vItem = item;
        }

    }




}

