package com.roy.todo.database;

import com.roy.todo.bean.TodoItem;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;
import com.roy.todo.database.TodoConstants.TodoTable;

public class TodoCursorWrapper  extends CursorWrapper{

    public TodoCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public TodoItem getTodoItem(){
        String uuidString = getString(getColumnIndex(TodoTable.Cols.UUID));
        String title = getString(getColumnIndex(TodoTable.Cols.TITLE));
        long date = getLong(getColumnIndex(TodoTable.Cols.DATE));
        int isCompleted = getInt(getColumnIndex(TodoTable.Cols.COMPLETED));

        TodoItem todoItem = new TodoItem(UUID.fromString(uuidString));
        todoItem.setTitle(title);
        todoItem.setDate(new Date(date));
        todoItem.setCompleted(isCompleted != 0);

        return todoItem;
    }
}
