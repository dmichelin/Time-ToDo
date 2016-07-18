package com.personal.daniel.timetodo;

/**
 * Created by daniel on 7/1/16.
 */
public interface ItemTouchAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
