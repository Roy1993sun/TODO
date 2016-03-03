package com.roy.todo.base;

import android.support.v4.app.Fragment;

import com.roy.todo.ui.TodoListFragment;


public class TodoListActivity extends SingleActivity {
    @Override
    protected Fragment createFragment() {
        return new TodoListFragment();
    }
}
