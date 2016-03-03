package com.roy.todo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.roy.todo.R;
import com.roy.todo.TodoLibrary;
import com.roy.todo.bean.TodoItem;

import java.util.List;
import java.util.UUID;

/**
 * 应用的主界面, 读取已经存入的条目并展示
 */
public class TodoPagerActivity extends AppCompatActivity {

    private static final String EXTRA_TODO_ID = "todo_id";

    private ViewPager      mViewPager;
    private List<TodoItem> mItemList;

    public static Intent newIntent(Context packageContext, UUID todoId) {
        Intent intent = new Intent(packageContext, TodoPagerActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_todo_pager_view_pager);

        UUID todoId = (UUID) getIntent().getSerializableExtra(EXTRA_TODO_ID);

        mItemList = TodoLibrary.get(this).getItems();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                TodoItem todoItem = mItemList.get(position);
                return TodoFragment.newInstance(todoItem.getID());
            }

            @Override
            public int getCount() {
                return mItemList.size();
            }
        });

        for (int i = 0; i < mItemList.size(); i++) {
            if (mItemList.get(i).getID().equals(todoId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
