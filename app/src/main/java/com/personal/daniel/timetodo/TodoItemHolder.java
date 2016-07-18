package com.personal.daniel.timetodo;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by daniel on 6/20/16.
 */
public class TodoItemHolder {
    private static TodoItemHolder sTodoItemHolder;

    private List<TodoItem> mTodoItems;
    private Context mContext;
    private SQLiteDatabase mItemDb;
    //Todo: Create update item menu
    private TodoItemHolder(Context context){
        mContext = context.getApplicationContext();
        mItemDb = new TodoItemDbHelper(mContext).getWritableDatabase();
        //mTodoItems = new ArrayList<TodoItem>();
    }

    public static TodoItemHolder get(Context context){
        if(sTodoItemHolder==null) {
            sTodoItemHolder = new TodoItemHolder(context);
        }
            return sTodoItemHolder;

    }

    private ContentValues getContentValues(TodoItem item){
        ContentValues values = new ContentValues();
        values.put(ItemDbSchema.ItemTable.Cols.TITLE,item.getName());
        values.put(ItemDbSchema.ItemTable.Cols.TIME,item.getTimeRemaining());
        values.put(ItemDbSchema.ItemTable.Cols.UUID,item.getUUID().toString());

        return values;
    }

    public List<TodoItem> getTodoItems() {
        List<TodoItem> items = new ArrayList<TodoItem>();

        ItemCursorWrapper cursor = queryCrimes(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return items;
    }

    public void addItem(TodoItem item){
        ContentValues v = getContentValues(item);

        mItemDb.insert(ItemDbSchema.ItemTable.NAME,null,v);
    }
    public void addItem(int length, String title){
        addItem(new TodoItem(length,title));
    }

    public TodoItem getItem(UUID id){
        ItemCursorWrapper cursor = queryCrimes(
                ItemDbSchema.ItemTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getItem();
        } finally {
            cursor.close();
        }

    }

    public void updateItem(TodoItem item){
        String uuid = item.getUUID().toString();
        ContentValues v = getContentValues(item);

        mItemDb.update(ItemDbSchema.ItemTable.NAME,v,ItemDbSchema.ItemTable.Cols.UUID + " = ?",
                new String[] { uuid });
    }

    private ItemCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mItemDb.query(
                ItemDbSchema.ItemTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null); // orderBy
        return new ItemCursorWrapper(cursor);
    }

    public void delete(TodoItem item){
        String uuid = item.getUUID().toString();
        mItemDb.delete(ItemDbSchema.ItemTable.NAME,ItemDbSchema.ItemTable.Cols.UUID + " = ?",new String[] { uuid });

    }
}
