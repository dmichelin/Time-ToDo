package com.personal.daniel.timetodo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by daniel on 7/1/16.
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback
{
    private final ItemTouchAdapter mTouchAdapter;

    public ItemTouchHelperCallback(ItemTouchAdapter adapter){
        mTouchAdapter = adapter;
    }
    /** Gets which movement flags to allow
     *
     * @param view
     * @param holder
     * @return movement flags
     */
    @Override
    public int getMovementFlags(RecyclerView view, RecyclerView.ViewHolder holder){
        int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START|ItemTouchHelper.END;
        return makeMovementFlags(dragFlags,swipeFlags);
    }
    /*
        Determines whether or not dragging is enabled
     */
    @Override
    public boolean isLongPressDragEnabled() {
    return false;
    }
    /*
        Determines whether or not swiping is enabled
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder holder, RecyclerView.ViewHolder target){
        mTouchAdapter.onItemMove(holder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder holder, int direction){
        mTouchAdapter.onItemDismiss(holder.getAdapterPosition());
    }
}
