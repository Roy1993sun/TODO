package com.roy.todo.database;

import com.roy.todo.database.TodoConstants.TodoTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创造本地数据库
 */
public class TodoBaseHelper extends SQLiteOpenHelper {
    private static final int    VERSION       = 1;
    private static final String DATABASE_NAME = "todoBase.db";

    public TodoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TodoTable.NAME + "(" +
                   TodoTable.Cols.UUID + ", " +
                   TodoTable.Cols.TITLE + ", " +
                   TodoTable.Cols.DATE + ", " +
                   TodoTable.Cols.COMPLETED + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
