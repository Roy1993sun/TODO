package com.roy.todo;

import com.roy.todo.bean.TodoItem;
import com.roy.todo.database.TodoBaseHelper;
import com.roy.todo.database.TodoConstants.TodoTable;
import com.roy.todo.database.TodoCursorWrapper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 对数据库的增删
 */

public class TodoLibrary {
    private static TodoLibrary    sLibrary;
    private        Context        mContext;
    private        SQLiteDatabase mDatabase;

    public static TodoLibrary get(Context context) {
        if (sLibrary == null) {
            sLibrary = new TodoLibrary(context);
        }
        return sLibrary;
    }

    private TodoLibrary(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TodoBaseHelper(mContext).getWritableDatabase();
    }

    public void addTodoItem(TodoItem item) {
        ContentValues values = getContentValues(item);

        mDatabase.insert(TodoTable.NAME, null, values);
    }

    public void deleteTodoItem(UUID todoId) {
        String uuidString = todoId.toString();

        mDatabase.delete(TodoTable.NAME,
                TodoTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public List<TodoItem> getItems() {
        List<TodoItem> todoItems = new ArrayList<>();

        TodoCursorWrapper cursor = queryTodoItems(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                todoItems.add(cursor.getTodoItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return todoItems;
    }

    public TodoItem getItem(UUID id) {
        TodoCursorWrapper cursor = queryTodoItems(
                TodoTable.Cols.UUID + " = ?", new String[]{id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTodoItem();
        } finally {
            cursor.close();
        }

    }

    public void updateItem(TodoItem todoItem) {
        String uuidString = todoItem.getID()
                                    .toString();
        ContentValues values = getContentValues(todoItem);

        mDatabase.update(TodoTable.NAME, values,
                TodoTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    private static ContentValues getContentValues(TodoItem todoItem) {
        ContentValues values = new ContentValues();

        values.put(TodoTable.Cols.UUID, todoItem.getID()
                                                .toString());

        values.put(TodoTable.Cols.TITLE, todoItem.getTitle());

        values.put(TodoTable.Cols.DATE, todoItem.getDate()
                                                .getTime());

        values.put(TodoTable.Cols.COMPLETED, todoItem.isCompleted() ?
                1 :
                0);

        return values;
    }

    private TodoCursorWrapper queryTodoItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(TodoTable.NAME, null, whereClause, whereArgs, null, null, null);

        return new TodoCursorWrapper(cursor);
    }

}
