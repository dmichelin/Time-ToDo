package com.personal.daniel.timetodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by daniel on 6/29/16.
 */
public class TodoItemDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "todoitemdb.db";
    public TodoItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ItemDbSchema.ItemTable.NAME + "(" +
        " _id integer primary key autoincrement, "+
        ItemDbSchema.ItemTable.Cols.UUID + ", " +
        ItemDbSchema.ItemTable.Cols.TITLE+ ", " +
        ItemDbSchema.ItemTable.Cols.TIME
        + ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
