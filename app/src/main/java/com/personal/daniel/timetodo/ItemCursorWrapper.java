package com.personal.daniel.timetodo;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

/**
 * Created by daniel on 6/29/16.
 */
public class ItemCursorWrapper extends CursorWrapper {
    public ItemCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public TodoItem getItem(){
        String uuid = getString(getColumnIndex(ItemDbSchema.ItemTable.Cols.UUID));
        String title = getString(getColumnIndex(ItemDbSchema.ItemTable.Cols.TITLE));
        int time = getInt(getColumnIndex(ItemDbSchema.ItemTable.Cols.TIME));

        TodoItem item = new TodoItem(UUID.fromString(uuid),time);
        item.setName(title);

        return item;
    }
}
